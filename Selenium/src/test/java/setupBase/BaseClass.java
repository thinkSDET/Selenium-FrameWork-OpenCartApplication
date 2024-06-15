package setupBase;

import commonMethods.ActionsMethods;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseClass extends ActionsMethods {

    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();

    /**
     *  set the driver
     */
    @BeforeTest
    public static void setDriver(){
        WebDriver driver = BrowserManager.browserSetup("chrome");
        driverThreadLocal.set(driver);
        System.out.println("Before Test Thread-->"+ Thread.currentThread().getId());
        //get URL
        getDriver().manage().window().maximize();
        implicitWait(getDriver());
        getDriver().manage().deleteAllCookies();
        getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    /**
     * get the driver
     * @return
     */
    public static WebDriver getDriver(){
       return driverThreadLocal.get();
    }

    @AfterTest
    public void tearDown(){
        getDriver().quit();
        System.out.println("After Test Thread ID-->"+Thread.currentThread().getId());
        driverThreadLocal.remove();
    }
}
