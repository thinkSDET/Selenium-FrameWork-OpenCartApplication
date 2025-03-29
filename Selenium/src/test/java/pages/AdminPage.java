/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package pages;

import customExcpetion.TestAutomationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;
import utils.BaseLogger;

import java.util.Set;


public class AdminPage extends BasePage {

    public AdminPage(WebDriver driver){
       super(driver);
    }
    @FindBy(xpath = "//button[@title='Help']")
    private WebElement helpIconBtn;
    @FindBy(xpath = "//span[normalize-space(text())='User Management']")
    private WebElement userManagementOption;
    @FindBy(xpath = "//a[text()='Users']")
    private WebElement userOption;
    @FindBy(xpath = "//div[@class='orangehrm-header-container']//button[normalize-space(text()='Add')]")
    private WebElement addUserBtn;


    public boolean isHelpIconVisible() {
        return isElementDisplayed(helpIconBtn);
    }

    public void clickOnHelpIcon(){
        helpIconBtn.click();
    }

    public String verifyUserAccountPageOpen(){
        waitForElementToBeClickable(helpIconBtn,10);
        clickOnHelpIcon();
        BaseLogger.info("Help Icon clicked");
        numberOfWindowsToBe(2,2);
       try {
           String parentWindow =driver.getWindowHandle();
           Set<String> windows = driver.getWindowHandles();
           for (String childWindow : windows) {
               if (!parentWindow.equals(childWindow)) {
                   driver.switchTo().window(childWindow);
                   BaseLogger.info("Switched to child window");
               }
           }
       }catch (Exception e){
           throw new TestAutomationException("There is no another window open");
       }
        try {
            forceWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return fetchPageTitle();
    }
}
