package utils;

import io.qameta.allure.Allure;
import org.testng.*;

public class TestListener implements ITestListener, IExecutionListener,ISuiteListener{

   /* @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        BaseLogger.startTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        BaseLogger.endTest(testName, "PASSED");  // ✅ Fixed: Removed threadId argument
        Allure.step("Test Passed: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        BaseLogger.error("Test Failed: " + result.getThrowable());
        BaseLogger.endTest(testName, "FAILED");  // ✅ Fixed: Removed threadId argument
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(io.qameta.allure.model.Status.FAILED));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        BaseLogger.endTest(testName, "SKIPPED");  // ✅ Fixed: Removed threadId argument
        Allure.step("Test Skipped: " + testName);
    }

    @Override
    public void onFinish(ITestContext context) {
        BaseLogger.info("===== TEST SUITE COMPLETED: " + context.getName() + " =====");
    }

    @Override
    public void onExecutionStart() {
        Common.deleteAllureResults();
    }*/
   @Override
   public void onStart(ISuite suite) {
       BaseLogger.suiteStart(suite.getName());
   }

    @Override
    public void onTestStart(ITestResult result) {
        BaseLogger.testStart(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        BaseLogger.testEnd(result.getMethod().getMethodName(), "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseLogger.testEnd(result.getMethod().getMethodName(), "FAILED");
    }
}
