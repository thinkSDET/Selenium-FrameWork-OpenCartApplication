/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testCases;

import common.CommonMethods;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.UIBaseTest;
import testBase.PageFactory;
import testData.LoginTestData;

public class LoginPageTest extends UIBaseTest {
    PageFactory pageFactory;

    @BeforeMethod(alwaysRun = true)
    void setup(){
        PageFactory.setDriver(getDriver());
        pageFactory = new PageFactory();
    }

    @Test(dataProvider = "loginWithAdmin",dataProviderClass = LoginTestData.class)
    void verifyUserCanLoginAsAdminAndNavigateToDashBoardPage(String userName, String password){
        pageFactory.loginPage().login(userName,password);
        Assert.assertTrue( pageFactory.dashBoardPage().dashBoardDisplay());
        Assert.assertEquals(CommonMethods.getTitleOfPage(),"OrangeHRM","Please check for the title");
    }

    @Test(groups = {"smoke"})
    void test_02_verifyUserCanNotLoginAsInvalidUserNameAndPassword(){
        pageFactory.loginPage().login("inValidUN","inValidPwd");
        Assert.assertEquals(pageFactory.loginPage().getInvalidCredentialValidationMessage(),"Invalid credentials");
    }
}
