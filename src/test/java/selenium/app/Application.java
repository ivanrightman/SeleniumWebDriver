package selenium.app;

import org.junit.Assert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import selenium.model.Product;
import selenium.page_blocks.Cart;
import selenium.pages.CartPage;
import selenium.pages.MainPage;
import selenium.pages.ProductPage;

public class Application {
    public EventFiringWebDriver driver;
    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private Cart cart;

    public Application() {
        driver = new EventFiringWebDriver(new ChromeDriver());
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        cart = new Cart(driver);
    }

    public void addOneProductToCart(Product.Builder product) {
        mainPage.open();
        String quantityBefore = cart.quantityInCart();
        mainPage.clickFirstProductFrom(product.getBlock());
        productPage.addToCart(product.getSize());
        cart.waitOneProductAdded();
        String quantityAfter = cart.quantityInCart();
        Assert.assertTrue(Integer.parseInt(quantityAfter) > Integer.parseInt(quantityBefore));
    }

    public MainPage goToMainPage() {
        return mainPage;
    }

    public CartPage doOnCartPage() {
        return cartPage;
    }

    public Cart doInCart() {
        return cart;
    }
}
