package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseLogger {
    private static final Logger logger = LogManager.getLogger();
    private static final Pattern THREAD_PATTERN = Pattern.compile("TestNG-test-(.*?)-(\\d+)");

    static {
        setThreadNameContext();
    }

    private static void setThreadNameContext() {
        String fullThreadName = Thread.currentThread().getName();
        String simplifiedName;

        // Parse TestNG thread names like "TestNG-test-Cart Test-1"
        Matcher matcher = THREAD_PATTERN.matcher(fullThreadName);
        if (matcher.find()) {
            // Extract just "Cart_Test-1" from the full name
            String testName = matcher.group(1).replace(" ", "_");
            String threadNum = matcher.group(2);
            simplifiedName = testName + "-" + threadNum;
        } else {
            // For main thread or other non-TestNG threads
            simplifiedName = fullThreadName.replace(" ", "_");
        }

        ThreadContext.put("threadName", simplifiedName);
    }

    private static void ensureThreadNameSet() {
        String currentThread = Thread.currentThread().getName();

        // If the thread has changed, update the context
        if (!ThreadContext.containsKey("threadName") ||
                !currentThread.equals(ThreadContext.get("originalThreadName"))) {
            ThreadContext.put("originalThreadName", currentThread);
            setThreadNameContext();
        }
    }

    public static void setTestContext(String testName) {
        ensureThreadNameSet();
        ThreadContext.put("testName", testName);
    }

    public static void clearTestContext() {
        ThreadContext.remove("testName");
    }

    public static void info(String message) {
        ensureThreadNameSet();
        logger.info(message);
    }

    public static void warn(String message) {
        ensureThreadNameSet();
        logger.warn(message);
    }

    public static void error(String message) {
        ensureThreadNameSet();
        logger.error(message);
    }
    public static void debug(String message) {
        ensureThreadNameSet();
        logger.debug(message);
    }
}