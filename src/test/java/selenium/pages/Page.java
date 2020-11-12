package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.app.Helpers;

import java.util.List;
import java.util.function.Function;


public class Page extends Helpers {
    protected EventFiringWebDriver driver;
    protected WebDriverWait wait;

    public Page(EventFiringWebDriver driver) {
        super(driver);
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);
    }

    public void logout() {
        driver.findElement(By.linkText("Logout")).click();
    }

    public void waitSomething(Function function) {
        wait.until(function);
    }



}
