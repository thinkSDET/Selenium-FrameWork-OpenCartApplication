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
import utils.BaseLogger;

public class MyInfoPage extends BasePage {

    WebDriver driver;
    public MyInfoPage(WebDriver driver){
        this.driver= driver;
        PageFactory.initElements(driver,this); // Initialize PageFactory
    }

    @FindBy(xpath = "//a[text()='Personal Details']")
    private WebElement personalDetails;
    @FindBy(xpath = "//a[text()='Job']")
    private WebElement job;
    @FindBy(xpath = "//h6[text()='Job Details']")
    private WebElement jobDetailsTitle;
    @FindBy(xpath = "//h6[text()='Personal Details']")
    private WebElement personalDetailsTitle;

   public void clickPersonalDetailsOption(){
       personalDetails.click();
       BaseLogger.info("personal details clicked");
       waitForVisibility(personalDetailsTitle,30);
    }
    public void clickJobOption(){
       job.click();
        BaseLogger.info("job option clicked");
    }
    public boolean jobDetailsVisibility(){
       return jobDetailsTitle.isDisplayed();
    }

}
