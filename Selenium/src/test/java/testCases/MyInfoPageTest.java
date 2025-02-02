package testCases;

import dataClasses.PersonalDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import setupBase.BaseClass;
import setupBase.PageFactory;
import testData.PersonalDetailsTestData;

public class MyInfoPageTest extends BaseClass {
    PageFactory pageFactory;
    PersonalDetails personalDetails;

    @BeforeTest
    void setup(){
        pageFactory =  new PageFactory(getDriver());
        pageFactory.loginPage().login("Admin","admin123");
        personalDetails =  PersonalDetailsTestData.getPersonalDetailsData();
    }
    @Test(enabled = false)
    void test01_verifyPersonalDetailsOnMyInfoPage() throws InterruptedException {
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickJobOption();
        Assert.assertTrue(pageFactory.myInfoPage().jobDetailsVisibility(),"Please have a look for the title");
    }
    @Test
    void test02_verifyUserIsAbleToFillPersonalDetails(){
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickPersonalDetailsOption();
        pageFactory.personalDetailsPage().fillPersonalDetails(personalDetails);
        Assert.assertFalse(false);
    }
}
