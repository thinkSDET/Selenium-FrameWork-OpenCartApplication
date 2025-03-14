/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package utils;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utils.Screenshot.addEnvironmentInfo;
import static utils.Screenshot.attachScreenshot;

/**
 * What This Does:
 *
 * Initializes Extent Reports before tests start.
 * Logs test status (PASS, FAIL, SKIP).
 * Captures a screenshot on test failure and attaches it to the report.
 * Flushes the report after all tests finish.
 */
public class TestListener implements ITestListener {

    private static final LoggerDecorator logger = new AllureLoggerDecorator(new BaseLogger());
    private static final String allureResultsPath;
    static {
        // Store directly in "target/allure-results/"
        allureResultsPath = System.getProperty("user.dir") + "/target/allure-results/";
        File resultsDir = new File(allureResultsPath);
        // Purani files delete karne ka logic
        if (resultsDir.exists()) {
            for (File file : resultsDir.listFiles()) {
                file.delete();
            }
        }
        // Ensure directory exists
        resultsDir.mkdirs();

        // Set system property for Allure
        System.setProperty("allure.results.directory", allureResultsPath);
    }



    @Override
    public void onStart(ITestContext context) {
        System.out.println("DEBUG: onStart() method called");
        logger.info("\n===== TEST EXECUTION STARTED =====");
        addEnvironmentInfo();
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("\n===== STARTING TEST: " + result.getMethod().getMethodName() + " =====");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test PASSED: " + result.getMethod().getMethodName());
        logger.info("===== END OF TEST: " + result.getMethod().getMethodName() + " =====\n");
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test FAILED: " + result.getMethod().getMethodName());
        logger.error("Reason: " + result.getThrowable());
        logger.info("===== END OF TEST: " + result.getMethod().getMethodName() + " =====\n");
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(io.qameta.allure.model.Status.FAILED));
        // Ensure screenshot is being captured
         attachScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test SKIPPED: " + result.getMethod().getMethodName());
        logger.info("===== END OF TEST: " + result.getMethod().getMethodName() + " =====\n");
        Allure.step("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
            logger.info("\n===== ALL TESTS EXECUTION COMPLETED =====\n");   // Ensures flush is only called once
    }
}
