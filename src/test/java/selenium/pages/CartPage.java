package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends Page {

    public CartPage(EventFiringWebDriver driver) {
        super(driver);
    }


    public List<WebElement> shortcuts() {
        return getElsByTwoStep(By.id("box-checkout-cart"), By.className("shortcut"));
    }
    public WebElement summary() {
        return driver.findElement(By.id("box-checkout-summary"));
    }
    public WebElement removeButton() {
        return driver.findElement(By.cssSelector("button[name=\"remove_cart_item\"]"));
    }

    public void removeProductFromCart() {
        for (WebElement el : shortcuts()) {
            summary();
            List<WebElement> shortcutsCurrent = shortcuts();
            if (shortcutsCurrent.size() > 0) {
                shortcutsCurrent.get(0).click();
            }
            removeButton().click();
            wait.until(ExpectedConditions.stalenessOf(summary()));
        }
    }


}
