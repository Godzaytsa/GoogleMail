package selenium.googlemail.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.helpers.WebElementHelper;
import selenium.wrappers.WebDriver;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class LoginPage {

    private WebDriver driver;

    @FindBy(xpath="//input[@id='Email']")
    private WebElement usernameLocator;

    @FindBy(xpath="//input[@id='Passwd']")
    private WebElement passwordLocator;

    @FindBy(xpath="//input[@id='next']")
    private WebElement nextButtonLocator;

    @FindBy(xpath="//input[@id='signIn']")
    private WebElement signinButtonLocator;

    @FindBy(xpath="//input[@id='PersistentCookie']")
    private WebElement staySignedinLocator;

    @FindBy(xpath="//h2[contains(text(), 'Sign in to continue to Gmail')]")
    private WebElement homePageCaptionLocator;

    @FindBy(xpath="//span[@id='errormsg_0_Email']")
    private WebElement userErrorLocator;

    public LoginPage(WebDriver driver) throws IllegalStateException {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        try {
            WebElementHelper.waitForElement(driver, homePageCaptionLocator, 30);
        }
        catch (NoSuchElementException e) {
            throw new IllegalStateException("This is not Login page.");
        }
    }

    private void typeUsername(String username) {
        usernameLocator.clear();
        usernameLocator.sendKeys(username);
    }

    private void typePassword(String password) {
        WebElementHelper.waitForElement(driver, passwordLocator, 10);
        passwordLocator.clear();
        passwordLocator.sendKeys(password);
    }

    private LoginPage submitLogin() {
        nextButtonLocator.submit();
        return new LoginPage(driver);
    }

    public LoginPage loginAs(String username) {
        typeUsername(username);
        return submitLogin();
    }

    public String getLoginErrorMessage() {
        WebElementHelper.isTextPresentInElement(userErrorLocator, 5);
        return userErrorLocator.getText();
    }

    public HomePage submitPassword(String pwd, boolean staySignedIn) {
        typePassword(pwd);

        if (staySignedIn && !staySignedinLocator.isSelected() || !staySignedIn && staySignedinLocator.isSelected())
            staySignedinLocator.click();

        staySignedinLocator.submit();
        return new HomePage(driver);
    }

    public Boolean isOpened() {
        return homePageCaptionLocator.isDisplayed();
    }

}
