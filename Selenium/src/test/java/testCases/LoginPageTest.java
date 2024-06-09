package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashBoardPage;
import pages.LoginPage;
import setupBase.SetupBrowser;

public class LoginPageTest {
    WebDriver driver;
    LoginPage loginPage;
    DashBoardPage dashBoardPage;
    SetupBrowser setupBrowser;

    @BeforeMethod
    void setup(){
        setupBrowser = new SetupBrowser();
        driver =  setupBrowser.getDriver();
        loginPage = new LoginPage(driver);
        dashBoardPage =  new DashBoardPage(driver);
    }

    @Test
    void loginPage(){
        loginPage.login("demo","demo");
        boolean b = dashBoardPage.userNameDisplay();
        System.out.println(b);
        String getTitle = dashBoardPage.getTitleOfDashBoardPage();
        Assert.assertEquals(getTitle,"Dashboard","Please check for the title");
    }

    @AfterMethod
    void tearDown(){
        driver.quit();
    }
}
