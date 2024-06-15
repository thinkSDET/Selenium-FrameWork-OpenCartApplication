package pages.myInfo;

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

    @FindBy(xpath = "//input[@name='firstName']")
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

    /**
     *
     * @param personalDetails
     */
    public void fillPersonalDetails(PersonalDetails personalDetails){
        sendKeyWithSelectAllTextAndRemove(firstName);
        sendKeyWithSelectAllTextAndRemove(middleName);
        sendKeyWithSelectAllTextAndRemove(lastname);
        sendKeyWithSelectAllTextAndRemove(employeeId);
        sendKeyWithSelectAllTextAndRemove(otherId);
        sendKeyWithSelectAllTextAndRemove(driverLicenseNumber);
        sendKeyWithSelectAllTextAndRemove(driverLicenseExpDate);
        firstName.sendKeys(personalDetails.firstName);
        middleName.sendKeys(personalDetails.middleName);
        lastname.sendKeys(personalDetails.lastName);
        employeeId.sendKeys(personalDetails.empId);
        otherId.sendKeys(personalDetails.otherId);
        driverLicenseNumber.sendKeys(personalDetails.drivingLicId);
        driverLicenseExpDate.sendKeys(personalDetails.licenseExpDate);
        selectValueFromDropDown(this.nationality,personalDetails.nationality,driver);
        selectValueFromDropDown(this.maritalStatus,personalDetails.maritalStatus,driver);
    }

/*    *//**
     * @param nationality
     *//*
    public void selectNationality(String nationality){
        this.nationality.click();
        List<WebElement> nationalityList = driver.findElements(By.xpath("//div[@class='oxd-select-option' and @role='option']"));
       for(WebElement list : nationalityList){
           if(list.getText().equalsIgnoreCase(nationality)){
            list.click();
            break;
           }
       }
    }*/
}
