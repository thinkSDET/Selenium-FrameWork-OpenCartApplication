package setupBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserManager {

    public static WebDriver browserSetup(String browserName){
        WebDriver driver = null;
        switch (browserName.toLowerCase()){
            case "chrome" :
                driver = new ChromeDriver();
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
