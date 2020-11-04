package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
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
        boolean elem = isElementPresent(driver, By.cssSelector("[title *= Database]"));
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

    @Test
    public void ifCountriesSortedInAlphabetOrder() {
        if (!areElementsPresent(driver, By.cssSelector("[title *= Database]"))) {
            adminLoginTest();
        }
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        ArrayList<String> countryNames = new ArrayList();
        ArrayList<String> countryNamesSort = new ArrayList();
        List<WebElement> rows = getElsByTwoStep(By.id("content"), By.className("row"));
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> currentRows = getElsByTwoStep(By.id("content"), By.className("row"));
            String countryName = currentRows.get(i).findElement(By.xpath("./td[5]")).getText();
            countryNames.add(countryName);
            int n = Integer.parseInt(currentRows.get(i).findElement(By.xpath("./td[6]")).getText());
            if (n > 0) {
                currentRows.get(i).findElement(By.xpath("./td[7]")).click();
                ArrayList<String> zoneNames = new ArrayList();
                ArrayList<String> zoneNamesSort = new ArrayList();
                List<WebElement> rowsZone = getElsByTwoStep(By.id("table-zones"), By.cssSelector("tr:not(.header)"));
                for (WebElement rowz : rowsZone) {
                    String zoneName = rowz.findElement(By.xpath("./td[3]")).getText();
                    if (!zoneName.equals("")) {
                        zoneNames.add(zoneName);
                    }
                }
                for (String namez : zoneNames) {
                    zoneNamesSort.add(namez);
                }
                Collections.sort(zoneNamesSort);
                assertThat(zoneNames, is(zoneNamesSort));
                driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
            }
        }
        for (String name : countryNames) {
            countryNamesSort.add(name);
        }
        Collections.sort(countryNamesSort);
        assertThat(countryNames, is(countryNamesSort));
    }

    @Test
    public void ifGeoZonesSortedInAlphabetOrder() {
        if (!areElementsPresent(driver, By.cssSelector("[title *= Database]"))) {
            adminLoginTest();
        }
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> rows = getElsByTwoStep(By.id("content"), By.className("row"));
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> currentRows = getElsByTwoStep(By.id("content"), By.className("row"));
            currentRows.get(i).findElement(By.xpath("./td[5]")).click();
            ArrayList<String> zoneNames = new ArrayList();
            ArrayList<String> zoneNamesSort = new ArrayList();
            List<WebElement> rowsZone = getElsByTwoStep(By.id("table-zones"), By.cssSelector("tr:not(.header)"));
            for (WebElement rowz : rowsZone) {
                List<WebElement> elems = rowz.findElements(By.xpath("./td[3]/select/option"));
                for (WebElement el : elems) {
                    boolean n = Boolean.parseBoolean(el.getAttribute("selected"));
                    if (n) {
                        zoneNames.add(el.getAttribute("textContent"));
                    }
                }
            }
            for (String name : zoneNames) {
                zoneNamesSort.add(name);
            }
            Collections.sort(zoneNamesSort);
            assertThat(zoneNames, is(zoneNamesSort));
            driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        }
    }
}
