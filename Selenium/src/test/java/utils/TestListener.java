package utils;

import io.qameta.allure.Allure;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static utils.Common.attachScreenshot;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        if (!BaseLogger.suiteStarted.getAndSet(true)) {
            BaseLogger.info("===== TEST SUITE STARTED: " + context.getSuite().getName() + " =====");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        BaseLogger.reset();
        BaseLogger.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseLogger.endTest(result.getMethod().getMethodName(), "PASSED");
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseLogger.error("Test Failed: " + result.getThrowable());
        BaseLogger.endTest(result.getMethod().getMethodName(), "FAILED");
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
