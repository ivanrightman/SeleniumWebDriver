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
            List<WebElement> products = elem.findElements(By.tagName("li"));
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
}
