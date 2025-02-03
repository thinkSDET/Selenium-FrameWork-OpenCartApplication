package setupBase;

import commonMethods.WaitManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseClass {

    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();
    private  static String browser = System.getProperty("browser", "chrome"); // Default to Chrome if not set

    /**
     *  set the driver
     */
    @BeforeTest
    public static void setDriver(){
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";  // Ensure default to Chrome if not set
        }

        WebDriver driver = BrowserManager.browserSetup(browser);
        driverThreadLocal.set(driver);
        System.out.println("Before Test Thread-->"+ Thread.currentThread().getId());

        // Get URL
        getDriver().manage().window().maximize();
        WaitManager.implicitWait(getDriver());
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
