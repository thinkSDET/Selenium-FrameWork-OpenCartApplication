/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testBase;

import org.openqa.selenium.WebDriver;
import pages.AdminPage;
import pages.DashBoardPage;
import pages.LoginPage;
import pages.MyInfoPage;
import pages.myInfo.PersonalDetailsPage;
import pages.navigationPages.MyInfoPageNavigation;

public class PageFactoryManager {
    private PageFactoryManager() {
// Constructor should be private to enforce singleton per thread
    }

    // Thread-safe WebDriver instance
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    // PageFactory should be thread-safe too
    private static ThreadLocal<PageFactoryManager> pageFactoryThreadLocal = new ThreadLocal<>();

    // Set the WebDriver for the current thread
    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    // Get the WebDriver for the current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setInstance() {
        pageFactoryThreadLocal.set(new PageFactoryManager()); //  Set instance before calling getInstance()
    }

    public static PageFactoryManager getInstance() {
        if (pageFactoryThreadLocal.get() == null) {
            throw new IllegalStateException("PageFactory instance is not set. Call setInstance() first!");
        }
        return pageFactoryThreadLocal.get(); // Now it will always return a valid instance
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

    public AdminPage adminPage() {
        return new AdminPage(getDriver());
    }
}
