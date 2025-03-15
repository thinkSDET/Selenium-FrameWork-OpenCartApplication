package utils;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static utils.Common.attachScreenshot;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        BaseLogger.flushSetupLogs(); // Ensure setup logs are flushed inside the test
        BaseLogger.startTest(result.getMethod().getMethodName()); // Start logging for this test
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseLogger.endTest(result.getMethod().getMethodName(), "PASSED");
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseLogger.endTest(result.getMethod().getMethodName(), "FAILED");
        BaseLogger.error("Reason: " + result.getThrowable());
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(io.qameta.allure.model.Status.FAILED));
        attachScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        BaseLogger.endTest(result.getMethod().getMethodName(), "SKIPPED");
        Allure.step("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        BaseLogger.info("===== ALL TESTS EXECUTION COMPLETED =====");
    }
}