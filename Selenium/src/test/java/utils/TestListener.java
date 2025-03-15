package utils;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static utils.Common.attachScreenshot;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        BaseLogger.flushSetupLogs(); // Flush setup logs after the test starts
        BaseLogger.info("\n===== STARTING TEST: " + result.getMethod().getMethodName() + " =====");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseLogger.info("Test PASSED: " + result.getMethod().getMethodName());
        BaseLogger.flushLogs(result.getMethod().getMethodName());
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseLogger.error("Test FAILED: " + result.getMethod().getMethodName());
        BaseLogger.error("Reason: " + result.getThrowable());
        BaseLogger.flushLogs(result.getMethod().getMethodName());
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(io.qameta.allure.model.Status.FAILED));
        attachScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        BaseLogger.warn("Test SKIPPED: " + result.getMethod().getMethodName());
        BaseLogger.flushLogs(result.getMethod().getMethodName());
        Allure.step("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        BaseLogger.info("===== ALL TESTS EXECUTION COMPLETED =====");
    }
}