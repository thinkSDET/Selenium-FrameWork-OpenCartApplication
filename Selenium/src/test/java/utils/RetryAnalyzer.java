/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * The RetryAnalyzer class implements TestNG's IRetryAnalyzer interface, which allows you to retry failed tests a specified number of times.
 * The class tracks the number of retry attempts for each test method, and once the number of retries exceeds the maximum allowed retries, it stops retrying.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount = 1; // Number of retries

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying test: " + result.getName() + " | Attempt: " + (retryCount + 1));
            return true; // Retry test
        }
        return false; // Stop retrying after reaching max attempts
    }
}
