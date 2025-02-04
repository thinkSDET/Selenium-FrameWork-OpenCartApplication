package testBase.baseUtils;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.UIBaseTest;

/**
 * What This Does:
 *
 * Initializes Extent Reports before tests start.
 * Logs test status (PASS, FAIL, SKIP).
 * Captures a screenshot on test failure and attaches it to the report.
 * Flushes the report after all tests finish.
 */
public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.setupReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable());

        // Capture screenshot and attach it
        WebDriver driver = UIBaseTest.getDriver();
        if (driver != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
            ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flushReport();
    }
}
