package setupBase;

import navigationPages.salesPage.ReturnsPageNavigation;
import navigationPages.salesPage.SalesPageNavigation;
import org.openqa.selenium.WebDriver;
import pages.DashBoardPage;
import pages.LoginPage;
import pages.ReturnsPage;

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
    public SalesPageNavigation salesPageNavigation(){
        return new SalesPageNavigation(driver);
    }
    public ReturnsPageNavigation returnsPageNavigation(){
        return new ReturnsPageNavigation(driver);
    }
    public ReturnsPage returnsPage(){
        return new ReturnsPage(driver);
    }
}
