package setupBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public class BaseClass extends Waits {

    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();

    /**
     *  set the driver
     */
    public static void set(){
        WebDriver driver = new ChromeDriver();
        driverThreadLocal.set(driver);
    }

    /**
     * get the driver
     * @return
     */
    public static WebDriver get(){
       return driverThreadLocal.get();
    }

    /**
     * launch the browser
     * @return
     */
    @BeforeTest
    public void launchBrowser(){
        set();
        get().manage().window().maximize();
        implicitWait(get());
        get().manage().deleteAllCookies();
        get().get("https://demo.opencart.com/admin/");
    }
}
