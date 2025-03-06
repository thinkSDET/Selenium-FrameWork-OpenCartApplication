/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package common;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import testBase.UIBaseTest;
import utils.Logger;

import java.util.List;

public class CommonMethods {
    private static final Logger logger = Logger.getLogger(CommonMethods.class);

    private static WebDriver getDriver() {
        return UIBaseTest.getDriver(); // Fetch driver automatically
    }

    private static Actions getActions() {
        return new Actions(getDriver()); // Create Actions instance dynamically
    }
    /**
     * get the title of DashBoardPage
     * @return
     */
    public static String getTitleOfPage(){
        return getDriver().getTitle();
    }

    /**
     *  Move to an element and then click on element
     * @param element
     */
    public static void moveToElementAndClick(WebElement element){
        getActions().moveToElement(element).click().perform();
    }

    /**
     *
     * @param element
     */
    public static void setTheFocusToElement(WebElement element){
        getActions().moveToElement(element).perform();
    }

    /**
     *  Fixes scrolling issues → Ensures the element is in view before checking.
     *  More reliable than isDisplayed() → Works for elements hidden due to scrolling.
     *
     *  arguments[0] → Refers to the first argument passed to executeScript(), which in Selenium is the WebElement.
     *  .getBoundingClientRect() → Returns the position and dimensions of the element as a DOMRect object.
     * @param element
     * @return
     */
    public static boolean isElementFullyVisible(WebElement element){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        // Scroll the element into view
        jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        // Check if the element is fully visible
        return (Boolean) jsExecutor.executeScript(
                "var dimensions = arguments[0].getBoundingClientRect();" + "return (dimensions.top >= 0 && dimensions.bottom <= window.innerHeight);",
                element
        );
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
     * Selects a value from a custom dropdown that is implemented using div elements instead of a select tag.
     * This method clicks the dropdown to expand options, waits for the options to be visible,
     * and then selects the desired value if available.
     *
     * @param element The dropdown WebElement that needs to be clicked to open the options.
     * @param value   The text of the option that should be selected.
     */
    public static void selectValueFromDropDown(WebElement element, String value){
        try {
            element.click();
            WaitManager.setWait(10);
            WaitManager.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='oxd-select-option' and @role='option']")));
            List<WebElement> nationalityList = getDriver().findElements(By.xpath("//div[@class='oxd-select-option' and @role='option']"));
            for(WebElement option : nationalityList){
                if(option.getText().equalsIgnoreCase(value)){
                    option.click();
                    break;
                }
            }
        }catch (Exception e){
            logger.error("Option '" + value + "' not found in the dropdown.");
        }

    }

    public static void clearInputField(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
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

    /**
     * Clicks on a WebElement using standard Selenium click().
     * If the element is not clickable, falls back to JavaScript click.
     *
     * @param element The WebElement to click.
     */
    public static void clickElement(WebElement element) {
        try {
            if (element == null) {
                Assert.fail("Element is null and cannot be clicked.");
            }
            setTheFocusToElement(element); // Bring element into focus
            element.click(); // Try normal Selenium click
            logger.info("Clicked on element successfully using Selenium.");
        } catch (Exception e) {
            logger.warn("Selenium click failed, attempting JavaScript click. Reason: " + e.getMessage());
            try {
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                js.executeScript("arguments[0].setAttribute('style', 'visibility: visible');", element);
                // above two lines : ensures the element is visible before interacting with it.
                js.executeScript("arguments[0].click();", element);
                logger.info("Clicked on element successfully using JavaScript.");
            } catch (Exception jsException) {
                logger.error("JavaScript click also failed: " + jsException.getMessage());
                Assert.fail("Element could not be clicked using both Selenium and JavaScript.");
            }
        }
    }

    /**
     * Switches to a frame by its index.
     *
     * @param index The zero-based index of the frame.
     * @throws AssertionError if the frame is not found or switching fails.
     */
    public static void switchToFrame(int index) {
        try {
            getDriver().switchTo().frame(index);
        } catch (Exception e) {
            logger.error("Failed to switch to frame by index [" + index + "]: " + e.getMessage());
        }
    }

    /**
     * Switches to a frame by its name or ID.
     *
     * @param nameOrId The name or ID attribute of the frame.
     * @throws AssertionError if the frame is not found or switching fails.
     */
    public static void switchToFrame(String nameOrId) {
        try {
            getDriver().switchTo().frame(nameOrId);
        } catch (Exception e) {
            logger.error("Failed to switch to frame by name/ID [" + nameOrId + "]: " + e.getMessage());
        }
    }

    /**
     * Switches to a frame using a WebElement reference.
     *
     * @param frameElement The WebElement representing the frame.
     * @throws AssertionError if the frame is not found or switching fails.
     */
    public static void switchToFrame(WebElement frameElement) {
        try {
            getDriver().switchTo().frame(frameElement);
        } catch (Exception e) {
            logger.error("Failed to switch to frame using WebElement: " + e.getMessage());
        }
    }

    /**
     * Switches back to the default content from an iframe.
     *
     * This method is used to exit the currently active iframe
     * and return control to the main document.
     *
     */
    public static void switchBackToDefault() {
        try {
            getDriver().switchTo().defaultContent();
        } catch (Exception e) {
            logger.error("Failed to switch back to default content: " + e.getMessage());
        }
    }
}
