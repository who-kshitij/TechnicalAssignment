package elementinteractionmanagement;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementState {

    private static final int TIMEOUT = 30;

    public static WebElement isVisible(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (WebDriverException e) {
            throw new WebDriverException("Exception occurred while checking for element visibility- "+e.getMessage());
        }
    }

    public static WebElement isInteractable(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            throw new WebDriverException("Exception occurred while checking for element interactability- "+e.getMessage());
        }
    }

    public static boolean isMobileDevice(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long width = (Long) js.executeScript("return window.innerWidth;");
        return width < 768;
    }

}
