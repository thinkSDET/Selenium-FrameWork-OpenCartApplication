package pages;

import commonMethods.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.UIBaseTest;

public class DashBoardPage extends UIBaseTest {
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
        WaitManager.waitForVisibility(userName,30);
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
