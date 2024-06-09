package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage  {

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="input-username")
    private WebElement userName;

    @FindBy(id="input-password")
    private WebElement password;

    @FindBy(xpath="//button[@type='submit']")
    private WebElement submitBtn;

    /**
     *  Login into open cart application
     * @param userName
     * @param password
     */
    public void login(String userName, String password){
        clearUserNameAndPassword();
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        this.submitBtn.click();
    }

    /**
     *  clear the fields of userName and Password
     */
    public void clearUserNameAndPassword(){
        userName.clear();
        password.clear();
    }

}
