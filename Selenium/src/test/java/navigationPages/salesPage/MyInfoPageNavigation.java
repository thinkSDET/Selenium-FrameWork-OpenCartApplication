package navigationPages.salesPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setupBase.BaseClass;

public class MyInfoPageNavigation extends BaseClass {

    WebDriver driver;
    public MyInfoPageNavigation(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }
    @FindBy(xpath = "//span[text()='My Info']")
    private WebElement clickMyInfo;

    public void clickOnMyInfoOption(){
        clickMyInfo.click();
    }
}
