/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.ConfigManager;
import testBase.PageFactory;
import testBase.UIBaseTest;

public class AdminPageTest extends UIBaseTest {

    PageFactory pageFactory;
    @BeforeMethod(alwaysRun = true)
    void setup(){
        PageFactory.setDriver(getDriver());
        PageFactory.setInstance();            //PageFactory instance initialize karo
        pageFactory = PageFactory.getInstance(); //Ab safely instance mil jayega
        pageFactory.loginPage().login(ConfigManager.getUserName(), ConfigManager.getPassword());
    }

    @Test
    public void test_01_verify_helpIconVisibleOn_AdminPage(){

        Assert.assertTrue(pageFactory.adminPage().isHelpIconVisible(),"Please check help icon does not displayed");
    }

    @Test(groups = {"smoke"})
    public void test_02_verify_AddUser_AccountPage(){
       Assert.assertEquals(pageFactory.adminPage().verifyUserAccountPageOpen(),"","Please check the app title on User Account page");
    }
}
