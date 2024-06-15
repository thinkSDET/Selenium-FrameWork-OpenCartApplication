package navigationPages.salesPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setupBase.BaseClass;

public class SalesPageNavigation extends BaseClass {

    public SalesPageNavigation(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//a[text()=' Sales']")
    private WebElement clickToExpendSalesPage;

   public void expendSalesPage(){
        clickToExpendSalesPage.click();
    }
}
