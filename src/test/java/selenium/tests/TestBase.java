package selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import selenium.app.Application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static ThreadLocal<Application> tlApp = new ThreadLocal<>();
    public Application app;
    private String random;

    @Before
    public void start() {
        if (tlApp.get() != null) {
            app = tlApp.get();
            return;
        }
        app = new Application();
        tlApp.set(app);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> { app.driver.quit(); app.driver = null; }));

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
    }
    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            //System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            //System.out.println(by +  " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            //System.out.println(throwable);
        }
/*
        public void beforeClickOn(WebElement element, WebDriver driver) {
            System.out.println(element.getAttribute("value"));
        }

        public void afterClickOn(WebElement element, WebDriver driver) {
            System.out.println(element.getAttribute("value") + " found");
        }

 */
    }
    
    boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }


    boolean areElementsPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }
    //здесь оставлено, чтобы не переписывать другие методы
    public List<WebElement> getElsByTwoStep(By locatorOne, By locatorTwo) {
        WebElement elem = app.driver.findElement(locatorOne);
        return elem.findElements(locatorTwo);
    }
    //здесь оставлено, чтобы не переписывать другие методы
    public List<WebElement> getElsByThreeStep(By locatorOne, By locatorTwo, By locatorThree) {
        WebElement elem2 = app.driver.findElement(locatorOne);
        WebElement elemInElem = elem2.findElement(locatorTwo);
        return elemInElem.findElements(locatorThree);
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

    protected void click(By locator) {//здесь оставлено, чтобы не переписывать другие методы
        app.driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null){
            String existingText = app.driver.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)) {
                app.driver.findElement(locator).clear();
                app.driver.findElement(locator).sendKeys(text);
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
        List<WebElement> itemsList = app.driver.findElements(LocatorTwo);
        for (WebElement item : itemsList) {
            item.findElement(By.xpath("./li[contains(text(),'" + select +"')]")).click();
        }
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
            app.driver.findElement(locator).sendKeys(file.getAbsolutePath());
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
        return isElementPresent(app.driver, By.linkText("Gold Duck" + random));
    }

    public void pause() {//здесь оставлено, чтобы не переписывать другие методы
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    public List browserLogs() {
        List log = new ArrayList();
        for (LogEntry l : app.driver.manage().logs().get("browser").getAll()) {
            log.add("[" + l.getTimestamp() + "] [" + l.getLevel() + "] " + l.getMessage());
        }
        return log;
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}
