package testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.logging.Logger;

public class BrowserManager {

    private static final Logger LOGGER = Logger.getLogger(BrowserManager.class.getName());

    public static WebDriver initializeBrowser(String browserName) {
        WebDriver driver;
        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                driver = setupChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserName + ". Supported browsers: Chrome, Firefox.");
        }
        LOGGER.info("Browser initialized: " + browserName);
        return driver;
    }

    private static WebDriver setupChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new");  // Uncomment below if running in headless mode
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}
