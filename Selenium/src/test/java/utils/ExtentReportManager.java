/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * What This Does:
 *
 * Initializes Extent Reports and generates an HTML report in /reports/.
 * Uses ThreadLocal to ensure parallel execution compatibility.
 * Provides methods to start tests, log steps, and flush reports.
 */
public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // Synchronized method to ensure only one instance of ExtentReports is created
    public static synchronized void setupReport() {
        if (extent == null) {
            extent = new ExtentReports();

            // Define the base directory (current working directory)
            String baseDir = System.getProperty("user.dir");

            // Define the target and extent-report directories
            String targetDir = baseDir + File.separator + "target";
            String extentReportDir = targetDir + File.separator + "extent-report";

            // Create the target and extent-report directories if they don't exist
            createDirectory(targetDir);
            createDirectory(extentReportDir);

            // Generate a timestamp for the report directory
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportDir = extentReportDir + File.separator + "Report_" + timestamp;

            // Create the timestamped report directory
            createDirectory(reportDir);

            // Define the report file path
            String reportFilePath = reportDir + File.separator + "ExtentReport.html";

            // Initialize the ExtentSparkReporter with the report file path
            ExtentSparkReporter spark = new ExtentSparkReporter(reportFilePath);
            extent.attachReporter(spark);
        }
    }

    public static synchronized void startTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Utility method to create directories
    private static void createDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}

