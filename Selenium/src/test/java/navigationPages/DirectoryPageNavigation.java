package navigationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setupBase.TestBase;

public class DirectoryPageNavigation extends TestBase {

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
