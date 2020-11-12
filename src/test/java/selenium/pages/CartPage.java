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

    public String quantityInCart() {
        List<WebElement> elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        String quantity = elQuantity.get(0).getAttribute("textContent");
        return quantity;
    }


    public void removeProductFromCart() {
        List<WebElement> shortcuts = getElsByTwoStep(By.id("box-checkout-cart"), By.className("shortcut"));
        for (WebElement el : shortcuts) {
            WebElement summary = driver.findElement(By.id("box-checkout-summary"));
            List<WebElement> shortcutsCurrent = getElsByTwoStep(By.id("box-checkout-cart"), By.className("shortcut"));
            if (shortcutsCurrent.size() > 0) {
                shortcutsCurrent.get(0).click();
            }
            click(By.cssSelector("button[name=\"remove_cart_item\"]"));
            wait.until(ExpectedConditions.stalenessOf(summary));
        }
    }

    public void checkout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Checkout »")));
        click(By.linkText("Checkout »"));
    }
}
