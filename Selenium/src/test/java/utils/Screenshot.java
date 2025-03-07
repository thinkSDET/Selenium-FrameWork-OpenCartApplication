/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * What This Does:
 *
 * Captures a screenshot and saves it in /reports/screenshots/.
 * Generates a unique filename using a timestamp.
 */
public class Screenshot {

    public static String captureScreenshot(WebDriver driver, String testName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH-mm-ss");
        String timestamp = dateFormat.format(new Date());

        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdirs();  // Ensure directory exists
        }

        String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(screenshotPath);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
}
