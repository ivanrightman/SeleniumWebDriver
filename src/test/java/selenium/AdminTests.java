package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class AdminTests extends TestBase {

    @Test
    public void adminLoginTest() {
        driver.get("http://localhost/litecart/admin/.");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        boolean elem = isElementPresent(driver, By.linkText("Appearence"));
        assertThat(elem, is(true));
    }

    @Test
    public void adminMenuClickTest() {
        adminLoginTest();
        List<WebElement> elems = getElsByTwoStep(By.id("box-apps-menu-wrapper"), By.id("app-"));
        for (int i = 0; i < elems.size(); i++) {
            List<WebElement> currentElList = getElsByTwoStep(By.id("box-apps-menu-wrapper"), By.id("app-"));
            currentElList.get(i).click();
            assertThat(isElementPresent(driver, By.tagName("h1")), is(true));
            boolean isNestedMenus = areElementsPresent(driver, By.xpath("//ul[contains(@class,'docs')]"));
            if (isNestedMenus) {
                List<WebElement> elemsInMenu = getElsByThreeStep(By.id("box-apps-menu-wrapper"), By.className("docs"), By.tagName("a"));
                for (int n = 0; n < elemsInMenu.size(); n++) {
                    List<WebElement> currentElList2 = getElsByThreeStep(By.id("box-apps-menu-wrapper"), By.className("docs"), By.tagName("a"));
                    currentElList2.get(n).click();
                    assertThat(isElementPresent(driver, By.tagName("h1")), is(true));
                }
            }
        }
    }
}
