package testCases;

import commonMethods.ReusableMethods;
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
    void test_01_verifyUserCanLoginAsAdminAndNavigateToDashBoardPage(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        Assert.assertTrue( pageFactory.dashBoardPage().dashBoardDisplay());
        Assert.assertEquals(ReusableMethods.getTitleOfPage(getDriver()),"OrangeHRM","Please check for the title");
    }
}
