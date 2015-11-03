package helpers;

/**
 * Created by Gadzilla on 02.11.2015.
 */
public class EmailAddress {

    private String userName = null;
    private String domain = null;

    public EmailAddress(String emailAddress) {
        int index = emailAddress.lastIndexOf("@");
        if (index < 0)
            userName = emailAddress;
        else
        {
            userName = emailAddress.substring(0, index);
            domain = emailAddress.substring(index + 1);
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getDomain() {
        return domain;
    }
}
