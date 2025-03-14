/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testCases;

import dataClasses.PersonalDetails;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.ConfigManager;
import testBase.UIBaseTest;
import testBase.PageFactory;
import testData.PersonalDetailsTestData;

public class MyInfoPageTest extends UIBaseTest {
    PageFactory pageFactory;
    PersonalDetails personalDetailsData;

    @BeforeMethod(alwaysRun = true)
    void setup(){
        PageFactory.setDriver(getDriver());
        PageFactory.setInstance();            //PageFactory instance initialize karo
        pageFactory = PageFactory.getInstance(); //Ab safely instance mil jayega
        pageFactory.loginPage().login(ConfigManager.getUserName(), ConfigManager.getPassword());
        personalDetailsData =  PersonalDetailsTestData.getPersonalDetailsData();
    }
    @Test
    void test_01_verify_PersonalDetails_On_MyInfoPage() throws InterruptedException {
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickJobOption();
        Assert.assertTrue(pageFactory.myInfoPage().jobDetailsVisibility(),"Please have a look for the title");
    }
    @Test(groups = {"smoke"})
    void test_02_verify_UserIsAbleToFillPersonalDetails_On_MyInfoPage(){
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickPersonalDetailsOption();
        pageFactory.personalDetailsPage().fillPersonalDetails(personalDetailsData);
       Assert.assertFalse(true);
    }

    @AfterMethod(alwaysRun = true)
    void tearDown() {
        pageFactory.loginPage().logout(); // Ensures no session is carried forward
    }
}
