package sections;

import elementinteractionmanagement.ElementActions;
import elementinteractionmanagement.ElementState;
import elementinteractionmanagement.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageSection extends BaseSection {

    public HomePageSection(WebDriver driver) {
        super(driver, By.id("main-content"));
    }

    public void agreeCookiePopup(){
        if(ElementState.isMobileDevice(driver)){
            ElementActions.click(driver, By.xpath("//a[@class='pmConsentWall-button' and text()='Accept and continue']"));
        }else{
            ElementActions.click(driver, By.id("didomi-notice-agree-button"));
        }
    }

    public void verifyIfPageIsSpanish(){
        WebElement htmlTag=ElementState.isVisible(driver, By.tagName("html"));
        if(htmlTag.getAttribute("lang").trim().equalsIgnoreCase("es-ES")){
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \""+
                    "Webpage text is displayed in Spanish language."+
                    "\", \"level\": \"info\"}}");
        }else{
            throw new RuntimeException("Webpage text is not displayed in Spanish language.");
        }
    }

    public NavbarMenuSection expandNavbar(){
        ElementActions.click(driver, By.id("btn_open_hamburger"));
        return new NavbarMenuSection(driver);
    }

}