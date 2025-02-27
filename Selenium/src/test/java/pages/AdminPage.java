/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package pages;

import common.CommonMethods;
import common.WaitManager;
import customExcpetion.FrameworkException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testBase.UIBaseTest;

import java.security.PrivateKey;
import java.util.Iterator;
import java.util.Set;

public class AdminPage extends UIBaseTest {

    WebDriver driver;
    public AdminPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver= driver;
    }
    @FindBy(xpath = "//button[@title='Help']")
    public WebElement helpIconBtn;
    @FindBy(xpath = "//span[normalize-space(text())='User Management']")
    public WebElement userManagementOption;
    @FindBy(xpath = "//a[text()='Users']")
    public WebElement userOption;
    @FindBy(xpath = "//div[@class='orangehrm-header-container']//button[normalize-space(text()='Add')]")
    public WebElement addUserBtn;



    public void clickOnHelpIcon(){
        helpIconBtn.click();
    }

    public String verifyUserAccountPageOpen(){
        WaitManager.waitForElementToBeClickable(helpIconBtn,10);
        clickOnHelpIcon();
        WaitManager.numberOfWindowsToBe(2,2);
       try {
           String parentWindow =driver.getWindowHandle();
           System.out.println(parentWindow);
           Set<String> windows = driver.getWindowHandles();
           Iterator<String> it  =  windows.iterator();
           while (it.hasNext()){
               String childWindow = it.next();
               if(!parentWindow.equals(childWindow)){
                   driver.switchTo().window(childWindow);
               }
           }
       }catch (Exception e){
           throw new FrameworkException("There is no another window open");
       }
       System.out.println(CommonMethods.getTitleOfPage(driver));
        return CommonMethods.getTitleOfPage(driver);
    }
}
