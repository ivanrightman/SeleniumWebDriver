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
        String mpSalePriceStyle = product.findElement(By.className("campaign-price")).getCssValue("color");
        if (isStringExist(mpPriceStyle, "line-through")) {

        }
        /*chrome
        mpPriceStyle = "line-through solid rgb(119, 119, 119)"
        mpSalePriceStyle = "none solid rgb(204, 0, 0)" - проверить наличие тега strong
         */
        /*firefox
         mpPriceStyle = "line-through rgb(119, 119, 119)"
         mpSalePriceStyle = "rgb(204, 0, 0)" - проверить наличие тега strong
         */
        /*IE
        mpPriceStyle = "line-through" - (color - rgba(119, 119, 119, 1) )
        mpSalePriceStyle = "none" (color - rgba(204, 0, 0, 1) ) - проверить наличие тега strong
        */
        product.click();
        String ppName = driver.findElement(By.cssSelector("h1.title")).getText();
        String ppPrice = driver.findElement(By.className("regular-price")).getText();
        String ppSalePrice = driver.findElement(By.className("campaign-price")).getText();
        String ppPriceStyle = driver.findElement(By.className("regular-price")).getCssValue("text-decoration"); // text-decoration
        String ppSalePriceStyle = driver.findElement(By.className("campaign-price")).getCssValue("color");
        assertThat(mpName, is(ppName));
        assertThat(mpPrice, is(ppPrice));
        assertThat(mpSalePrice, is(ppSalePrice));
    }
}
