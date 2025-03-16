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
    private static final Object lock = new Object(); // Thread safety

    private static final ThreadLocal<StringBuilder> logBuffer = ThreadLocal.withInitial(StringBuilder::new);
    private static final ThreadLocal<Set<String>> processedLogsByTest = ThreadLocal.withInitial(LinkedHashSet::new);

    private static final Set<String> staticInitLogs = new LinkedHashSet<>();
    private static final AtomicBoolean testContextActive = new AtomicBoolean(false);
    private static final ThreadLocal<String> currentTestName = ThreadLocal.withInitial(() -> null);
    protected static final AtomicBoolean suiteStarted = new AtomicBoolean(false);

    static {
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

    public static void startTest(String testName) {
        // **Fix: Ensure Suite Start Logs Appear Only Once**
        if (!suiteStarted.getAndSet(true)) {
            log("INFO", "===== TEST SUITE STARTED =====");
            synchronized (staticInitLogs) {
                staticInitLogs.add("===== TEST SUITE STARTED =====");
            }
        }

        currentTestName.set(testName);
        testContextActive.set(true);

        String startMessage = "\n===== STARTING TEST: " + testName + " =====";
        logBuffer.get().setLength(0);
        logBuffer.get().append(startMessage).append("\n");

        processedLogsByTest.get().clear();

        // **Fix: Ensure Static Logs Are Added Only Once**
        synchronized (staticInitLogs) {
            for (String log : staticInitLogs) {
                if (!processedLogsByTest.get().contains(log)) {
                    logBuffer.get().append(log).append("\n");
                    processedLogsByTest.get().add(log);
                }
            }
        }

        logger.info(startMessage);
    }

    public static void endTest(String testName, String status) {
        if (!testName.equals(currentTestName.get())) {
            logger.warn("Mismatched test ending: Expected " + currentTestName.get() + " but got " + testName);
        }

        String formattedStatus = formatStatus(status);
        String endMessage = "===== END OF TEST: " + testName + " | STATUS: " + formattedStatus + " =====\n";
        logBuffer.get().append(endMessage).append("\n");

        flushLogs(testName);
        testContextActive.set(false);
        currentTestName.remove();
        logger.info(endMessage);
    }

    // **Fix: Ensure Reset Clears Thread-Specific Data**
    public static void reset() {
        logBuffer.get().setLength(0);
        processedLogsByTest.get().clear();
        testContextActive.set(false);
        currentTestName.remove();
    }

    private static void log(String level, String message) {
        String logMessage = formatLogMessage(level, message);

        if (!testContextActive.get()) {
            synchronized (staticInitLogs) {
                staticInitLogs.add(logMessage);
            }
            logger.info(logMessage);
            return;
        }

        if (!processedLogsByTest.get().contains(logMessage)) {
            logBuffer.get().append(logMessage).append("\n");
            processedLogsByTest.get().add(logMessage);
            logger.info(logMessage);
        }
    }

    private static String formatStatus(String status) {
        return switch (status) {
            case "PASSED" -> "**PASSED**";
            case "FAILED" -> "**FAILED**";
            case "SKIPPED" -> "**SKIPPED**";
            default -> status;
        };
    }

    private static String formatLogMessage(String level, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
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
                logBuffer.get().setLength(0);
            }
        }
    }
}
