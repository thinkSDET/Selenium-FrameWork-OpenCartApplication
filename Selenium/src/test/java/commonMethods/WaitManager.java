package commonMethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import setupBase.BaseClass;

import java.time.Duration;

public class WaitManager {
    // ThreadLocal to store WebDriverWait instances for parallel execution
    public static ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<WebDriverWait>();

    /**
     * Sets an implicit wait for the driver.
     * This applies to all findElement() calls and waits for elements to be available.
     */
    public static void implicitWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }
    /**
     * Waits until the given element is visible.
     * Uses the ThreadLocal wait instance to ensure thread safety.
     */
    public static void waitForVisibility(WebElement element){
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
    public void waitForElementToBeClickable(WebElement element){
        WebDriverWait wait = getWait();
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait is not initialized. Call setWait() first.");
        }
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
    /**
     * Sets up an explicit wait (WebDriverWait) using ThreadLocal for thread safety.
     */
    public static void setWait(WebDriver driver,long timeoutInSeconds){
        waitThreadLocal.set(new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)));
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
}
