package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setupBase.BaseClass;

public class DashBoardPage extends BaseClass {
    WebDriver driver;
    public DashBoardPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    @FindBy(xpath = "//p[contains(text(),'manda user')]")
    private WebElement userName;

    /**
     *
     */
    public void userNameDisplay(){
        visibilityOfElement(driver,userName);
        userName.isDisplayed();
    }

    /**
     * get the title of DashBoardPage
     * @return
     */
    public String getTitleOfDashBoardPage(){
       return driver.getTitle();
    }
}
