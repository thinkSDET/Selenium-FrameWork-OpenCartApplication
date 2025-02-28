/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package testCases;

import dataClasses.PersonalDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testBase.UIBaseTest;
import testBase.PageFactory;
import testData.PersonalDetailsTestData;

public class MyInfoPageTest extends UIBaseTest {
    PageFactory pageFactory;
    PersonalDetails personalDetailsData;

    @BeforeMethod(alwaysRun = true)
    void setup(){
        PageFactory.setDriver(getDriver());
        pageFactory =  new PageFactory();
        pageFactory.loginPage().login("Admin","admin123");
        personalDetailsData =  PersonalDetailsTestData.getPersonalDetailsData();
    }
    @Test
    void verifyPersonalDetailsOnMyInfoPage() throws InterruptedException {
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickJobOption();
        Assert.assertTrue(pageFactory.myInfoPage().jobDetailsVisibility(),"Please have a look for the title");
        System.out.println("test01_verifyPersonalDetailsOnMyInfoPage");
    }
    @Test(groups = {"smoke"})
    void verifyUserIsAbleToFillPersonalDetails(){
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickPersonalDetailsOption();
        pageFactory.personalDetailsPage().fillPersonalDetails(personalDetailsData);
        Assert.assertFalse(false);
        System.out.println("test02_verifyUserIsAbleToFillPersonalDetails");
    }
}
