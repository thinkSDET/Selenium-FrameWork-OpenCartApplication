package testBase;

import navigationPages.MyInfoPageNavigation;
import org.openqa.selenium.WebDriver;
import pages.DashBoardPage;
import pages.LoginPage;
import pages.MyInfoPage;
import pages.myInfo.PersonalDetailsPage;

public class PageFactory {
  private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Set the WebDriver for the current thread
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    // Get the WebDriver for the current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    public LoginPage loginPage() {
        return new LoginPage(getDriver());
    }

    public DashBoardPage dashBoardPage() {
        return new DashBoardPage(getDriver());
    }

    public MyInfoPageNavigation myInfoPageNavigation() {
        return new MyInfoPageNavigation(getDriver());
    }

    public MyInfoPage myInfoPage() {
        return new MyInfoPage(getDriver());
    }

    public PersonalDetailsPage personalDetailsPage() {
        return new PersonalDetailsPage(getDriver());
    }
}
