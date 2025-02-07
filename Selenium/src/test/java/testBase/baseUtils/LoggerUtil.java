package testBase.baseUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {

    private final Logger logger;

    // Private constructor
    private LoggerUtil(Logger logger) {
        this.logger = logger;
    }

    public static LoggerUtil getLogger(Class<?> clazz) { // Take class as parameter
        return new LoggerUtil(LogManager.getLogger(clazz));
    }

    public void info(String message) {
        logger.info(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public void warn(String message) {
        logger.warn(message);
    }
}