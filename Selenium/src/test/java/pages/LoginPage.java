package pages;

import commonMethods.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import setupBase.BaseClass;

public class LoginPage extends BaseClass {
    WebDriver driver;
    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
    }

    @FindBy(xpath = "//input[@name='username']")
    private WebElement userName;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement password;

    @FindBy(xpath="//button[@type='submit']")
    private WebElement submitBtn;

    @FindBy(xpath = "//div[@id='alert']//div")
    private WebElement toastMessage;

    @FindBy(xpath = "//div[@role='alert']//p")
    private WebElement getInvalidCredentialMessage;

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

    /**
     * get toast message while un-successful login
     */
    public String getToastMessage(){
        WaitManager.waitForVisibility(toastMessage);
        return toastMessage.getText();
    }

    public String getInvalidCredentialValidationMessage() {
       return getInvalidCredentialMessage.getText();
    }
}
