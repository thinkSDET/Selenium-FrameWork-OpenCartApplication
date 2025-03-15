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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.basePage.BasePage;
import testBase.UIBaseTest;

public class MyInfoPageNavigation extends BasePage {

    WebDriver driver;
    public MyInfoPageNavigation(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this); // Initialize PageFactory

    }
    @FindBy(xpath = "//span[text()='My Info']")
    private WebElement clickMyInfo;

    public void clickOnMyInfoOption(){
        setWait(30);
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='My Info']")));
        clickMyInfo.click();
    }

    public void clickOnClaim(){
        setWait(30);
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Claim']")));
        clickMyInfo.click();
    }
}
