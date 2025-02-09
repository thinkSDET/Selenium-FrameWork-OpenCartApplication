package testBase;

import customExcpetion.FrameworkException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import testBase.baseUtils.LoggerUtil;

import java.util.logging.Logger;

public class BrowserManager {

    private static final LoggerUtil logger = LoggerUtil.getLogger(BrowserManager.class);
    private static final boolean HEADLESS_MODE = Boolean.parseBoolean(System.getProperty("HEADLESS", "false")); // Default is false (headed mode)

    public static WebDriver initializeBrowser(String browserName) {
        WebDriver driver;
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                driver = setupChromeDriver();
                break;
            case "firefox":
            case "ff":  // Both "firefox" and "ff" will result in the same action
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                // CustomException
                throw new FrameworkException("Invalid browser name: " + browserName + ". Supported browsers: Chrome, Firefox.");
        }
        logger.info("Browser initialized: " + browserName);
        return driver;
    }

    private static WebDriver setupChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        if (HEADLESS_MODE) {
            options.addArguments("--headless=new");  // Run in headless mode
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

 /*   private static WebDriver setupFirefoxDriver() {

    }*/
}
