/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package pages;

import common.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.UIBaseTest;

public class LoginPage extends UIBaseTest {
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
        WaitManager.waitForVisibility(toastMessage,30);
        return toastMessage.getText();
    }

    public String getInvalidCredentialValidationMessage() {
       return getInvalidCredentialMessage.getText();
    }
}
