package setupBase;

import commonMethods.WaitManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();
    private  static String browser = System.getProperty("browser", "chrome"); // Default to Chrome if not set

    /**
     *  set the driver
     */
    @BeforeMethod
    public static void setDriver(){
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";  // Ensure default to Chrome if not set
        }

        WebDriver driver = BrowserManager.initializeBrowser(browser);
        driverThreadLocal.set(driver);
        System.out.println("Before Method Thread-->"+ Thread.currentThread().getId());

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

    @AfterMethod
    public void tearDown(){
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit(); // Quit the driver after the test is finished
            System.out.println("After Test Thread ID-->"+Thread.currentThread().getId());
            driverThreadLocal.remove(); // Clean up the ThreadLocal reference
        }
    }
}
