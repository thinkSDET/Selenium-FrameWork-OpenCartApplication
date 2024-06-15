package testCases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import setupBase.BaseClass;
import setupBase.PageFactory;

public class ReturnPageTest extends BaseClass {
    PageFactory pageFactory;
    @BeforeMethod
    void setup(){
        launchBrowser();
        pageFactory =  new PageFactory(get());
        pageFactory.loginPage().login("demo","demo");
    }
    @Test
    void test01_verifyReturnProductCount(){
        pageFactory.salesPageNavigation().expendSalesPage();
        pageFactory.returnsPageNavigation().clickOnReturns();
        pageFactory.returnsPage().enterProductName("iPhone");
        pageFactory.returnsPage().pressFilterButton();
    }
}
