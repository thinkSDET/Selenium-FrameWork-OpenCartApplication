/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testBase;

import customExcpetion.FrameworkException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.BaseLogger;

import java.time.Duration;

public class UIBaseTest {
    /**
     * A driverThreadLocal variable ensures that each thread (which corresponds to each test in parallel test execution) gets its own separate WebDriver instance.
     * This prevents conflicts when running tests in parallel.
     */
    protected static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();
    /**
     * Initialize the WebDriver instance and store it in ThreadLocal.
     */
    private static void initializeDriver() {
        try {
            String browser = ConfigManager.getBrowser(); // Use ConfigManager for browser
            WebDriver driver = BrowserManager.initializeBrowser(browser);   // Using Factory Pattern
            BaseLogger.info("WedDriver is successfully initialized");
            driverThreadLocal.set(driver);
        } catch (Exception e){
            BaseLogger.error("Error initializing WebDriver: " + e.getMessage());
            throw new FrameworkException("WebDriver initialization failed.", e);
        }
    }

    /**
     * Set up the WebDriver before each test method.
     *
     * Note : @BeforeMethod should not be static because they operate on instance methods.
     */
    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        initializeDriver(); // Call the new method
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().deleteAllCookies();
            driver.get(ConfigManager.getBaseUrl());
        }
    }

    /**
     * get the driver
     * @return
     */
    public static WebDriver getDriver(){
       return driverThreadLocal.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit(); // Quit the driver after the test is finished
            BaseLogger.info("---Driver is cosed---");
            driverThreadLocal.remove(); // Clean up the ThreadLocal reference
        }
    }
}
