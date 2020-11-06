package selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

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
            //driver.findElement(locator);
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
        int i = one.compareTo(two);
        if (i > 0) {
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
        click(By.cssSelector("input[name=tax_id]"));
        type(By.cssSelector("input[name=firstname]"),"Vasya" + input);
        type(By.cssSelector("input[name=lastname]"), "Vaskin" + input);
        type(By.cssSelector("input[name=address1]"), "ulitsa" + input);
        type(By.cssSelector("input[name=postcode]"), "99150");
        type(By.cssSelector("input[name=city]"), "City" + input);
        selectItemByTwoSteps(By.className("select2-selection"), By.className("select2-results__options"), "United States");
        type(By.cssSelector("input[name=email]"), "mail" + input + "@mail.com");
        type(By.cssSelector("input[name=phone]"), "+1" + input);
        type(By.cssSelector("input[name=password]"), input);
        type(By.cssSelector("input[name=confirmed_password]"), input);
    }

    public void fillLoginForm() {
        click(By.cssSelector("input[name=email]"));
        type(By.cssSelector("input[name=email]"), "mail" + random + "@mail.com");
        type(By.cssSelector("input[name=password]"), random);
    }

    public void selectItemByTwoSteps(By clickLocator, By selectLocator, String select) {
        click(clickLocator);
        List<WebElement> itemsList = driver.findElements(selectLocator);
        for (WebElement item : itemsList) {
            item.findElement(By.xpath("./li[contains(text(),'" + select +"')]")).click();
        }
    }

    public void logout() {
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        click(By.linkText("Logout"));
    }

    /*public void makeVisible(WebElement item) {
        String s = item.getAttribute("outerHTML");
        if (isStringExist(s, "hidden")) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].type=visible", item);
        }
    }*/

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}
