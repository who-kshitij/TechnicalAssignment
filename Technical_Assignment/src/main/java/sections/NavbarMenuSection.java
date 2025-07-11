package sections;

import elementinteractionmanagement.ElementActions;
import elementinteractionmanagement.ElementState;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavbarMenuSection extends BaseSection {

    public NavbarMenuSection(WebDriver driver) {
        super(driver, By.id("hamburger_container"));
    }

    public OpinionPageSection goToOpinionPage() {
        ElementActions.click(driver, By.cssSelector("#hamburger_container a[href='https://elpais.com/opinion/']"));
        return new OpinionPageSection(driver);
    }
}
