package testBase;

import commonMethods.WaitManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class UIBaseTest {

    /**
     * A driverThreadLocal variable ensures that each thread (which corresponds to each test in parallel test execution) gets its own separate WebDriver instance.
     * This prevents conflicts when running tests in parallel.
     */
    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();
    /**
     * If the browser property is not passed at runtime, it defaults to chrome.
     */
    private  static String browser = System.getProperty("browser", "chrome"); // Default to Chrome if not set

    /**
     * Initialize the WebDriver instance and store it in ThreadLocal.
     */
    private static void initializeDriver() {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";  // Ensure default to Chrome if not set
        }
        WebDriver driver = BrowserManager.initializeBrowser(browser);
        driverThreadLocal.set(driver);
    }

    /**
     * Set up the WebDriver before each test method.
     */
    @BeforeMethod
    public static void setDriver() {
        initializeDriver(); // Call the new method
        System.out.println("Before Method Thread--> " + Thread.currentThread().getId());

        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        WaitManager.implicitWait(driver);
        driver.manage().deleteAllCookies();
        driver.get(ConfigReader.getBaseUrl());
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
