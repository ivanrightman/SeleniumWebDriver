package selenium.tests;
import com.tngtech.java.junit.dataprovider.DataProvider;
import selenium.model.Product;

public class DataProviders {

    @DataProvider
    public static Object[][] products() {
        return new Object[][] {
                {Product.newEntity()
                        .withBlock("Most Popular")
                        .withQuantity(1)
                        .withSize("Small")
                }
        };
    }
}
