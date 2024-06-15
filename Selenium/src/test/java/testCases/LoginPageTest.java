package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import setupBase.BaseClass;
import setupBase.PageFactory;
import testData.LoginTestData;

public class LoginPageTest extends BaseClass {
    PageFactory pageFactory;

    @BeforeMethod
    void setup(){
        pageFactory =  new PageFactory(getDriver());
    }

    @Test(dataProvider = "loginWithAdmin",dataProviderClass = LoginTestData.class)
    void test01_verifyUserCanLoginAsAdminAndNavigateToDashBoardPage(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        pageFactory.dashBoardPage().userNameDisplay();
        String getTitle = pageFactory.dashBoardPage().getTitleOfDashBoardPage();
        Assert.assertEquals(getTitle,"OrangeHRM","Please check for the title");
    }
    /**
     * quit the driver, after the execution of test case
     */
}
