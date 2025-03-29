/*
 * *
 *  * Copyright (c) 2025 [thinkSDET]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package testBase;

import customExcpetion.TestAutomationException;
import org.openqa.selenium.WebDriver;
import utils.BaseLogger;

public class DriverManager {

    /**
     * A driverThreadLocal variable ensures that each thread (which corresponds to each test in parallel test execution) gets its own separate WebDriver instance.
     * This prevents conflicts when running tests in parallel.
     */
    private static ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();

    /**
     * Initialize the WebDriver instance and store it in ThreadLocal.
     */
    protected static void initializeDriver() {
        try {
            String browser = ConfigManager.getBrowser(); // Use ConfigManager for browser
            WebDriver driver = BrowserManager.initializeBrowser(browser);   // Using Factory Pattern
            BaseLogger.info("WedDriver is successfully initialized");
            driverThreadLocal.set(driver);
        } catch (Exception e){
            BaseLogger.error("Error initializing WebDriver: " + e.getMessage());
            throw new TestAutomationException("WebDriver initialization failed.", e);
        }
    }

    /**
     * get the driver
     * @return
     */
    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }

    protected  static void remove(){
        driverThreadLocal.remove();
    }
}
