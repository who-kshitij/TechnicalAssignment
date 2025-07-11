package sections;

import elementinteractionmanagement.ElementState;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import java.io.*;
import java.net.URL;
import java.util.List;

public class OpinionPageSection extends BaseSection {

    public OpinionPageSection(WebDriver driver) {
        super(driver, By.xpath("//h1//a[text()='Opinión']"));
    }

    private By articlesLocator = By.tagName("article");
    private By figureLocator = By.tagName("img");
    private By headingLocator = By.tagName("h2");

    public void fetchArticles() {
        ElementState.isVisible(driver,articlesLocator);
        ElementState.isVisible(driver,By.cssSelector("article h2"));
        List<WebElement> articles = driver.findElements(articlesLocator);

        int counter = 0;
        for (WebElement article : articles) {
            if (counter == 5) break;
            counter++;

            WebElement headingElement = article.findElement(headingLocator);
            String heading = headingElement.getText();

            WebElement contentElement = article.findElement(By.tagName("p"));
            String content = contentElement.getText();

            WebElement imageElement = null;
            try {
                imageElement = article.findElement(figureLocator);
                String imageUrl = imageElement.getAttribute("src");

                if (imageUrl != null && !imageUrl.isEmpty()) {
                    downloadAndSaveImage(imageUrl, heading);
                } else {
                    System.out.println("No image found for article: " + heading);
                }
            } catch (NoSuchElementException | IOException e) {
                System.out.println("No image for article: " + heading);
            }

            JavascriptExecutor jse = (JavascriptExecutor)driver;
            jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \""+
                    "Article Title: " + heading+
                    "\", \"level\": \"info\"}}");
            jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \""+
                    "Article Content: " + content+
                    "\", \"level\": \"info\"}}");
        }
    }

    public String[] getArticleTitles() {
        String[] titles=new String[5];
        List<WebElement> articles = driver.findElements(articlesLocator);

        int counter = 0;
        for (WebElement article : articles) {
            if (counter == 5){
                break;
            }
            counter++;
            WebElement headingElement = article.findElement(headingLocator);
            String heading = headingElement.getText();
            titles[counter-1]=heading;
        }
        return titles;
    }

    private void downloadAndSaveImage(String imageUrl, String heading) throws IOException {
        String dirPath = "src/downloads/";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String imagePath = dirPath + heading.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg";
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new FileOutputStream(imagePath);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();

        System.out.println("Image saved to: " + imagePath);
    }

}
