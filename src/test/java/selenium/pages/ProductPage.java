package selenium.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductPage extends Page {
    public ProductPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public WebElement addToCartButton() {
        return driver.findElement(By.cssSelector("button[name='add_cart_product']"));
    }
    public void addOneToCart() {
        List<WebElement> elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        String quantity = elQuantity.get(0).getAttribute("textContent");
        if (isElementPresent(driver, By.cssSelector("select[name=\"options[Size]\"]"))) {
            click(By.cssSelector("select[name=\"options[Size]\"]"));
            click(By.cssSelector("option[value=\"Small\"]"));
        }
        click(By.cssSelector("button[name='add_cart_product']"));
        wait.until(ExpectedConditions.attributeToBe(elQuantity.get(0), "textContent", strPlusInt(quantity, 1)));
        elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        String quantityAfter = elQuantity.get(0).getAttribute("textContent");
        Assert.assertTrue(Integer.parseInt(quantityAfter) > Integer.parseInt(quantity));
    }

    public void selectProductSize(String size) {
        if (driver.findElements(By.cssSelector("select[name=\"options[Size]\"]")).size() > 0) {
            click(By.cssSelector("select[name=\"options[Size]\"]"));
            click(By.cssSelector("option[value=" + size + "]"));
        }
    }

}

