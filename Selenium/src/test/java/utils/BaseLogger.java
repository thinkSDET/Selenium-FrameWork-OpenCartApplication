package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseLogger {

    private static final Logger logger = LogManager.getLogger(BaseLogger.class);
    private static final String logFilePath = System.getProperty("user.dir") + "/target/logs/log.txt";
    private static final Object lock = new Object(); // Lock for thread safety
    private static final ThreadLocal<StringBuilder> logBuffer = ThreadLocal.withInitial(StringBuilder::new);
    private static final ThreadLocal<StringBuilder> setupLogBuffer = ThreadLocal.withInitial(StringBuilder::new);

    static {
        // Create log directory if it doesn't exist
        new File(System.getProperty("user.dir") + "/target/logs/").mkdirs();
    }

    public static void info(String message) {
        String logMessage = formatLogMessage("INFO", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.info(logMessage); // Log to console
    }

    public static void error(String message) {
        String logMessage = formatLogMessage("ERROR", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.error(logMessage); // Log to console
    }

    public static void warn(String message) {
        String logMessage = formatLogMessage("WARN", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.warn(logMessage); // Log to console
    }

    public static void debug(String message) {
        String logMessage = formatLogMessage("DEBUG", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.debug(logMessage); // Log to console
    }

    // Log setup steps
    public static void logSetup(String message) {
        String logMessage = formatLogMessage("INFO", message);
        setupLogBuffer.get().append(logMessage).append("\n");
        logger.info(logMessage); // Log to console
    }

    // Flush setup logs
    public static void flushSetupLogs() {
        synchronized (lock) {
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                writer.write(setupLogBuffer.get().toString()); // Write setup logs to the file
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                setupLogBuffer.get().setLength(0); // Clear the setup log buffer
            }
        }
    }

    // Flush logs for each test
    public static void flushLogs(String testName) {
        synchronized (lock) {
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                writer.write(logBuffer.get().toString()); // Write logs to the file
                writer.write("\n===== END OF TEST: " + testName + " =====\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                logBuffer.get().setLength(0); // Clear the buffer after flushing
            }
        }
    }

    private static String formatLogMessage(String level, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return timestamp + " [" + level + "] " + message; // Removed [Thread XX]
    }
}