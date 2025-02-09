package testBase;

import commonMethods.WaitManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import testBase.baseUtils.LoggerUtil;

public class UIBaseTest {

    /**
     * A driverThreadLocal variable ensures that each thread (which corresponds to each test in parallel test execution) gets its own separate WebDriver instance.
     * This prevents conflicts when running tests in parallel.
     */
    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();
    private static final LoggerUtil logger = LoggerUtil.getLogger(UIBaseTest.class);

    /**
     * Initialize the WebDriver instance and store it in ThreadLocal.
     */
    private static void initializeDriver() {
        String browser = ConfigManager.getBrowser(); // Use ConfigManager for browser
        WebDriver driver = BrowserManager.initializeBrowser(browser);
        logger.info("WedDriver is successfully initialized");
        driverThreadLocal.set(driver);
    }

    /**
     * Set up the WebDriver before each test method.
     *
     * Note : @BeforeMethod should not be static because they operate on instance methods.
     */
    @BeforeMethod
    public void setDriver() {
        initializeDriver(); // Call the new method
        logger.info("Before Method Thread--> " + Thread.currentThread().getId());
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
            logger.info("After Test Thread ID-->"+Thread.currentThread().getId());
            driverThreadLocal.remove(); // Clean up the ThreadLocal reference
        }
    }
}
