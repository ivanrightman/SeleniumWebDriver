package selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    private String random;

    @Before
    public void start() {
        /*
        //Задание 4. Научитесь запускать разные браузеры
        driver = new FirefoxDriver();
        driver = new ChromeDriver();
        driver = new InternetExplorerDriver();
         */

        /*
        //Задание 5. Научитесь запускать Firefox ESR "по старой схеме"
        options
                .setLegacy(false)
                .setBinary("C:\\Program Files\\Mozilla Firefox ESR\\firefox.exe");
         */

        /*
        //Задание 6. Научитесь запускать Firefox Nightly
        options
                .setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
        driver = new FirefoxDriver(options);
        */

        /*
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        } */

        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        tlDriver.set(driver);
        wait = new WebDriverWait(driver, 15);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { driver.quit(); driver = null; }));
    }

    boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }


    boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public List<WebElement> getElsByTwoStep(By locatorOne, By locatorTwo) {
        WebElement elem = driver.findElement(locatorOne);
        return elem.findElements(locatorTwo);
    }

    public List<WebElement> getElsByThreeStep(By locatorOne, By locatorTwo, By locatorThree) {
        WebElement elem2 = driver.findElement(locatorOne);
        WebElement elemInElem = elem2.findElement(locatorTwo);
        return elemInElem.findElements(locatorThree);
    }

    public void mainPage() {
        if (areElementsPresent(driver, By.id("slider"))) {
            return;
        }
        driver.get("http://localhost/litecart/en/");
    }

    public boolean isStringExist(String string, String target) {
        if (string.contains(target)) {
            return true;
        } else {
            return false;
        }
    }

    public String colorIs(String string) {
        String[] stringSplit = string.substring(5, string.length() - 1).split(", ");
        String color = "";
        if (Integer.parseInt(stringSplit[0]) == Integer.parseInt(stringSplit[1]) &&
                Integer.parseInt(stringSplit[1]) == Integer.parseInt(stringSplit[2])) {
            color = "gray";
        } else if (Integer.parseInt(stringSplit[1]) == 0 &&
                    Integer.parseInt(stringSplit[2]) == 0) {
            color = "red";
        }
        return color;
    }

    public boolean isSizeBigger(String one, String two) {
        String[] fromOne = one.split("px");
        String[] fromTwo = two.split("px");
        double ione = Double.parseDouble(fromOne[0]);
        double itwo = Double.parseDouble(fromTwo[0]);
        if (ione > itwo) {
            return true;
        } else {
            return false;
        }
    }

    public String inputStringGenerator() {
        Random rm = new Random();
        random = String.valueOf(rm.nextInt() + System.currentTimeMillis());
        return random;
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null){
            String existingText = driver.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    public void fillCreateAccount(String input) {
        click(By.cssSelector("input[name=\"tax_id\"]"));
        type(By.cssSelector("input[name=\"firstname\"]"),"Vasya" + input);
        type(By.cssSelector("input[name=\"lastname\"]"), "Vaskin" + input);
        type(By.cssSelector("input[name=\"address1\"]"), "ulitsa" + input);
        type(By.cssSelector("input[name=\"postcode\"]"), "99150");
        type(By.cssSelector("input[name=\"city\"]"), "City" + input);
        selectItemByTwoSteps(By.className("select2-selection"), By.className("select2-results__options"), "United States");
        type(By.cssSelector("input[name=\"email\"]"), "mail" + input + "@mail.com");
        type(By.cssSelector("input[name=\"phone\"]"), "+1" + input);
        type(By.cssSelector("input[name=\"password\"]"), input);
        type(By.cssSelector("input[name=\"confirmed_password\"]"), input);
    }

    public void fillLoginForm() {
        click(By.cssSelector("input[name=email]"));
        type(By.cssSelector("input[name=email]"), "mail" + random + "@mail.com");
        type(By.cssSelector("input[name=password]"), random);
    }

    public void selectItemByTwoSteps(By locatorOne, By LocatorTwo, String select) {
        click(locatorOne);
        List<WebElement> itemsList = driver.findElements(LocatorTwo);
        for (WebElement item : itemsList) {
            item.findElement(By.xpath("./li[contains(text(),'" + select +"')]")).click();
        }
    }

    public void logout() {
        pause();
        click(By.linkText("Logout"));
    }

    /*public void makeVisible(WebElement item) {
        String s = item.getAttribute("outerHTML");
        if (isStringExist(s, "hidden")) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].type=visible", item);
        }
    }*/

    public void fillProductGeneral(String input) {
        pause();
        click(By.cssSelector("input[name=status]"));
        type(By.cssSelector("input[name=\"name[en]\"]"), "Gold Duck" + input);
        type(By.cssSelector("input[name=\"code\"]"), input);
        click(By.cssSelector("input[name=\"categories[]\"]"));
        click(By.cssSelector("input[data-name=\"Rubber Ducks\"]"));
        clickProductGroupsCheckBox(2);
        type(By.cssSelector("input[name=\"quantity\"]"), "1");
        attach(By.cssSelector("input[name=\"new_images[]\"]"), new File("src/test/resources/photo.png"));
        type(By.cssSelector("input[name=\"date_valid_from\"]"), "10.10.2020");
    }

    public void clickProductGroupsCheckBox(int checkBoxIndex) {
        List<WebElement> rows = getElsByTwoStep(By.xpath("//strong[contains(text(), 'Gender')]/../../.."), By.tagName("tr"));
        List<WebElement> tdToClick = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            if (i == 0) {
            } else {
                WebElement el = rows.get(i).findElement(By.cssSelector("input[type=\"checkbox\"]"));
                tdToClick.add(el);
            }
        }
        try {
            tdToClick.get(checkBoxIndex).click();
        } catch (IndexOutOfBoundsException ex) {
            tdToClick.get(0).click();
        }
    }

    public void attach (By locator, File file) {
        if (file != null){
            driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    public void fillProductInformation(String input) {
        click(By.linkText("Information"));
        pause();
        click(By.cssSelector("select[name=\"manufacturer_id\"]"));
        click(By.xpath("//option[contains(text(),'ACME Corp.')]"));
        type(By.cssSelector("input[name=\"keywords\"]"), input);
        type(By.cssSelector("input[name=\"short_description[en]\"]"), input);
        type(By.className("trumbowyg-editor"), input);
        type(By.cssSelector("input[name=\"head_title[en]\"]"), input);
        type(By.cssSelector("input[name=\"meta_description[en]\"]"), input);
    }

    public void fillProductPrices() {
        click(By.linkText("Prices"));
        pause();
        type(By.cssSelector("input[name=\"purchase_price\"]"), "10");
        click(By.cssSelector("select[name=\"purchase_price_currency_code\"]"));
        click(By.xpath("//option[contains(text(),'US Dollars')]"));
        type(By.cssSelector("input[name=\"gross_prices[USD]\"]"), "20");
    }

    public boolean isProductExist() {
        return isElementPresent(driver, By.linkText("Gold Duck" + random));
    }

    public void pause() {
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addFirstProductFromList(By locator) {
        List<WebElement> elems = getElsByTwoStep(By.cssSelector("div.middle > div.content"), locator);
        elems.get(0).findElement(By.cssSelector("li:first-child")).click();
        List<WebElement> elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        String quantity = elQuantity.get(0).getAttribute("textContent");
        if (isElementPresent(driver, By.cssSelector("select[name=\"options[Size]\"]"))) {
            click(By.cssSelector("select[name=\"options[Size]\"]"));
            click(By.cssSelector("option[value=\"Small\"]"));
        }
        click(By.cssSelector("button[name='add_cart_product']"));
        wait.until(ExpectedConditions.attributeToBe(elQuantity.get(0), "textContent", strPlusInt(quantity, 1) ));
        elQuantity = getElsByTwoStep(By.id("cart"), By.cssSelector("span[class=\"quantity\"]"));
        String quantityAfter = elQuantity.get(0).getAttribute("textContent");
        Assert.assertTrue(Integer.parseInt(quantityAfter) > Integer.parseInt(quantity));
    }

    public void removeProductFromCart() {
        List<WebElement> shortcuts = getElsByTwoStep(By.id("box-checkout-cart"), By.className("shortcut"));
        for (WebElement el : shortcuts) {
            WebElement summary = driver.findElement(By.id("box-checkout-summary"));
            List<WebElement> shortcutsCurrent = getElsByTwoStep(By.id("box-checkout-cart"), By.className("shortcut"));
            if (shortcutsCurrent.size() > 0) {
                shortcutsCurrent.get(0).click();
            }
            click(By.cssSelector("button[name=\"remove_cart_item\"]"));
            wait.until(ExpectedConditions.stalenessOf(summary));
        }
    }

    public String strPlusInt(String one, int two) {
        int o = Integer.parseInt(one);
        int r = o + two;
        String result = Integer.toString(r);
        return result;
    }

    public void checkout() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Checkout »")));
        click(By.linkText("Checkout »"));
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}
