/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testBase;

import customExcpetion.TestAutomationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.BaseLogger;


public class BrowserManager {

    private static final boolean isHeadless = ConfigManager.isHeadlessMode();

    // Private constructor to prevent instantiation
    private BrowserManager() {

    }
    //Protected members are accessible within the same package and to subclasses outside the package.
    /**
     * Factory method to create a WebDriver instance based on the given browser.
     */
    protected static WebDriver initializeBrowser(String browserName) {
        BaseLogger.info("Launching browser: " + browserName + " | Headless: " + isHeadless);
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                return setupChromeDriver();
            case "firefox":
            case "ff":  // Both "firefox" and "ff" will result in the same action
                return setupFirefoxDriver();
            case "edge":
                return  setupEdgeDriver();
            default:
                // CustomException
                throw new TestAutomationException("Invalid browser name: " + browserName + ". Supported browsers: Chrome, Firefox.");
        }
    }

    private static WebDriver setupChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.addArguments("--headless=new");  // Run in headless mode
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static WebDriver setupFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless) {
            options.addArguments("--headless"); // Headless mode for Firefox
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver setupEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        if (isHeadless) {
            options.addArguments("--headless=new"); // Headless mode for Edge
        }
        return new EdgeDriver(options);
    }
}
