package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MyFirstTest {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new FirefoxDriver();
    }

    @Test
    public void myFirstTest() {
        driver.navigate().to("http://www.google.com");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
