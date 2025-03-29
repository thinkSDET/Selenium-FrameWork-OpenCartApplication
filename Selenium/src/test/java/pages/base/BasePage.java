/*
 * *
 *  * Copyright (c) 2025 [thinkSDET]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package pages.base;

import customExcpetion.TestAutomationException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import testBase.DriverManager;
import testBase.UIBaseTest;
import utils.BaseLogger;

import java.util.List;

public class BasePage extends WaitManager {
    protected WebDriver driver; // Protected so subclasses can access it

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initialize PageFactory once
    }


    // Fetch driver for the current thread
    private WebDriver getDriver() {
        return DriverManager.getDriver();
    }
    // Create Actions instance for the current thread
    private Actions getActions() {
        return new Actions(getDriver());
    }
    /**
     * get the title of DashBoardPage
     * @return
     */
    public String fetchPageTitle(){
        return getDriver().getTitle();
    }

    /**
     *  Move to an element and then click on element
     * @param element
     */
    public  void hoverAndClick(WebElement element){
        getActions().moveToElement(element).click().perform();
    }

    /**
     *
     * @param element
     */
    public  void focusOnElement(WebElement element){
        getActions().moveToElement(element).perform();
    }
    /**
     * Generic method to check if an element is displayed.
     * @param element The WebElement to check visibility.
     * @return true if displayed, false otherwise.
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false; // Element not found or stale, return false
        }
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
    public  boolean isElementCompletelyVisible(WebElement element){
        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        // Scroll the element into view
        jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        BaseLogger.info(element + "-- is fully visible on the page");
        // Check if the element is fully visible
        return (Boolean) jsExecutor.executeScript(
                "var dimensions = arguments[0].getBoundingClientRect();" + "return (dimensions.top >= 0 && dimensions.bottom <= window.innerHeight);",
                element
        );
    }

    /**
     * Selects a value from a custom dropdown that is implemented using div elements instead of a select tag.
     * This method clicks the dropdown to expand options, waits for the options to be visible,
     * and then selects the desired value if available.
     *
     * @param element The dropdown WebElement that needs to be clicked to open the options.
     * @param value   The text of the option that should be selected.
     */
    public  void selectValueFromDropDown(WebElement element, String value, long timeOut){
        try {
            element.click();
            getWait(timeOut).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='oxd-select-option' and @role='option']")));
            List<WebElement> nationalityList = getDriver().findElements(By.xpath("//div[@class='oxd-select-option' and @role='option']"));
            for(WebElement option : nationalityList){
                if(option.getText().equalsIgnoreCase(value)){
                    option.click();
                    break;
                }
            }
        }catch (Exception e){
            BaseLogger.error("Option '" + value + "' not found in the dropdown.");
        }

    }

    /**
     * Scrolls the given WebElement into view using JavaScript.
     * This ensures that the element is visible within the viewport
     * before interacting with it.
     *
     * @param element The WebElement to be scrolled into view.
     * @throws IllegalArgumentException if the provided element is null.
     */
    private void scrollIntoView(WebElement element) {
        if (element == null) {
            BaseLogger.error("Element is null, cannot scroll");
            throw new TestAutomationException("Element is null, cannot scroll.");
        }
        try {
            BaseLogger.info(element + "Scroll to the element");
            ((JavascriptExecutor) getDriver()).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                    element
            );
        } catch (Exception e) {
            BaseLogger.error("Scrolling failed for element" + element);
            throw new TestAutomationException("Scrolling failed: " + e.getMessage(), e);
        }
    }


    /**
     * Method with boolean parameter to choose between strategies
     * @param element
     * @param useSelectAll
     */
    public void clearInputField(WebElement element, boolean useSelectAll) {
        if (useSelectAll) {
            //simulates the keyboard shortcut "Ctrl+A" (select all) followed by "Delete" to clear the field entirely.
            element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        } else {
            //simulates pressing the backspace key repeatedly to delete the existing text. It's effective in most scenarios.
            element.click();
            String value = element.getAttribute("value");
            while (value!=null && !value.isEmpty()){
                element.sendKeys(Keys.BACK_SPACE);
            }
        }
    }
    /**
     *  No-parameter method - calls the JavaScript implementation with default timeout
     * @param element
     */
    public void clearInputField(WebElement element) {
        clearInputField(element, 10); // Calls the other method with default timeout
    }

    /**
     * Method with timeout parameter - uses JavaScript implementation
     * @param element
     * @param timeOut
     */
    public  void clearInputField(WebElement element,long timeOut) {
        try {
            // Scroll the element into view
            scrollIntoView(element);
            // Click on the element to focus it
            clickElement(element);
            // Clear the field using JavaScript
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", element);
            // Wait for the element to be clickable again, just in case
            // Clear the input field using CTRL + A and DELETE
            element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
            // Wait for the input field to be cleared using JavaScript
            getWait(timeOut).until(d -> {
                String inputValue = (String) ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].value;", element);// Get the value of the input field
                System.out.println(inputValue);
                return inputValue == null || inputValue.isEmpty(); // Ensure the value is empty
            });
        }
        catch (Exception e){
            BaseLogger.error("Failed to clear input field: " + e.getMessage());
            throw new TestAutomationException("Failed to clear input field.", e);
        }

    }
    /**
     * Clicks on a WebElement using standard Selenium click().
     * If the element is not clickable, falls back to JavaScript click.
     *
     * @param element The WebElement to click.
     */
    public  void clickElement(WebElement element) {
        try {
            if (element == null) {
                Assert.fail("Element is null and cannot be clicked.");
            }
            focusOnElement(element); // Bring element into focus
            element.click(); // Try normal Selenium click
            BaseLogger.info("Clicked on element successfully using Selenium.");
        } catch (Exception e) {
            BaseLogger.warn("Selenium click failed, attempting JavaScript click. Reason: " + e.getMessage());
            try {
                JavascriptExecutor js = (JavascriptExecutor) getDriver();
                js.executeScript("arguments[0].setAttribute('style', 'visibility: visible');", element);
                // above two lines : ensures the element is visible before interacting with it.
                js.executeScript("arguments[0].click();", element);
                BaseLogger.info("Clicked on element successfully using JavaScript.");
            } catch (Exception jsException) {
                BaseLogger.error("JavaScript click also failed: " + jsException.getMessage());
            }
        }
    }

    /**
     * Switches to a frame by its index.
     *
     * @param index The zero-based index of the frame.
     * @throws AssertionError if the frame is not found or switching fails.
     */
    public  void switchToFrame(int index) {
        try {
            getDriver().switchTo().frame(index);
        } catch (Exception e) {
            BaseLogger.error("Failed to switch to frame by index [" + index + "]: " + e.getMessage());
        }
    }

    /**
     * Switches to a frame by its name or ID.
     *
     * @param nameOrId The name or ID attribute of the frame.
     * @throws AssertionError if the frame is not found or switching fails.
     */
    public  void switchToFrame(String nameOrId) {
        try {
            getDriver().switchTo().frame(nameOrId);
        } catch (Exception e) {
            BaseLogger.error("Failed to switch to frame by name/ID [" + nameOrId + "]: " + e.getMessage());
        }
    }

    /**
     * Switches to a frame using a WebElement reference.
     *
     * @param frameElement The WebElement representing the frame.
     * @throws AssertionError if the frame is not found or switching fails.
     */
    public  void switchToFrame(WebElement frameElement) {
        try {
            getDriver().switchTo().frame(frameElement);
        } catch (Exception e) {
            BaseLogger.error("Failed to switch to frame using WebElement: " + e.getMessage());
        }
    }

    /**
     * Switches back to the default content from an iframe.
     *
     * This method is used to exit the currently active iframe
     * and return control to the main document.
     *
     */
    public  void switchBackToDefault() {
        try {
            getDriver().switchTo().defaultContent();
        } catch (Exception e) {
            BaseLogger.error("Failed to switch back to default content: " + e.getMessage());
        }
    }
}
