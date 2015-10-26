package tests.testng.googlemail;

import selenium.helpers.User;
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
        driver = new WebDriver(WebDriver.FIREFOX);
        //driver = new Driver(Driver.CHROME, "d:\\Projects\\Selenium\\WebDrivers\\chromedriver.exe");
        //driver = new Driver(Driver.IE, "d:\\Projects\\Selenium\\WebDrivers\\IEDriverServer.exe");
        //driver = new WebDriver(WebDriver.HTMLUNIT);
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

            // Click 'Compose' button and send an email
            homePage.composeEmail().sendEmail("gadzilla.test@gmail.com", "Test", "Test");

        }
        catch(Exception e) {
            System.out.println(driver.getTitle() + "; " + driver.getCurrentUrl());
            System.out.println("Error: " + e.getMessage());
            System.out.println("Test failed.");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
