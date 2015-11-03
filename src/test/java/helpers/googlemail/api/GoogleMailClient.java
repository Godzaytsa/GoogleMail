package helpers.googlemail.api;

import helpers.EmailAddress;
import helpers.User;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Iterator;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;

/**
 * Created by Gadzilla on 28.10.2015.
 */
public class GoogleMailClient {

    private String user;
    private String password;

    private final String receivingHost = "imap.gmail.com";
    private final int receivingPort = 993;
    //private final String sendingHost = "smtp.gmail.com";
    //private final int sendingPort = 465;

    private Properties props = new Properties();
    private Store store = null;
    private HashMap<String, Folder> folders = new HashMap<String, Folder>();

    public GoogleMailClient() {
        //props.put("mail.smtp.host", this.sendingHost);
        //props.put("mail.smtp.port", String.valueOf(this.sendingPort));

        props.put("mail.imaps.host", this.receivingHost);
        props.put("mail.imaps.port", String.valueOf(this.receivingPort));
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.store.protocol", "imaps");
    }

    private Folder getFolder(String folderName) throws MessagingException {
        if (! folders.containsKey(folderName))
            folders.put(folderName, store.getFolder(folderName));

        Folder folder = folders.get(folderName);
        if (folder != null && (! folder.isOpen()))
            folder.open(Folder.READ_ONLY);

        return folder;
    }

    private void downloadAttachments(Message message, String pathToSave) throws MessagingException, IOException {
        // Create folder to download attachments
        File file = new File(pathToSave);
        if (! file.exists())
            file.mkdirs();

        // Message with attachments should have MultiPart type
        if (! message.getContentType().contains("multipart"))
            return;

        // Part that contains a file must be of type MimeBodyPart
        // Iterate throw all parts of our message to find out which one contains file
        Multipart multiPart = (Multipart) message.getContent();
        for (int i = 0; i < multiPart.getCount(); i++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                part.saveFile(file.getPath() + "\\" + part.getFileName());
            }
        }
    }

    public void login(String user, String password) throws MessagingException {
        this.user = user;
        this.password = password;

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        if (store == null)
            store = session.getStore("imaps");

        if (! store.isConnected())
            store.connect();
    }

    public void logout() {
        try {
            // Close all opened folders anr clear folders HashMap on logout.
            Iterator<Map.Entry<String, Folder>> iterator = folders.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Folder> mapEntry = iterator.next();
                Folder folder = mapEntry.getValue();
                if(folder != null && folder.isOpen())
                    folder.close(false); // do not expunge all deleted messages
                iterator.remove();
            }

            if (store != null && store.isConnected())
                store.close();
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Message[] getRecentMessages(String folderName) {
        Folder folder;
        Message[] messages;
        FlagTerm recentFlagTerm = new FlagTerm(new Flags(Flags.Flag.RECENT), true);

        // Search for recent messages in all un-read messages.
        try {
            folder = getFolder(folderName);
            messages = folder.search(recentFlagTerm, getUnreadMessages(folderName));
            //messages = folder.search(recentFlagTerm);
        }
        catch (MessagingException e) {
            return new Message[0];
        }

        return messages;
    }

    public Message[] getUnreadMessages(String folderName) {
        Folder folder;
        Message[] messages;
        FlagTerm seenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);

        try {
            folder = getFolder(folderName);
            messages = folder.search(seenFlagTerm);
        }
        catch(MessagingException e) {
            return new Message[0];
        }

        return messages;
    }

    public void downloadAllAttachmentsSentToUser(String email, String pathToSave) throws MessagingException, IOException{
        Message[] messages = getUnreadMessages("INBOX");
        for (Message message : messages) {
            boolean containRecepient = false;

            Address[] recepients = message.getRecipients(Message.RecipientType.TO);
            for (Address address : recepients) {
                if (address == null)
                    continue;

                if (email.equalsIgnoreCase(address.toString())) {
                    containRecepient = true;
                    break;
                }
            }

            if (! containRecepient)
                continue;

            downloadAttachments(message, pathToSave + "\\" + new EmailAddress(email).getUserName());
        }
    }

    public static void main(String[] args) {
        GoogleMailClient gmc = new GoogleMailClient();
        try {
            gmc.login(User.email, User.password);

            Message[] messages;
            messages = gmc.getUnreadMessages("INBOX");
            System.out.println("Unread messages: " + messages.length);
            for (int i = 0; i < messages.length; i++) {
                System.out.println(messages[i].getMessageNumber() + ": " + messages[i].getSubject());
            }

            gmc.logout();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
