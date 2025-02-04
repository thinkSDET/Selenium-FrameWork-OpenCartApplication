package pages.navigationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.UIBaseTest;

public class DirectoryPageNavigation extends UIBaseTest {

    WebDriver driver;
    public DirectoryPageNavigation(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }
    @FindBy(xpath = "//span[text()='Directory']")
    private WebElement clickDirectory;

    public void clickOnMyInfoOption(){
        clickDirectory.click();
    }
}
