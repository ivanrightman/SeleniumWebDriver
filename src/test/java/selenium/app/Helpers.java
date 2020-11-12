package selenium.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Helpers {
    protected WebDriver driver;

    public Helpers(WebDriver driver) {
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


}
