package utils;

import io.qameta.allure.Allure;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.Common.attachScreenshot;

public class TestListener implements ITestListener, IExecutionListener {
    private static final ThreadLocal<String> currentTestThreadIds = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        if (!BaseLogger.suiteStarted.get()) {
            BaseLogger.suiteStarted.set(true);
           // BaseLogger.info("===== TEST SUITE STARTED: " + context.getSuite().getName() + " =====");
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        currentTestThreadIds.set(threadId);
        BaseLogger.startTest(result.getMethod().getMethodName(), threadId);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String threadId = currentTestThreadIds.get();
        BaseLogger.endTest(result.getMethod().getMethodName(), "PASSED", threadId);
        Allure.step("Test Passed: " + result.getMethod().getMethodName());
        currentTestThreadIds.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String threadId = currentTestThreadIds.get();
        BaseLogger.error("Test Failed: " + result.getThrowable());
        BaseLogger.endTest(result.getMethod().getMethodName(), "FAILED", threadId);
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(io.qameta.allure.model.Status.FAILED));
        attachScreenshot(result.getMethod().getMethodName());
        currentTestThreadIds.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String threadId = currentTestThreadIds.get();
        BaseLogger.endTest(result.getMethod().getMethodName(), "SKIPPED", threadId);
        Allure.step("Test Skipped: " + result.getMethod().getMethodName());
        currentTestThreadIds.remove();
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
