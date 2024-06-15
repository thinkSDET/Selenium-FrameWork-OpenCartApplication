package commonMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class ReusableMethods extends Waits {
    protected Actions actions;

    /**
     *  Move to an element and then click on element
     * @param driver
     * @param element
     */
    protected void moveToElementAndClick(WebDriver driver, WebElement element){
        actions =  new Actions(driver);
        actions.moveToElement(element).click().perform();
    }
    /**
     * clear the existing text from the input field
     */
    protected void clearTextFromField(WebElement element){
        element.clear();
    }

    /**
     * This approach simulates the keyboard shortcut "Ctrl+A" (select all) followed by "Delete" to clear the field entirely.
     * @param element
     */
    protected void sendKeyWithSelectAllTextAndRemove(WebElement element){
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
    }

    /**
     * This method simulates pressing the backspace key repeatedly to delete the existing text. It's effective in most scenarios.
     * @param element
     */
    protected void sendKeysWithBackSpaceAndClearField(WebElement element){
        element.click();
        while (element.getAttribute("")!=null){
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    /**
     *
     * @param element
     * @param value
     * @param driver
     */
    protected void selectValueFromDropDown(WebElement element, String value,WebDriver driver){
        element.click();
        List<WebElement> nationalityList = driver.findElements(By.xpath("//div[@class='oxd-select-option' and @role='option']"));
        for(WebElement list : nationalityList){
            if(list.getText().equalsIgnoreCase(value)){
                list.click();
                break;
            }
        }
    }
}
