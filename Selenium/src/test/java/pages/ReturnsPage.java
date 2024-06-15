package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setupBase.BaseClass;

public class ReturnsPage extends BaseClass {

    WebDriver driver;
    public ReturnsPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver= driver;
    }

    @FindBy(xpath = "//input[@id='input-product']")
    private WebElement enterProductName;
    @FindBy(xpath = "//button[@id='button-filter']")
    private WebElement clickFilterButton;

   public void enterProductName(String productName){
        enterProductName.sendKeys(productName);
    }
   public void pressFilterButton(){
        clickFilterButton.click();
    }
}
