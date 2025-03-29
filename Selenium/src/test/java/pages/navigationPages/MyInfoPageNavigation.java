/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package pages.navigationPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;
import utils.BaseLogger;

public class MyInfoPageNavigation extends BasePage {

    WebDriver driver;
    public MyInfoPageNavigation(WebDriver driver){
      super(driver);

    }
    @FindBy(xpath = "//span[text()='My Info']")
    private WebElement clickMyInfo;

    public void clickOnMyInfoOption(){
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='My Info']")));
        clickMyInfo.click();
        BaseLogger.info("My info option is clicked");
    }

    public void clickOnClaim(){
        getWait(10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Claim']")));
        clickMyInfo.click();
    }
}
