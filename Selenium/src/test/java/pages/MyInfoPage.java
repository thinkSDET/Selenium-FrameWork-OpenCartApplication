package pages;

import commonMethods.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.TestBase;

public class MyInfoPage extends TestBase {

    WebDriver driver;
    public MyInfoPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver= driver;
    }

    @FindBy(xpath = "//a[text()='Personal Details']")
    private WebElement personalDetails;
    @FindBy(xpath = "//a[text()='Job']")
    private WebElement job;
    @FindBy(xpath = "//h6[text()='Job Details']")
    private WebElement jobDetailsTitle;
    @FindBy(xpath = "//h6[text()='Personal Details']")
    private WebElement personalDetailsTitle;

   public void clickPersonalDetailsOption(){
       personalDetails.click();
       WaitManager.waitForVisibility(personalDetailsTitle,30);
    }
    public void clickJobOption(){
       job.click();
    }
    public boolean jobDetailsVisibility(){
       return jobDetailsTitle.isDisplayed();
    }

}
