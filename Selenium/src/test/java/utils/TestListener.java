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

    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        BaseLogger.setTestContext(testName);
        BaseLogger.info("---- Starting test: " + testName + " ----");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseLogger.info("---- Test Passed: " + result.getMethod().getMethodName() + " ----");
        BaseLogger.clearTestContext();
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseLogger.info("---- Test Failed: " + result.getMethod().getMethodName() + " ----");
        BaseLogger.clearTestContext();
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(io.qameta.allure.model.Status.FAILED));
        attachScreenshot(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
       //
    }

    @Override
    public void onFinish(ITestContext context) {
       // BaseLogger.info("===== TEST CONTEXT COMPLETED: " + context.getName() + " =====");
    }

    @Override
    public void onExecutionStart() {
        Common.deleteAllureResults();
    }
}
