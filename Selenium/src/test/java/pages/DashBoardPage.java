package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashBoardPage {
    WebDriver driver;
    public DashBoardPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    @FindBy(xpath = "//span[contains(text(),'demo demo')]")
    private WebElement userName;

    public boolean userNameDisplay(){
       return userName.isDisplayed();
    }

    public String getTitleOfDashBoardPage(){
       return driver.getTitle();
    }
}
