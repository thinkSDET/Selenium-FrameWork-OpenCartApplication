/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package pages.navigationPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.UIBaseTest;

public class DirectoryPageNavigation {

    WebDriver driver;
    public DirectoryPageNavigation(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this); // Initialize PageFactory

    }
    @FindBy(xpath = "//span[text()='Directory']")
    private WebElement clickDirectory;

    public void clickOnMyInfoOption(){
        clickDirectory.click();
    }
}
