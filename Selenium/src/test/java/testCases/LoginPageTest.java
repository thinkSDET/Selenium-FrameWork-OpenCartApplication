package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.UIBaseTest;
import testBase.PageFactory;
import testData.LoginTestData;

public class LoginPageTest extends UIBaseTest {
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
