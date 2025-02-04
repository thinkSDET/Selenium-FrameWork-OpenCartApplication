package commonMethods;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class ReusableMethods {
    public static Actions actions;


    /**
     * get the title of DashBoardPage
     * @return
     */
    public static String getTitleOfPage(WebDriver driver){
        return driver.getTitle();
    }

    /**
     *  Move to an element and then click on element
     * @param driver
     * @param element
     */
    public static void moveToElementAndClick(WebDriver driver, WebElement element){
        actions =  new Actions(driver);
        actions.moveToElement(element).click().perform();
    }
    /**
     * clear the existing text from the input field
     */
    public static void clearTextFromField(WebElement element){
        element.clear();
    }

    /**
     * This approach simulates the keyboard shortcut "Ctrl+A" (select all) followed by "Delete" to clear the field entirely.
     * @param element
     */
    public static void sendKeyWithSelectAllTextAndRemove(WebElement element){
       // element.click();
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
    public static void selectValueFromDropDown(WebElement element, String value,WebDriver driver){
        element.click();
        List<WebElement> nationalityList = driver.findElements(By.xpath("//div[@class='oxd-select-option' and @role='option']"));
        for(WebElement list : nationalityList){
            if(list.getText().equalsIgnoreCase(value)){
                list.click();
                break;
            }
        }
    }

    public static void clearInputField(WebElement element, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        try {
            WaitManager.forceWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        element.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
        try {
            WaitManager.forceWait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
       /* // Click on the element to focus it
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);

        // Wait for the element to be clickable again, just in case
        WaitManager.waitForElementToBeClickable(element,20);

        // Clear the input field using CTRL + A and DELETE
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));

        // Wait for the input field to be cleared using JavaScript
        WaitManager.getWait().until(d -> {
            String inputValue = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element);// Get the value of the input field
            System.out.println(inputValue);
            return inputValue == null || inputValue.isEmpty(); // Ensure the value is empty
        });*/
    }
}
