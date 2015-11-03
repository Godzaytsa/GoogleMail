package selenium.googlemail.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import selenium.wrappers.WebDriver;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class ComposeEmail {

    private WebDriver driver;

    @FindBy(xpath="//div[@class='nH Hd'][@role='dialog']")
    private WebElement composeEmailAreaLocator;

    @FindBy(xpath="//div[@class='aoD hl']/div[@class='oL aDm'][text()='Recipients']/..")
    private WebElement toInactiveLocator;

    @FindBy(xpath="//textarea[@name='to']")
    private WebElement toActiveLocator;

    @FindBy(xpath="//input[@name='subjectbox']")
    private WebElement subjectLocator;

    @FindBy(xpath="//div[@aria-label='Message Body']")
    private WebElement bodyLocator;

    @FindBy(xpath="//div[@class='a1 aaA aMZ']")
    private WebElement attachFileLocator;

    @FindBy(xpath="//div[@role='button'][text()='Send']")
    private WebElement sendButtonLocator;

    public ComposeEmail(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void setTo(String to) {
        activateToField();
        toActiveLocator.sendKeys(to);
    }

    private void activateToField() {
        if (toInactiveLocator.isDisplayed())
            toInactiveLocator.click();
    }

    public void setSubject(String subject) {
        subjectLocator.sendKeys(subject);
    }

    public void setBody(String body) {
        bodyLocator.sendKeys(body);
    }

    public void attachFile(String attachmentPath) throws AWTException {
        attachFileLocator.click();

        // Copy file path to clipboard
        StringSelection ss = new StringSelection(attachmentPath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        Robot robot = new Robot();

        robot.delay(3000);

        // Paste string from clipboard by using Ctrl+C -> Ctrl+V combination
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Press Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public HomePage sendEmail() {
        sendButtonLocator.click();
        return new HomePage(driver);
    }

    public HomePage sendEmail(String to, String subject, String body) {
        setTo(to);
        setSubject(subject);
        setBody(body);
        return sendEmail();
    }

    public HomePage sendEmail(String to, String subject, String body, String attachment) throws AWTException {
        attachFile(attachment);
        return sendEmail(to, subject, body);
    }

    public Boolean isOpened() {
        return composeEmailAreaLocator.isDisplayed();
    }

}
