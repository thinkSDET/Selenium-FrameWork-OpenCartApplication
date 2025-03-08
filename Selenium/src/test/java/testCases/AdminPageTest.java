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
import testBase.ConfigManager;
import testBase.PageFactory;
import testBase.UIBaseTest;

public class AdminPageTest extends UIBaseTest {

    PageFactory pageFactory;
    @BeforeMethod(alwaysRun = true)
    void setup(){
        PageFactory.setDriver(getDriver());
        pageFactory = new PageFactory();
        pageFactory.loginPage().login(ConfigManager.getUserName(),ConfigManager.getPassword());
    }

    @Test
    public void test_01_verify_helpIconVisibleOn_AdminPage(){
       boolean helpIconDisplayed  = pageFactory.adminPage().helpIconBtn.isDisplayed();
        Assert.assertTrue(helpIconDisplayed,"Please check help icon does not displayed");
    }

    @Test(groups = {"smoke"})
    public void test_02_verify_AddUser_AccountPage(){
       Assert.assertEquals(pageFactory.adminPage().verifyUserAccountPageOpen(),"OrangeHRM","Please check the app title on User Account page");
    }
}
