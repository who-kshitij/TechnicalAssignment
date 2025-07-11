package sections;

import elementinteractionmanagement.ElementState;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseSection {
    protected WebDriver driver;

    public BaseSection(WebDriver driver, By locator) {
        this.driver = driver;
        ElementState.isVisible(driver, locator);
    }

}