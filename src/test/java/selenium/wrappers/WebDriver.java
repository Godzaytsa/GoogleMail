package selenium.wrappers;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class WebDriver implements org.openqa.selenium.WebDriver {

    private org.openqa.selenium.WebDriver driver;

    public static final String FIREFOX = "FIREFOX";
    public static final String CHROME = "CHROME";
    public static final String IE = "IE";

    public WebDriver() {
        this(FIREFOX);
    }

    public WebDriver(String name) {
        this(name, "");
    }

    public WebDriver(String name, String pathToDriver) {
        switch (name.toUpperCase()) {

            case "CHROME":
                this.driver = getChromeDriver(pathToDriver);
                break;

            case "IE":
                this.driver = getIEDriver(pathToDriver);
                break;

            default:
                this.driver = getFirefoxDriver(pathToDriver);
                break;
        }

        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private org.openqa.selenium.WebDriver getFirefoxDriver(String pathToDriver) {
        if (! pathToDriver.isEmpty())
            System.setProperty("webdriver.firefox.driver", pathToDriver);

        org.openqa.selenium.WebDriver driver = new FirefoxDriver();
        return driver;
    }

    private org.openqa.selenium.WebDriver getChromeDriver(String pathToDriver) {
        if (! pathToDriver.isEmpty())
            System.setProperty("webdriver.chrome.driver", pathToDriver);

        ChromeOptions driverOptions = new ChromeOptions();
        driverOptions.addArguments("--lang=en");
        return new ChromeDriver(driverOptions);
    }

    private org.openqa.selenium.WebDriver getIEDriver(String pathToDriver) {
        if (! pathToDriver.isEmpty())
            System.setProperty("webdriver.ie.driver", pathToDriver);

        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return new InternetExplorerDriver(capabilities);
    }

    @Override
    public void get(String url) {
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

}
