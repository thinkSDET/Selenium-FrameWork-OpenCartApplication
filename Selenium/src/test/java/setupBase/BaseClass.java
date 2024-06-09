package setupBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseClass extends Waits {

    protected ThreadLocal<WebDriver> driverThreadLocal =  new ThreadLocal<>();

    /**
     *  set the driver
     */
    public void set(){
        WebDriver driver = new FirefoxDriver();
        driverThreadLocal.set(driver);
    }

    /**
     * get the driver
     * @return
     */
    public WebDriver get(){
       return driverThreadLocal.get();
    }

    /**
     * launch the browser
     * @return
     */
    public void launchBrowser(){
        set();
        get().manage().window().maximize();
        implicitWait(get());
        get().manage().deleteAllCookies();
        get().get("https://demo.opencart.com/admin/");
    }
}
