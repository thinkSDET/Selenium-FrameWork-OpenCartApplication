package utils;

import io.qameta.allure.Allure;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static utils.Common.attachScreenshot;

public class TestListener implements ITestListener, IExecutionListener {

    @Override
    public void onStart(ITestContext context) {
        // No changes needed here for this issue.
    }

    @Override
    public void onTestStart(ITestResult result) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        BaseLogger.startTest(result.getMethod().getMethodName(), threadId);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        BaseLogger.endTest(result.getMethod().getMethodName(), "PASSED", threadId);
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        BaseLogger.error("Test Failed: " + result.getThrowable());
        BaseLogger.endTest(result.getMethod().getMethodName(), "FAILED", threadId);
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(io.qameta.allure.model.Status.FAILED));
        attachScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        BaseLogger.endTest(result.getMethod().getMethodName(), "SKIPPED", threadId);
        Allure.step("Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        BaseLogger.info("===== TEST CONTEXT COMPLETED: " + context.getName() + " =====");
    }

    @Override
    public void onExecutionStart() {
        Common.deleteAllureResults();
    }
}