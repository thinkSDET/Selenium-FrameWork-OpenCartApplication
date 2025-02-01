package setupBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserManager {

    public static WebDriver browserSetup(String browserName){
        WebDriver driver = null;
        switch (browserName.toLowerCase()){
            case "chrome" :
                ChromeOptions options = new ChromeOptions();
               // options.addArguments("--headless=new");  // Run in headless mode (needed for GitHub Actions)
                options.addArguments("--no-sandbox");  // Bypass container security
                options.addArguments("--disable-dev-shm-usage");  // Prevent shared memory issues
                options.addArguments("--remote-allow-origins=*");  // Allow cross-origin requests
                driver = new ChromeDriver(options);
                break;
            case "firefox" :
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Please enter correct browser");
        }
        return  driver;

    }
}
