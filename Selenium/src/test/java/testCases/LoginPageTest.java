package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.TestBase;
import testBase.PageFactory;
import testData.LoginTestData;

public class LoginPageTest extends TestBase {
    PageFactory pageFactory;

    @BeforeMethod
    void setup(){
        PageFactory.setDriver(getDriver());
        pageFactory = new PageFactory();
    }

    @Test(dataProvider = "loginWithAdmin",dataProviderClass = LoginTestData.class)
    void test01_verifyUserCanLoginAsAdminAndNavigateToDashBoardPage(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        pageFactory.dashBoardPage().userNameDisplay();
        Assert.assertEquals(pageFactory.dashBoardPage().getTitleOfDashBoardPage(),"OrangeHRM","Please check for the title");
    }
    /**
     * quit the driver, after the execution of test case
     */
}
