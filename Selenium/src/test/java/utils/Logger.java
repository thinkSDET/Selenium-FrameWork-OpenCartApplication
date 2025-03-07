/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package utils;

import org.apache.logging.log4j.LogManager;

public class Logger {

    private final org.apache.logging.log4j.Logger logger;

    // Private constructor
    private Logger(org.apache.logging.log4j.Logger logger) {
        this.logger = logger;
    }

    public static Logger getLogger(Class<?> clazz) { // Take class as parameter
        return new Logger(LogManager.getLogger(clazz));
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