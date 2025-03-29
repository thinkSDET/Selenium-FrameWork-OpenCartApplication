/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package pages.myInfo;

import dataClasses.PersonalDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;

public class PersonalDetailsPage extends BasePage {

    WebDriver driver;
    public PersonalDetailsPage(WebDriver driver){
      super(driver);
    }

    @FindBy(xpath = "//label[text()='Employee Full Name']/parent::div/following-sibling::div//input[@name='firstName']")
    private WebElement firstName;
    @FindBy(xpath = "//input[@name='middleName']")
    private WebElement middleName;
    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement lastname;
    @FindBy(xpath = "//label[text()='Employee Id']/parent::div/following-sibling::div/input")
    private WebElement employeeId;
    @FindBy(xpath = "//label[text()='Other Id']/parent::div/following-sibling::div/input")
    private WebElement otherId;
    @FindBy(xpath = "//label[contains(text(),'License Number')]/parent::div/following-sibling::div/input")
    private WebElement driverLicenseNumber;
    @FindBy(xpath = "//label[contains(text(),'License Expiry Date')]/parent::div/following-sibling::div//input")
    private WebElement driverLicenseExpDate;
    @FindBy(xpath = "//label[contains(text(),'Nationality')]/parent::div/following-sibling::div//div[@class='oxd-select-text oxd-select-text--active']")
    private WebElement nationality;
    @FindBy(xpath = "//label[contains(text(),'Marital Status')]/parent::div/following-sibling::div//div[@class='oxd-select-text oxd-select-text--active']")
    private WebElement maritalStatus;
    @FindBy(xpath = "//label[contains(text(),'Date of Birth')]/parent::div/following-sibling::div//input']")
    private WebElement dateOfBirth;
    @FindBy(xpath = "//input[@type='radio']/parent::label[text()='Male']//input")
    private WebElement male;
    @FindBy(xpath = "//input[@type='radio']/parent::label[text()='Female']//input")
    private WebElement female;
    @FindBy(xpath = "//label[contains(text(),'Blood Type')]/parent::div/following-sibling::div//div[@class='oxd-select-text oxd-select-text--active']")
    private WebElement bloodType;
    @FindBy (xpath = "//label[contains(text(),'Test_Field')]/parent::div/following-sibling::div//input")
    private WebElement test_Field;
    @FindBy(xpath = "//h6[text()='Personal Details']/parent::div//form//button[@type='submit']")
    private WebElement personalDetailsSubmitBtn;
    @FindBy(xpath = "(//button[@type='submit'])[1]")
    private WebElement saveButton;

    /**
     *
     * @param personalDetails
     */
    public void fillPersonalDetails(PersonalDetails personalDetails){
        isElementFullyVisible(firstName);
        clearInputField(firstName);
        clearInputField(middleName);
        clearInputField(lastname);
        clearInputField(employeeId);
        clearInputField(otherId);
        clearInputField(driverLicenseNumber);
        clearInputField(driverLicenseExpDate);
        firstName.sendKeys(personalDetails.firstName);
        middleName.sendKeys(personalDetails.middleName);
        lastname.sendKeys(personalDetails.lastName);
        employeeId.sendKeys(personalDetails.empId);
        otherId.sendKeys(personalDetails.otherId);
        driverLicenseNumber.sendKeys(personalDetails.drivingLicId);
        driverLicenseExpDate.sendKeys(personalDetails.licenseExpDate);
        selectValueFromDropDown(this.nationality,personalDetails.nationality,10);
        selectValueFromDropDown(this.maritalStatus,personalDetails.maritalStatus,10);
        saveButton.click();
    }
}
