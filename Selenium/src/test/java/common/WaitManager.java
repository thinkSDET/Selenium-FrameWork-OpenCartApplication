/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.UIBaseTest;
import testBase.baseUtils.LoggerUtil;

import java.time.Duration;

public class WaitManager {
    private static final LoggerUtil logger = LoggerUtil.getLogger(WaitManager.class);
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
    // Return the composite condition
    public static ExpectedCondition<Boolean> elementToBeReady(WebElement element){
        return ExpectedConditions.and(
                ExpectedConditions.visibilityOf(element),
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    public static void forceWait() throws InterruptedException {
        Thread.sleep(500);
    }

    /**
     *
     * @param timeOut
     * @param windows
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
