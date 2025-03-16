package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class BaseLogger {
    private static final Logger logger = LogManager.getLogger(BaseLogger.class);
    private static final String logFilePath = System.getProperty("user.dir") + "/target/logs/log.txt";
    private static final Object fileLock = new Object(); // Lock for file operations

    // **Thread-local storage for test-specific logs**
    private static final ThreadLocal<StringBuilder> logBuffer = ThreadLocal.withInitial(StringBuilder::new);
    private static final ThreadLocal<String> currentTestThreadId = new ThreadLocal<>();
    private static final ThreadLocal<String> currentTestName = new ThreadLocal<>();
    private static final ThreadLocal<Set<String>> processedLogsByTest = ThreadLocal.withInitial(LinkedHashSet::new);

    // **Global suite logs storage**
    private static final Set<String> staticInitLogs = ConcurrentHashMap.newKeySet();
    protected static final AtomicBoolean suiteStarted = new AtomicBoolean(false);

    static {
        new File(System.getProperty("user.dir") + "/target/logs/").mkdirs();
    }

    // ✅ **Logging Methods**
    public static void info(String message) { log("INFO", message); }
    public static void error(String message) { log("ERROR", message); }
    public static void warn(String message) { log("WARN", message); }
    public static void debug(String message) { log("DEBUG", message); }

    public static void startTest(String testName, String threadId) {
        currentTestName.set(testName);
        currentTestThreadId.set(threadId);
        logger.info("DEBUG-START: Test=" + testName + ", Expected Thread=" + threadId + ", Actual Thread=" + Thread.currentThread().getId());

        String startMessage = "\n===== STARTING TEST: " + testName + " (Thread-" + threadId + ") =====";
        clearTestLogs();  // ✅ Clear per-test logs only

        logBuffer.get().append(startMessage).append("\n");

        // **Log static suite initialization messages again**
        if (suiteStarted.compareAndSet(false, true)) {
            info("===== TEST SUITE STARTED =====");
        }

        for (String log : staticInitLogs) {
            logBuffer.get().append(log).append("\n");
        }

        logger.info(startMessage);
    }

    public static void endTest(String testName, String status, String threadId) {
        if (!testName.equals(currentTestName.get())) {
            warn("Mismatched test ending: Expected " + currentTestName.get() + " but got " + testName);
        }

        String formattedStatus = formatStatus(status);
        String endMessage = "===== END OF TEST: " + testName + " | STATUS: " + formattedStatus + " (Thread-" + threadId + ") =====\n";

        logBuffer.get().append(endMessage).append("\n");
        flushLogs(testName, threadId);

        currentTestThreadId.remove();
        currentTestName.remove();
        processedLogsByTest.remove(); // Explicitly remove ThreadLocal
        clearTestLogs();  // ✅ Reset only after writing logs
        logger.info("DEBUG-END: Test=" + testName + ", Expected Thread=" + threadId + ", Actual Thread=" + Thread.currentThread().getId());

        logger.info(endMessage);
    }

    // ✅ **Clear per-test logs while keeping global suite logs intact**
    protected static void clearTestLogs() {
        logBuffer.get().setLength(0);
        processedLogsByTest.get().clear();
    }

    private static void log(String level, String message) {
        String currentThreadId = currentTestThreadId.get();
        logger.info("DEBUG-LOG: Level=" + level + ", Message=\"" + message + "\", Expected Thread=" + currentThreadId + ", Actual Thread=" + Thread.currentThread().getId());
        String logMessage = formatLogMessage(level, message, currentThreadId);

        // **Global log preservation**
        if (currentTestName.get() == null) {
            staticInitLogs.add(logMessage);
            logger.info(logMessage);
            return;
        }

        logBuffer.get().append(logMessage).append("\n");
        logger.info(logMessage);
    }

    private static String formatStatus(String status) {
        return switch (status) {
            case "PASSED" -> "**PASSED**";
            case "FAILED" -> "**FAILED**";
            case "SKIPPED" -> "**SKIPPED**";
            default -> status;
        };
    }

    private static String formatLogMessage(String level, String message, String threadId) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Ensure logs are assigned to the correct test thread
        String actualThreadId = (threadId != null) ? "Thread-" + threadId : "Thread-" + Thread.currentThread().getId();

        return String.format("%s [%s] [%s] %s", timestamp, level, actualThreadId, message);
    }

    public static void flushLogs(String testName, String threadId) {
        synchronized (fileLock) {
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                writer.write("\n");
                writer.write(logBuffer.get().toString());
                writer.write("\n");
            } catch (IOException e) {
                logger.error("Failed to write logs to file: " + e.getMessage(), e);
            }
        }
    }
}
