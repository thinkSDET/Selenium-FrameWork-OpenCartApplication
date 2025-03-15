package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BaseLogger: Logs messages to console and writes thread-safe logs to a single file.
 */
public class BaseLogger {

    private static final Logger logger = LogManager.getLogger(BaseLogger.class);
    private static final String logFilePath;
    private static final Object lock = new Object(); // Lock for thread safety
    private static final ThreadLocal<StringBuilder> logBuffer = ThreadLocal.withInitial(StringBuilder::new);

    static {
        // Define log directory inside "target/logs/"
        String logDir = System.getProperty("user.dir") + "/target/logs/";
        new File(logDir).mkdirs();  // Create directory if not exists
        logFilePath = logDir + "log.txt"; // Log file location
    }

    public static void info(String message) {
        String logMessage = formatLogMessage("INFO", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.info(logMessage); // Console & File Sync Fix
    }

    public static void error(String message) {
        String logMessage = formatLogMessage("ERROR", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.error(logMessage);
    }

    public static void warn(String message) {
        String logMessage = formatLogMessage("WARN", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.warn(logMessage);
    }

    public static void debug(String message) {
        String logMessage = formatLogMessage("DEBUG", message);
        logBuffer.get().append(logMessage).append("\n");
        logger.debug(logMessage);
    }

    private static String formatLogMessage(String level, String message) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        long threadId = Thread.currentThread().getId();
        return timestamp + " [" + level + "] " + "[Thread " + threadId + "] " + message;
    }

    // Flush logs for each test
    public static void flushLogs(String testName) {
        synchronized (lock) {
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                writer.write(logBuffer.get().toString()); // Sirf logs likhne do, STARTING TEST yahan se hata diya
                writer.write("\n===== END OF TEST: " + testName + " =====\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                logBuffer.remove(); // Clear buffer after flush
            }
        }
    }

}
