package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class adminLoginTest {
    private WebDriver driver;
    private FirefoxOptions options = new FirefoxOptions();

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

        //Задание 6. Научитесь запускать Firefox Nightly
        options
                .setBinary("C:\\Program Files\\Firefox Nightly\\firefox.exe");
        driver = new FirefoxDriver(options);
    }


    @Test
    public void myFirstTest() {
        driver.get("http://localhost/litecart/admin/.");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        driver.findElement(By.linkText("Appearence")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
