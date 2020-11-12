package selenium.app;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Helpers {
    protected EventFiringWebDriver driver;

    public Helpers(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public String strPlusInt(String one, int two) {
        int o = Integer.parseInt(one);
        int r = o + two;
        String result = Integer.toString(r);
        return result;
    }

    public void pause() {
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

}
