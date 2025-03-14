/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import testBase.ConfigManager;
import testBase.UIBaseTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * What This Does:
 *
 * Captures a screenshot and saves it in /reports/screenshots/.
 * Generates a unique filename using a timestamp.
 */
public class Screenshot {

    /**
     * Attaches a screenshot to Allure on test failure.
     */
    @Attachment(value = "Failure Screenshot", type = "image/png")
    protected static void attachScreenshot(String testName) {
        WebDriver driver = UIBaseTest.getDriver();
        if (driver != null) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = "screenshot_" + testName + "_" + System.currentTimeMillis() + ".png";
            File destFile = new File(System.getProperty("allure.results.directory") + "/" + fileName);
            try {
                Files.copy(screenshot.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Allure.addAttachment("Screenshot - " + testName, new FileInputStream(destFile));
                System.out.println("SUCCESS: Screenshot File Saved & Attached -> " + fileName);
            } catch (IOException e) {
                System.out.println("ERROR: Screenshot file copy failed.");
            }
        }
        }

    /**
     * Adds environment details to Allure Report.
     */
    protected static void addEnvironmentInfo() {
        // Environment details
        String envDetails = "Execution Details:\n"
                + "Environment=" + System.getProperty("ENV", "qa") + "\n"
                + "Headless Mode=" + ConfigManager.isHeadlessMode() + "\n"
                + "URL=" + ConfigManager.getBaseUrl();

        // Attach in Allure
        Allure.addAttachment("Environment", envDetails);

        // **Write to environment.properties file**
        try {
            File envFile = new File(System.getProperty("allure.results.directory") + "/environment.properties");
            FileWriter writer = new FileWriter(envFile);
            writer.write(envDetails.replace("\n", "\n"));  // Ensure correct formatting
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR: Failed to create environment.properties file - " + e.getMessage());
        }
    }
}
