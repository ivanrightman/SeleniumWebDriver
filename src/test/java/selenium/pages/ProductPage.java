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

    public List<WebElement> sizeField() {
        return driver.findElements(By.cssSelector("select[name=\"options[Size]\"]"));
    }

    public WebElement addToCartButton() {
        return driver.findElement(By.cssSelector("button[name='add_cart_product']"));
    }
    public void addToCart(String size) {
        if(sizeField().size() > 0) {
            selectProductSize(size);
        }
       addToCartButton().click();
    }

    public void selectProductSize(String size) {
        sizeField().get(0).click();
        click(By.cssSelector("option[value=" + size + "]"));
    }

}

