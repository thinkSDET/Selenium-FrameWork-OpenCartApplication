/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package pages;

import common.CommonMethods;
import common.WaitManager;
import customExcpetion.FrameworkException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.UIBaseTest;

import java.util.Set;

public class AdminPage {

    WebDriver driver;
    public AdminPage(WebDriver driver){
        this.driver= driver;
        PageFactory.initElements(driver,this); // Initialize PageFactory
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
           System.out.println("parent window id -->"+ parentWindow);
           Set<String> windows = driver.getWindowHandles();
           for (String childWindow : windows) {
               if (!parentWindow.equals(childWindow)) {
                   driver.switchTo().window(childWindow);
               }
           }
       }catch (Exception e){
           throw new FrameworkException("There is no another window open");
       }
       System.out.println(CommonMethods.getTitleOfPage());
        try {
            WaitManager.forceWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CommonMethods.getTitleOfPage();
    }
}
