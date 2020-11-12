package selenium.page_blocks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import selenium.pages.Page;

import java.util.List;

public class Cart extends Page {

    public Cart(EventFiringWebDriver driver) {
        super(driver);
    }

    public String quantityInCart() {
        List<WebElement> elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        String quantity = elQuantity.get(0).getAttribute("textContent");
        return quantity;
    }

    public void waitOneProductAdded() {
        List<WebElement> elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        waitSomething(ExpectedConditions.attributeToBe(elQuantity.get(0), "textContent", strPlusInt(quantityInCart(), 1)));
    }

    public void checkout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Checkout »")));
        driver.findElement(By.linkText("Checkout »")).click();
    }
}
