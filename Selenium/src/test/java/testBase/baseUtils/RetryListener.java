package testBase.baseUtils;


import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class); // This is ESSENTIAL
    }
}
/**
 * annotation.setRetryAnalyzer(RetryAnalyzer.class):
 * This line ensures that the RetryAnalyzer is applied to all the test methods in the class.
 * RetryAnalyzer.class is the class that contains the retry logic.
 */

/**
 * RetryListener automatically applies the RetryAnalyzer to all test methods, allowing failed tests to be retried based on the logic defined in RetryAnalyzer.
 */