package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;

public class MainPage extends Page{
    public MainPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public List<WebElement> firstProdBlock() {
        return driver.findElements(By.id("box-most-popular"));
    }
    public List<WebElement> secondProdBlock() {
        return driver.findElements(By.id("box-campaigns"));
    }
    public List<WebElement> thirdProdBlock() {
        return driver.findElements(By.id("box-latest-products"));
    }

    public void open() {
        if (driver.findElements(By.id("slider")).size() > 0) {
            return;
        }
        driver.get("http://localhost/litecart/en/");
    }


    public void clickFirstProductFrom(String block) {
        if (block.equals("Most Popular")) {
            List<WebElement> elems = firstProdBlock();
            getFirst(elems);
        } else if (block.equals("Campaigns")) {
            List<WebElement> elems = secondProdBlock();
            getFirst(elems);
        } else if (block.equals("Latest Products")) {
            List<WebElement> elems = thirdProdBlock();
            getFirst(elems);
        }
    }

    public void getFirst(List<WebElement> elems) {
        elems.get(0).findElement(By.cssSelector("li:first-child")).click();
    }

    public List<WebElement> getWebElements(WebElement webElement) {
        return webElement.findElements(By.className("product"));
    }


}
