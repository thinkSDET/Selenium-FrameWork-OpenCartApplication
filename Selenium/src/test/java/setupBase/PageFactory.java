package setupBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v85.page.Page;
import pages.DashBoardPage;
import pages.LoginPage;

public class PageFactory {
    WebDriver driver;
    public PageFactory(WebDriver driver){
        this.driver = driver;
    }
    public LoginPage loginPage(){
       return new LoginPage(driver);
    }
    public DashBoardPage dashBoardPage(){
        return new DashBoardPage(driver);
    }
}
