package navigationPages.salesPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setupBase.BaseClass;

public class ReturnsPageNavigation extends BaseClass {

    WebDriver driver;
    public ReturnsPageNavigation(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }
    @FindBy(xpath = "//a[text()=' Sales']/parent::li//ul//li//a[text()='Returns']")
    private WebElement clickReturns;

    public void clickOnReturns(){
        clickReturns.click();
    }
}
