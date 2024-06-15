package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import setupBase.BaseClass;
import setupBase.PageFactory;

public class MyInfoPageTest extends BaseClass {
    PageFactory pageFactory;
    @BeforeMethod
    void setup(){
        pageFactory =  new PageFactory(getDriver());
        pageFactory.loginPage().login("Admin","admin123");
    }
    @Test
    void test01_verifyPersonalDetailsOnMyInfoPage() throws InterruptedException {
        pageFactory.myInfoPageNavigation().clickOnMyInfoOption();
        pageFactory.myInfoPage().clickJobOption();
        Assert.assertTrue(pageFactory.myInfoPage().jobDetailsVisibility(),"Please have a look for the title");
    }
}
