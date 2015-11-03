package selenium.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.SystemClock;

import selenium.wrappers.WebDriver ;

/**
 * Created by Gadzilla on 25.10.2015.
 */
public class WebElementHelper {

    public static WebElement waitForElement(WebDriver driver, By locator, long timeOutInSeconds) throws NoSuchElementException {
        WebElement elem;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            elem = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        catch(Exception e) {
            throw new NoSuchElementException(e.getMessage(), e);
        }

        return elem;
    }

    public static WebElement waitForElement(WebDriver driver, WebElement element, long timeOutInSeconds) throws NoSuchElementException {
        WebElement elem;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            elem = wait.until(ExpectedConditions.visibilityOf(element));
        }
        catch(Exception e) {
            throw new NoSuchElementException(e.getMessage(), e);
        }

        return elem;
    }

    public static Boolean isTextPresentInElement(WebDriver driver, By locator, String text, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static Boolean isTextPresentInElement(WebDriver driver, WebElement element, String text, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public static Boolean isTextPresentInElement(WebElement element, long timeOutInSeconds) {
        SystemClock clock = new SystemClock();
        long endTime = clock.laterBy(timeOutInSeconds * 1000);
        do {
            if (! element.getText().trim().isEmpty()) {
                return true;
            }
        } while (clock.isNowBefore(endTime));
        return false;
    }
}
