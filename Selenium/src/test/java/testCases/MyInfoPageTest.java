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

    @BeforeMethod
    void setup(){
        PageFactory.setDriver(getDriver());
        pageFactory =  new PageFactory();
        pageFactory.loginPage().login("Admin","admin123");
        personalDetailsData =  PersonalDetailsTestData.getPersonalDetailsData();
    }
    @Test
    void test01_verifyPersonalDetailsOnMyInfoPage() throws InterruptedException {
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickJobOption();
        Assert.assertTrue(false);
        Assert.assertTrue(pageFactory.myInfoPage().jobDetailsVisibility(),"Please have a look for the title");
        System.out.println("test01_verifyPersonalDetailsOnMyInfoPage");
    }
    @Test
    void test02_verifyUserIsAbleToFillPersonalDetails(){
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickPersonalDetailsOption();
        pageFactory.personalDetailsPage().fillPersonalDetails(personalDetailsData);
        Assert.assertFalse(true);
        System.out.println("test02_verifyUserIsAbleToFillPersonalDetails");
    }
}
