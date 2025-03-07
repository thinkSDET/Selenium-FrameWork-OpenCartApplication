/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testBase;

import customExcpetion.FrameworkException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.Logger;


public class BrowserManager {

    private static final Logger logger = Logger.getLogger(BrowserManager.class);
    private static final boolean isHeadless = ConfigManager.isHeadlessMode();

    public static WebDriver initializeBrowser(String browserName) {
        WebDriver driver;
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                driver = setupChromeDriver();
                break;
            case "firefox":
            case "ff":  // Both "firefox" and "ff" will result in the same action
                driver = setupFirefoxDriver();
                break;
            case "edge":
                driver = setupEdgeDriver();
                break;
            default:
                // CustomException
                throw new FrameworkException("Invalid browser name: " + browserName + ". Supported browsers: Chrome, Firefox.");
        }
        logger.info("Browser initialized: " + browserName + " (Headless: " + isHeadless + ")");
        return driver;
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
