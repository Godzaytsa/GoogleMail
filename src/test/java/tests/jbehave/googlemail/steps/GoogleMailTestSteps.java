package tests.jbehave.googlemail.steps;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import selenium.helpers.User;
import selenium.googlemail.pages.*;
import selenium.wrappers.WebDriver;

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

    @BeforeStories
    public void setUp() {
        driver = new WebDriver(WebDriver.FIREFOX);
        //driver = new WebDriver(WebDriver.CHROME);
        //driver = new WebDriver(WebDriver.IE);
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

    @When("User type $email in To field")
    public void typeTextInToField(String email) {
        composeEmail.setTo(email);
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

    @Then("email with $subject subject is send to $recepients")
    public void emailIsSendToRecepient(@Named("recepients") String recepients, @Named("subject") String subject) {
        // TODO: emailIsSendToRecepient method need to be implemented.
    }
}

