package tests.junit.googlemail;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import selenium.helpers.User;
import selenium.googlemail.pages.HomePage;
import selenium.googlemail.pages.LoginPage;
import selenium.wrappers.WebDriver;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class GoogleMailTest {

    WebDriver driver;

    @Before
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
            assertEquals("Please enter your email.", loginPage.getLoginErrorMessage());

            // Test login with invalid email
            loginPage.loginAs("@%");
            assertEquals("Please enter a valid email address.", loginPage.getLoginErrorMessage());

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

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }

}
