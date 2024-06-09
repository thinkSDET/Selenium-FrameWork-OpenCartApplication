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
        pageFactory =  new PageFactory(get());
    }

    @Test (dataProvider = "loginWithAdmin",dataProviderClass = LoginTestData.class)
    void verifyUserCanLoginAsAdminAndNavigateToDashBoardPage(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        pageFactory.dashBoardPage().userNameDisplay();
        String getTitle = pageFactory.dashBoardPage().getTitleOfDashBoardPage();
        Assert.assertEquals(getTitle,"Dashboard","Please check for the title");
    }

    @Test(dataProvider = "WrongUserNameAndPassword",dataProviderClass = LoginTestData.class)
    void verifyLoginWithWrongUserNameAndPassword(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        String toastMessage = pageFactory.loginPage().getToastMessage();
        Assert.assertEquals(toastMessage,"No match for Username and/or Password.","Message is not correct please check");
    }
    /**
     * quit the driver, after the execution of test case
     */
    @AfterMethod
    void tearDown(){
        get().quit();
    }
}
