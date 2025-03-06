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
import customExcpetion.FrameworkException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.UIBaseTest;
import utils.LoggerUtil;

public class LoginPage extends UIBaseTest {
    WebDriver driver;
    private static final LoggerUtil logger = LoggerUtil.getLogger(LoginPage.class);
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
        try {
            clearUserNameAndPassword();
            this.userName.sendKeys(userName);
            this.password.sendKeys(password);
            logger.info("userName and Password Entered successfully");
            this.submitBtn.click();
        }catch (Exception exception){
            logger.error("Login failed due to an issue with the WebElement: " + exception.getMessage());
            throw new FrameworkException("Login failed. Please check the WebElement or locator.", exception);
        }
    }

    /**
     *  clear the fields of userName and Password
     */
    public void clearUserNameAndPassword(){
        WaitManager.waitForElementToBeVisible(userName,10);
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
