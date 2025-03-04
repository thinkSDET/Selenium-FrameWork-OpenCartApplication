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

public class DashBoardPage extends UIBaseTest {
    WebDriver driver;
    public DashBoardPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    @FindBy(xpath = "//h6[text()='Dashboard']")
    private WebElement dashBoard;

    /**
     * @return
     */
    public boolean dashBoardDisplay(){
        WaitManager.waitForVisibility(dashBoard,20);
        try {
            dashBoard.isDisplayed();
            return true;
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }
        return false;
    }
}
