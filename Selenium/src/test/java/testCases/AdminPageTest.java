/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package testCases;

import common.CommonMethods;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.PageFactory;
import testBase.UIBaseTest;

public class AdminPageTest extends UIBaseTest {

    PageFactory pageFactory;
    @BeforeMethod(alwaysRun = true)
    void setup(){
        PageFactory.setDriver(getDriver());
        pageFactory = new PageFactory();
        pageFactory.loginPage().login("Admin","admin123");
    }

    @Test
    public void verify_helpIconVisibleOnAdminPage(){
       boolean helpIconDisplayed  = pageFactory.adminPage().helpIconBtn.isDisplayed();
        Assert.assertTrue(helpIconDisplayed,"Please check help icon does not displayed");
    }

    @Test(groups = {"smoke"})
    public void verify_AddUserAccountPage(){
       Assert.assertEquals(pageFactory.adminPage().verifyUserAccountPageOpen(),"OrangeHRM","Please check the app title on User Account page");
    }
}
