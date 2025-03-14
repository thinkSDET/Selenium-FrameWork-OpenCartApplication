/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.UIBaseTest;

import java.time.Duration;

public class WaitManager {

    private static WebDriver getDriver() {
        return UIBaseTest.getDriver(); // Fetch driver automatically
    }
    // ThreadLocal to store WebDriverWait instances for parallel execution
    public static ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<WebDriverWait>();

    /**
     * Sets up an explicit wait (WebDriverWait) using ThreadLocal for thread safety.
     */
    public static void setWait(long timeoutInSeconds){
        waitThreadLocal.set(new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds)));
    }

    /**
     * Retrieves the WebDriverWait instance for the current thread.
     */
    public static WebDriverWait getWait() {
        return waitThreadLocal.get();
    }
    /**
     * Removes the WebDriverWait instance from ThreadLocal to prevent memory leaks.
     * Call this method in @AfterMethod in TestNG.
     */
    public static void removeWait() {
        waitThreadLocal.remove();
    }

    /**
     * Sets an implicit wait for the driver.
     * This applies to all findElement() calls and waits for elements to be available.
     */
    public static void implicitWait(){
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    /**
     * Waits until the given element is visible.
     * Uses the ThreadLocal wait instance to ensure thread safety.
     */
    public static void waitForVisibility(WebElement element,long timeOut){
        setWait(timeOut);
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
    public static void waitForElementToBeClickable(WebElement element,long timeOut){
        setWait(timeOut);
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

    public static ExpectedCondition<Boolean> elementToBeReady(WebElement element){
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
    public static void forceWait() throws InterruptedException {
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
    public static void numberOfWindowsToBe(long timeOut, int windows){
        setWait(timeOut);
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
    public static void waitForElementToBeVisible(WebElement element,Integer timeOut){
        setWait(timeOut);
        WebDriverWait wait = getWait();
        wait.until(displayed->element.isDisplayed());
        wait.until(displayed->element.isEnabled());
    }
}
