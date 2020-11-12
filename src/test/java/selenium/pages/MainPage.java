package selenium.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MainPage extends Page{
    public MainPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public void open() {
        if (driver.findElements(By.id("slider")).size() > 0) {
            return;
        }
        driver.get("http://localhost/litecart/en/");
    }


    public void clickFirstProductFromList(By locator) {
        List<WebElement> elems = getElsByTwoStep(By.cssSelector("div.middle > div.content"), locator);
        elems.get(0).findElement(By.cssSelector("li:first-child")).click();
        addOneToCart();
    }


}
