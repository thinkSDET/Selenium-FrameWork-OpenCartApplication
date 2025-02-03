package pages.myInfo;

import commonMethods.ReusableMethods;
import commonMethods.WaitManager;
import dataClasses.PersonalDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import setupBase.BaseClass;

import java.util.List;

public class PersonalDetailsPage extends BaseClass {

    WebDriver driver;
    public PersonalDetailsPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver=driver;
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
        ReusableMethods.clearInputField(firstName,driver);
        ReusableMethods.clearInputField(middleName,driver);
        ReusableMethods.clearInputField(lastname,driver);
        ReusableMethods.clearInputField(employeeId,driver);
        ReusableMethods.clearInputField(otherId,driver);
        ReusableMethods.clearInputField(driverLicenseNumber,driver);
        ReusableMethods.clearInputField(driverLicenseExpDate,driver);
        firstName.sendKeys(personalDetails.firstName);
        middleName.sendKeys(personalDetails.middleName);
        lastname.sendKeys(personalDetails.lastName);
        employeeId.sendKeys(personalDetails.empId);
        otherId.sendKeys(personalDetails.otherId);
        driverLicenseNumber.sendKeys(personalDetails.drivingLicId);
        driverLicenseExpDate.sendKeys(personalDetails.licenseExpDate);
        ReusableMethods.selectValueFromDropDown(this.nationality,personalDetails.nationality,driver);
        ReusableMethods.selectValueFromDropDown(this.maritalStatus,personalDetails.maritalStatus,driver);
        saveButton.click();
    }
}
