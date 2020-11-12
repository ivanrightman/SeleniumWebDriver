package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MainPageTests extends TestBase {

    @Test
    public void ifAllProductsHaveStickers() {
        mainPage();
        int ducksCount = 0;
        int stickerCount = 0;
        List<WebElement> elems = getElsByTwoStep(By.cssSelector("div.middle > div.content"), By.className("box"));
        for (WebElement elem : elems) {
            List<WebElement> products = elem.findElements(By.className("product"));
            ducksCount += products.size();
            for (WebElement product : products) {
                List<WebElement> stickers = product.findElements(By.cssSelector("[class *= sticker]"));
                assertThat(stickers.size(), is(1));
                if (stickers.size() == 1) {
                    stickerCount++;
                }
            }
        }
        assertThat(ducksCount, is(stickerCount));
    }

    @Test
    public void ifProductSameOnMainPageAndProductPage() {
        mainPage();
        List<WebElement> elems = getElsByTwoStep(By.cssSelector("div.middle > div.content"), By.id("box-campaigns"));
        WebElement product = elems.get(0).findElement(By.cssSelector("li:first-child"));
        String mpName = product.findElement(By.cssSelector("a")).getAttribute("title");
        String mpPrice = product.findElement(By.className("regular-price")).getText();
        String mpSalePrice = product.findElement(By.className("campaign-price")).getText();
        String mpPriceStyle = product.findElement(By.className("regular-price")).getCssValue("text-decoration"); // text-decoration
        String mpPriceColor = product.findElement(By.className("regular-price")).getCssValue("color");
        String mpSalePriceStyle = product.findElement(By.className("campaign-price")).getTagName();
        String mpSalePriceColor = product.findElement(By.className("campaign-price")).getCssValue("color");
        String mpPriceSize = product.findElement(By.className("regular-price")).getCssValue("font-size");
        String mpSalePriceSize = product.findElement(By.className("campaign-price")).getCssValue("font-size");

        assertThat(colorIs(mpPriceColor), is("gray"));
        assertThat(colorIs(mpSalePriceColor), is("red"));
        assertThat(isSizeBigger(mpSalePriceSize, mpPriceSize), is(true));

        product.click();
        String ppName = driver.findElement(By.cssSelector("h1.title")).getText();
        String ppPrice = driver.findElement(By.className("regular-price")).getText();
        String ppSalePrice = driver.findElement(By.className("campaign-price")).getText();
        String ppPriceStyle = driver.findElement(By.className("regular-price")).getCssValue("text-decoration"); // text-decoration
        String ppPriceColor = driver.findElement(By.className("regular-price")).getCssValue("color");
        String ppSalePriceStyle = driver.findElement(By.className("campaign-price")).getTagName();
        String ppSalePriceColor = driver.findElement(By.className("campaign-price")).getCssValue("color");
        String ppPriceSize = driver.findElement(By.className("regular-price")).getCssValue("font-size");
        String ppSalePriceSize = driver.findElement(By.className("campaign-price")).getCssValue("font-size");

        assertThat(mpName, is(ppName));
        assertThat(mpPrice, is(ppPrice));
        assertThat(mpSalePrice, is(ppSalePrice));
        assertThat(isStringExist(mpPriceStyle, "line-through"), is(isStringExist(ppPriceStyle, "line-through")));
        assertThat(colorIs(ppPriceColor), is("gray"));
        assertThat(mpSalePriceStyle,is(ppSalePriceStyle));
        assertThat(colorIs(ppSalePriceColor), is("red"));
        assertThat(isSizeBigger(ppSalePriceSize,ppPriceSize), is(true));
    }

    @Test
    public void userRegTest() {
        mainPage();
        click(By.linkText("New customers click here"));
        fillCreateAccount(inputStringGenerator());
        click(By.cssSelector("button[name='create_account']"));
        logout();
        fillLoginForm();
        click(By.cssSelector("button[name='login']"));
        logout();
    }

    @Test
    public void productAddTest() {
        for (int i = 0; i < 3; i++) {
           app.mainPage().;
           addFirstProductFromList(By.id("box-most-popular"));
        }
        mainPage();
        checkout();
        removeProductFromCart();
    }
}
