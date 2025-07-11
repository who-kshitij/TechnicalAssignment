
import elementinteractionmanagement.Utils;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import sections.HomePageSection;
import sections.OpinionPageSection;

public class SeleniumTest {
    public WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @SuppressWarnings("unchecked")
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
    }

    @Test
    public void technicalTask(){
        System.out.println("Execution started");
        driver.get("https://elpais.com/");
        System.out.println(driver.getTitle());
        Assert.assertTrue(driver.getTitle().matches("EL PAÍS: el periódico global"));
        HomePageSection homePageSection=new HomePageSection(driver);
        homePageSection.agreeCookiePopup();
        homePageSection.verifyIfPageIsSpanish();
        OpinionPageSection opinionPageSection=homePageSection.expandNavbar().goToOpinionPage();
        opinionPageSection.fetchArticles();
        String[] articleTitles = opinionPageSection.getArticleTitles();
        String[] translatedTitle = Utils.translateToEnglish(driver,articleTitles);
        Utils.analyzeTranslatedHeaders(driver, translatedTitle);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }
}
