package selenium;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

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

        //driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        driver = new InternetExplorerDriver();
        tlDriver.set(driver);
        wait = new WebDriverWait(driver, 10);

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

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}
