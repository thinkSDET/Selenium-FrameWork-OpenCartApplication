/*
 * *
 *  * Copyright (c) 2025 [thinkSDET]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package pages.basePage;

import customExcpetion.FrameworkException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testBase.UIBaseTest;
import utils.BaseLogger;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver; // Protected so subclasses can access it

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // Initialize PageFactory once
    }
    // ThreadLocal to store WebDriverWait instances for parallel execution
    public static ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<WebDriverWait>();

    // Fetch driver for the current thread
    private WebDriver getDriver() {
        return UIBaseTest.getDriver();
    }
    // Create Actions instance for the current thread
    private Actions getActions() {
        return new Actions(getDriver());
    }
    /**
     * get the title of DashBoardPage
     * @return
     */
    public String getTitleOfPage(){
        return getDriver().getTitle();
    }

    /**
     *  Move to an element and then click on element
     * @param element
     */
    public  void moveToElementAndClick(WebElement element){
        getActions().moveToElement(element).click().perform();
    }

    /**
     *
     * @param element
     */
    public  void setTheFocusToElement(WebElement element){
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
    public  boolean isElementFullyVisible(WebElement element){
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
     * clear the existing text from the input field
     */
    public  void clearTextFromField(WebElement element){
        element.clear();
    }

    /**
     * This approach simulates the keyboard shortcut "Ctrl+A" (select all) followed by "Delete" to clear the field entirely.
     * @param element
     */
    public  void sendKeyWithSelectAllTextAndRemove(WebElement element){
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
    public  void selectValueFromDropDown(WebElement element, String value){
        try {
            element.click();
            getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='oxd-select-option' and @role='option']")));
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

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    public  void clearInputField(WebElement element) {
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
            getWait().until(d -> {
                String inputValue = (String) ((JavascriptExecutor) getDriver()).executeScript("return arguments[0].value;", element);// Get the value of the input field
                System.out.println(inputValue);
                return inputValue == null || inputValue.isEmpty(); // Ensure the value is empty
            });
        }
        catch (Exception e){
            BaseLogger.error("Failed to clear input field: " + e.getMessage());
            throw new FrameworkException("Failed to clear input field.", e);
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
            setTheFocusToElement(element); // Bring element into focus
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

    /**
     * Sets up an explicit wait (WebDriverWait) using ThreadLocal for thread safety.
     */
  /*  public  void setWait(long timeoutInSeconds){
        waitThreadLocal.set(new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)));
    }*/

    /**
     * Retrieves the WebDriverWait instance for the current thread.
     */
    public WebDriverWait getWait() {
        waitThreadLocal.set(new WebDriverWait(getDriver(), Duration.ofSeconds(15)));
        return waitThreadLocal.get();
    }
    /**
     * Removes the WebDriverWait instance from ThreadLocal to prevent memory leaks.
     * Call this method in @AfterMethod in TestNG.
     */
    public  void removeWait() {
        waitThreadLocal.remove();
    }

    /**
     * Sets an implicit wait for the driver.
     * This applies to all findElement() calls and waits for elements to be available.
     */
    public  void implicitWait(){
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    /**
     * Waits until the given element is visible.
     * Uses the ThreadLocal wait instance to ensure thread safety.
     */
    public void waitForVisibility(WebElement element,long timeOut){
        WebDriverWait wait = getWait();
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait is not initialized. Call setWait() first.");
        }
        getWait().until(ExpectedConditions.visibilityOf(element));
    }
    /**
     * Waits until the given element is clickable.
     * Uses the ThreadLocal wait instance to ensure thread safety.
     */
    public void waitForElementToBeClickable(WebElement element,long timeOut){
        WebDriverWait wait = getWait();
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait is not initialized. Call setWait() first.");
        }
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Returns an {@code ExpectedCondition} that checks if the given element is visible and clickable.
     * This method combines {@code ExpectedConditions.visibilityOf(element)} and
     * {@code ExpectedConditions.elementToBeClickable(element)} to ensure the element is fully interactable.
     *
     * @param element The {@code WebElement} to be checked.
     * @return An {@code ExpectedCondition<Boolean>} that evaluates to {@code true} when the element is visible and clickable.
     */

    public ExpectedCondition<Boolean> elementToBeReady(WebElement element){
        return ExpectedConditions.and(
                ExpectedConditions.visibilityOf(element),
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    /**
     * Forces the execution to pause for a fixed duration.
     * This method introduces a hard-coded wait using {@code Thread.sleep(500)}.

     * Using Thread.sleep() is not recommended in test automation
     * as it can slow down execution. Prefer explicit waits like {@code WebDriverWait} instead.</p>
     *
     */
    public void forceWait() throws InterruptedException {
        Thread.sleep(500);
    }

    /**
     * Waits until the number of browser windows matches the expected count.
     * This method initializes a {@code WebDriverWait} with the specified timeout
     * and waits until the number of open windows equals the given value.
     *
     * @param timeOut The maximum time (in seconds) to wait for the windows to reach the expected count.
     * @param windows The expected number of browser windows.
     * @throws IllegalStateException if {@code WebDriverWait} is not initialized before calling this method.
     */
    public void numberOfWindowsToBe(long timeOut, int windows){
        WebDriverWait wait = getWait();
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait is not initialized. Call setWait() first.");
        }
        getWait().until(ExpectedConditions.numberOfWindowsToBe(windows));
    }

    /**
     * This method is used to wait until a web element becomes visible and enabled before performing actions on it.
     * Both conditions are checked one after another (not in parallel).
     * If the first condition fails, the second is never checked.
     * If both pass within the timeout, the method proceeds normally.
     * @param element
     * @param timeOut
     *
     * This is imp method
     */
    public void waitForElementToBeVisible(WebElement element,Integer timeOut){
        WebDriverWait wait = getWait();
        wait.until(displayed->element.isDisplayed());
        wait.until(displayed->element.isEnabled());
    }
}
