package setupBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseClass extends Waits {

    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();

    /**
     *  set the driver
     */
    public void set(){
        WebDriver driver = new ChromeDriver();
        driverThreadLocal.set(driver);
    }

    /**
     * get the driver
     * @return
     */
    public WebDriver get(){
       return driverThreadLocal.get();
    }

    /**
     * launch the browser
     * @return
     */
    @BeforeSuite
    public void launchBrowser(){
        set();
        get().manage().window().maximize();
        implicitWait(get());
        get().manage().deleteAllCookies();
        get().get("https://demo.opencart.com/admin/");
    }
}
