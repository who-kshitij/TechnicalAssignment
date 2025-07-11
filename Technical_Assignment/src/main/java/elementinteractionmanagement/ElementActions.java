package elementinteractionmanagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class ElementActions {
    public static void click(WebDriver driver, By locator) {
        ElementState.isInteractable(driver, locator).click();
    }
}
