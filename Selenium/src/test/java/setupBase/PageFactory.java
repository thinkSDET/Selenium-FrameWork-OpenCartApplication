package setupBase;

import navigationPages.MyInfoPageNavigation;
import org.openqa.selenium.WebDriver;
import pages.DashBoardPage;
import pages.LoginPage;
import pages.MyInfoPage;
import pages.myInfo.PersonalDetailsPage;

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
    public MyInfoPageNavigation myInfoPageNavigation(){
        return new MyInfoPageNavigation(driver);
    }
    public MyInfoPage myInfoPage(){
        return new MyInfoPage(driver);
    }
    public PersonalDetailsPage personalDetailsPage(){
       return new PersonalDetailsPage(driver);
    }
}
