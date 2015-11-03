package tests.jbehave.googlemail.steps;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import helpers.User;
import helpers.DateTime;
import selenium.googlemail.pages.*;
import selenium.wrappers.WebDriver;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class GoogleMailTestSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private HomePage homePage;
    private ComposeEmail composeEmail;

    private String sendToEmail = null;

    @BeforeStories
    public void setUp() {
        //driver = new WebDriver(WebDriver.FIREFOX);
        driver = new WebDriver(WebDriver.CHROME, "d:\\Projects\\Selenium\\WebDrivers\\chromedriver.exe");
        //driver = new WebDriver(WebDriver.IE, "d:\\Projects\\Selenium\\WebDrivers\\IEDriverServer.exe");
    }

    @AfterStories
    public void tearDown() {
        driver.close();
        driver.quit();
    }
    /*
    public GoogleMailTestSteps() {
        driver = new WebDriver(WebDriver.FIREFOX);
        //driver = new WebDriver(WebDriver.CHROME);
        //driver = new WebDriver(WebDriver.IE);
    }
    */
    @Given("Gmail Login page is opened")
    public void loginPageIsOpened() {
        driver.get("http://mail.google.com");
        loginPage = new LoginPage(driver);
    }

    @When("User attempts to login with invalid email $email")
    public void loginWithInvalidEmail(String email) {
        loginPage.loginAs(email);
    }

    @Then("$error_message error message should appear")
    public void errorMessageShouldAppear(@Named("error_message") String errorMessage) {
        assertEquals(errorMessage, loginPage.getLoginErrorMessage());
    }

    @When("User enter valid email and press Next button")
    public void enterValidUserOnLoginPage() {
        loginPage.loginAs(User.email);
    }

    @When("User enter valid password and press Sign In button")
    public void enterValidPasswordAndLogIn() {
        homePage = loginPage.submitPassword(User.password, false);
    }

    @Given("Gmail Home page is opened")
    @Then("Gmail Home page is opened")
    public void homePageIsOpened() {
        assertTrue("Home page is opened.", homePage.isOpened());
    }

    @When("User clicks Compose button on Home page")
    public void clickComposeButtonOnHomePage() {
        composeEmail = homePage.composeEmail();
    }

    @Then("New Email window is opened")
    public void emailWindowIsOpened() {
        assertTrue("New Email window is opened.", composeEmail.isOpened());
    }

    @When("User attach $file file")
    public void attachFile(String file) throws AWTException {
        composeEmail.attachFile(System.getProperty("user.dir") + file);
    }

    @When("User type $account+$dateFormat@$domain in To field")
    public void typeTextInToField(String account, String dateFormat, String domain) {
        sendToEmail = account + "+" + DateTime.getCurrentTime(dateFormat) + "@" + domain;
        composeEmail.setTo(sendToEmail);
    }

    @When("User type $subject in Subject field")
    public void typeTextInSubjectField(String subject) {
        composeEmail.setSubject(subject);
    }

    @When("User type $body in Email body field")
    public void typeTextInBodyField(String body) {
        composeEmail.setBody(body);
    }

    @When("User clicks Send button")
    public void clickSendButton() {
        homePage = composeEmail.sendEmail();
    }

    @Then("\"$message\" notification message appears on Gmail Home page")
    public void notificationMessageAppears(String message) {
        assertEquals(message, homePage.getNotificationMessage());
    }
}

