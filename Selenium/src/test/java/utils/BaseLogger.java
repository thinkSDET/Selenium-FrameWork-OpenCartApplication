// BaseLogger.java
package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class BaseLogger {

    private static final Logger logger = LogManager.getLogger(BaseLogger.class);
    private static final String logFilePath = System.getProperty("user.dir") + "/target/logs/log.txt";
    private static final Object lock = new Object(); // Lock for thread safety
    private static final ThreadLocal<StringBuilder> logBuffer = ThreadLocal.withInitial(StringBuilder::new);
    private static final ThreadLocal<StringBuilder> setupLogBuffer = ThreadLocal.withInitial(StringBuilder::new);

    // For handling static initialization logs - using Set to avoid duplicates
    private static final Set<String> staticInitLogs = new LinkedHashSet<>();
    private static final Set<String> processedLogs = new LinkedHashSet<>();
    private static final AtomicBoolean testContextActive = new AtomicBoolean(false);

    static {
        // Create log directory if it doesn't exist
        new File(System.getProperty("user.dir") + "/target/logs/").mkdirs();
    }

    public static void info(String message) {
        log("INFO", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    public static void warn(String message) {
        log("WARN", message);
    }

    public static void debug(String message) {
        log("DEBUG", message);
    }

    // Log setup tasks but don't print immediately
    public static void setupLog(String message) {
        String logMessage = formatLogMessage("INFO", message);
        setupLogBuffer.get().append(logMessage).append("\n");
        logger.info(logMessage);  // Log to console
    }

    // Flush setup logs inside the test context
    public static void flushSetupLogs() {
        synchronized (lock) {
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                // Write the setup logs
                writer.write(setupLogBuffer.get().toString());
                writer.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                setupLogBuffer.get().setLength(0); // Clear setup logs
            }
        }
    }

    // Start logging for a new test
    public static void startTest(String testName) {
        testContextActive.set(true);
        String startMessage = "\n===== STARTING TEST: " + testName + " =====";
        logBuffer.get().setLength(0); // Clear buffer first
        logBuffer.get().append(startMessage).append("\n");

        // Clear the processed logs for this test
        processedLogs.clear();

        // Include static initialization logs within the test logs
        // Skip any "BaseLogger initialized" messages
        for (String log : staticInitLogs) {
            if (!log.contains("BaseLogger initialized")) {
                logBuffer.get().append(log).append("\n");
                processedLogs.add(log); // Mark this log as processed
            }
        }

        logger.info(startMessage);
    }

    // End logging for a test
    public static void endTest(String testName, String status) {
        // Format status to stand out
        String formattedStatus = formatStatus(status);
        String endMessage = "===== END OF TEST: " + testName + " | STATUS: " + formattedStatus + " =====\n";
        logBuffer.get().append(endMessage).append("\n");
        flushLogs(testName);
        testContextActive.set(false);
        logger.info(endMessage);
    }

    // Format status to make it stand out
    private static String formatStatus(String status) {
        // Using ASCII formatting for logs
        if (status.equals("PASSED")) {
            return "**PASSED**";
        } else if (status.equals("FAILED")) {
            return "**FAILED**";
        } else if (status.equals("SKIPPED")) {
            return "**SKIPPED**";
        }
        return status;
    }

    private static void log(String level, String message) {
        String logMessage = formatLogMessage(level, message);

        // If we're not in a test context, log to static init logs
        if (!testContextActive.get() && !isInTestListenerContext()) {
            synchronized (staticInitLogs) {
                staticInitLogs.add(logMessage);
            }
            logger.info(logMessage);
            return;
        }

        // Check if this log has already been processed
        if (!processedLogs.contains(logMessage)) {
            logBuffer.get().append(logMessage).append("\n");
            processedLogs.add(logMessage);
            logger.info(logMessage);
        }
    }

    private static boolean isInTestListenerContext() {
        // Check if we're being called from TestListener
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().contains("TestListener")) {
                return true;
            }
        }
        return false;
    }

    private static String formatLogMessage(String level, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // Removed thread ID from log format
        return timestamp + " [" + level + "] " + message;
    }

    public static void flushLogs(String testName) {
        synchronized (lock) {
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                writer.write(logBuffer.get().toString());
                writer.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                logBuffer.remove();
                processedLogs.clear(); // Clear processed logs after flushing
            }
        }
    }
}