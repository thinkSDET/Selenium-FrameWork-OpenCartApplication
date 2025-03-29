/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testBase;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.BaseLogger;

import java.time.Duration;

import static testBase.DriverManager.*;

public class UIBaseTest {

    /**
     * Set up the WebDriver before each test method.
     *
     * Note : @BeforeMethod should not be static because they operate on instance methods.
     */
    @BeforeMethod(alwaysRun = true)
    public void setDriver() {
        initializeDriver(); // Call the new method
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().deleteAllCookies();
            driver.get(ConfigManager.getBaseUrl());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit(); // Quit the driver after the test is finished
            BaseLogger.info("Driver is closed");
            remove();// Clean up the ThreadLocal reference
        }
    }
}
