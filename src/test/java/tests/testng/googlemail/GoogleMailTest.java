package tests.testng.googlemail;

import helpers.DateTime;
import helpers.EmailAddress;
import helpers.User;

import helpers.googlemail.api.GoogleMailClient;
import selenium.googlemail.pages.HomePage;
import selenium.googlemail.pages.LoginPage;
import selenium.wrappers.WebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class GoogleMailTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        //driver = new WebDriver(WebDriver.FIREFOX);
        driver = new WebDriver(WebDriver.CHROME, "d:\\Projects\\Selenium\\WebDrivers\\chromedriver.exe");
        //driver = new WebDriver(WebDriver.IE, "d:\\Projects\\Selenium\\WebDrivers\\IEDriverServer.exe");
        driver.get("http://mail.google.com");
    }

    @Test
    public void testLoginToGoogleAccount() {

        try {

            LoginPage loginPage = new LoginPage(driver);

            // Test login without credentials
            loginPage.loginAs("");
            Assert.assertEquals(loginPage.getLoginErrorMessage(), "Please enter your email.");

            // Test login with invalid email
            loginPage.loginAs("@%");
            Assert.assertEquals(loginPage.getLoginErrorMessage(), "Please enter a valid email address.");

            // Login as valid user and and un-check 'Stay Signed In' check-box.
            loginPage.loginAs(User.email);
            HomePage homePage = loginPage.submitPassword(User.password, false);

            // Generate email address (based on original email address) to which we will send new email
            EmailAddress email = new EmailAddress(User.email);
            String sendTo = email.getUserName() + "+" + DateTime.getCurrentTime("YYYY-MM-dd-hh-mm-ss") + "@" + email.getDomain();

            // Click 'Compose' button and send an email
            homePage.composeEmail().sendEmail(sendTo, "Test", "Test",
                                              System.getProperty("user.dir") + "\\src\\test\\resources\\testfiles\\test.pdf");

            // Check that message has been send.
            Assert.assertEquals("Your message has been sent. View message", homePage.getNotificationMessage());

            // Find out message that was send and download all attachments from this email
            GoogleMailClient gmc = new GoogleMailClient();
            gmc.login(User.email, User.password);
            gmc.downloadAllAttachmentsSentToUser(sendTo, System.getProperty("user.dir") + "\\src\\test\\resources\\temp");
            gmc.logout();
        }
        catch(Exception e) {
            System.out.println(driver.getTitle() + "; " + driver.getCurrentUrl());
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Test failed.");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
