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
                options.addArguments("--user-data-dir=/tmp/selenium-profile-" + System.currentTimeMillis()); // Unique directory
                options.addArguments("--disable-dev-shm-usage"); // Helps avoid crashes in Docker/Linux
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
