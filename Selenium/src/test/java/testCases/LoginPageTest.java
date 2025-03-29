/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.UIBaseTest;
import testBase.PageFactoryManager;
import testData.LoginTestData;

import static testBase.DriverManager.getDriver;

public class LoginPageTest extends UIBaseTest {
    PageFactoryManager pageFactory;

    @BeforeMethod(alwaysRun = true)
    void setup(){
        PageFactoryManager.setDriver(getDriver());
        PageFactoryManager.setInstance();            //PageFactory instance initialize karo
        pageFactory = PageFactoryManager.getInstance(); //Ab safely instance mil jayega
    }

    @Test(dataProvider = "loginWithAdmin",dataProviderClass = LoginTestData.class)
    void test_01_verifyUserCanLoginAsAdminAndNavigateToDashBoardPage(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        Assert.assertTrue( pageFactory.dashBoardPage().dashBoardDisplay());
        Assert.assertEquals(pageFactory.loginPage().fetchPageTitle(),"OrangeHRM","Please check for the title");
    }

    @Test(groups = {"smoke"},dataProvider = "WrongUserNameAndPassword",dataProviderClass = LoginTestData.class)
    void test_02_verifyUserCanNotLoginAsInvalidUserNameAndPassword(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        Assert.assertEquals(pageFactory.loginPage().getInvalidCredentialValidationMessage(),"Invalid credentials");
    }
}
