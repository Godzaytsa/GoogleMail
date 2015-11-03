package selenium.googlemail.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.helpers.WebElementHelper;
import selenium.wrappers.WebDriver;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class HomePage {

    private WebDriver driver;

    @FindBy(xpath="//div[@role='button'][text()='COMPOSE']")
    private WebElement composeButtonLocator;

    @FindBy(xpath="//div[@class='b8 UC'][@role='alert']/*/div[@class='vh']")
    private WebElement notificationMessageLocator;

    HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public ComposeEmail composeEmail() {
        composeButtonLocator.click();
        return new ComposeEmail(driver);
    }

    public Boolean isOpened() {
        return composeButtonLocator.isDisplayed();
    }

    public String getNotificationMessage() {
        WebElementHelper.isTextPresentInElement(notificationMessageLocator, 5);
        return notificationMessageLocator.getText();
    }

}
