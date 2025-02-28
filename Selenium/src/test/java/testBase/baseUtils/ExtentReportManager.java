/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package testBase.baseUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

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
            ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/ExtentReport.html");
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
}

