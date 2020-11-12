package selenium.app;

import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.TestBase;
import selenium.pages.CartPage;
import selenium.pages.MainPage;
import selenium.pages.ProductPage;

import java.util.List;

public class Application extends Helpers{
    public EventFiringWebDriver driver;
    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @Before
    public void start() {
        driver = new EventFiringWebDriver(new ChromeDriver());
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public void addOneProductToCart() {
        mainPage.open();
        cartPage.quantityInCart();
        productPage.selectProductSize("Small");
        productPage.addToCartButton();
        wait.until(ExpectedConditions.attributeToBe(elQuantity.get(0), "textContent", strPlusInt(quantity, 1)));
        elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        String quantityAfter = elQuantity.get(0).getAttribute("textContent");
        Assert.assertTrue(Integer.parseInt(quantityAfter) > Integer.parseInt(quantity));
    }

    public int getQuantity() {

    }
}
