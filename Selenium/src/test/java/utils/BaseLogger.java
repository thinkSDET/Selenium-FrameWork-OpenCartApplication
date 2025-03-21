package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Reporter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BaseLogger {
    private static final Logger logger = LogManager.getLogger();
    private static final BlockingQueue<LogMessage> messageQueue = new LinkedBlockingQueue<>();
    private static final LogProcessor processor = new LogProcessor();

    static {
        processor.start();
    }
    public static void info(String message) {
        log(null, message, "INFO");
    }

    public static void error(String message) {
        log(null, message, "ERROR");
    }

    public static void warn(String message) {
        log(null, message, "WARN");
    }

    public static void log(String source, String message, String level) {
        String threadId = String.format("[Thread ID: %d]", Thread.currentThread().getId());
        String className = (source != null) ? source.substring(source.lastIndexOf('.') + 1) : "";

        // Get test name from TestNG context (if available)
        String testName = Reporter.getCurrentTestResult() != null
                ? Reporter.getCurrentTestResult().getMethod().getMethodName()
                : "UNKNOWN";

        String formattedMessage = String.format("%-25s %-20s [Test: %s] - %s",
                threadId, className, testName, message);

        messageQueue.offer(new LogMessage(formattedMessage, level));
    }


    public static void suiteStart(String suiteName) {
        log(null, "Suite Started: " + suiteName, "INFO");
        log(null, "------------------------", "INFO");
    }

    public static void testStart(String testName) {
        log(null, "\nTest Started: " + testName, "INFO");
    }

    public static void testEnd(String testName, String status) {
        log(null, "Test " + status + ": " + testName, "INFO");
        log(null, "------------------------", "INFO");
    }

    private static class LogProcessor extends Thread {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                try {
                    LogMessage message = messageQueue.take(); // Correctly fetch message from queue
                    writeLog(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }

        private void writeLog(LogMessage message) {
            switch (message.getLevel().toUpperCase()) {
                case "INFO":
                    logger.info(message.getMessage());
                    break;
                case "ERROR":
                    logger.error(message.getMessage());
                    break;
                case "WARN":
                    logger.warn(message.getMessage());
                    break;
                default:
                    logger.info(message.getMessage());
            }
        }
    }

    private static class LogMessage {
        private final String message;
        private final String level;

        public LogMessage(String message, String level) {
            this.message = message;
            this.level = level;
        }

        public String getMessage() {
            return message;
        }

        public String getLevel() {
            return level;
        }
    }

}
