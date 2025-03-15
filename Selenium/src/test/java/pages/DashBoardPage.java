/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.basePage.BasePage;
import testBase.UIBaseTest;

public class DashBoardPage extends BasePage {
    WebDriver driver;
    public DashBoardPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this); // Initialize PageFactory
    }

    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement dashBoard;

    /**
     * @return
     */
    public boolean dashBoardDisplay(){
        waitForVisibility(dashBoard,20);
        try {
            dashBoard.isDisplayed();
            return true;
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }
        return false;
    }
}
