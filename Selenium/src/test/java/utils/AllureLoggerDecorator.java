/*
 * *
 *  * Copyright (c) 2025 [thinkSDET]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package utils;

import io.qameta.allure.Attachment;

//AllureLoggerDecorator: Adds Allure Reporting
public class AllureLoggerDecorator implements LoggerDecorator{

    private final LoggerDecorator wrappedLogger;
    public AllureLoggerDecorator(LoggerDecorator logger) {
        this.wrappedLogger = logger;
    }

    @Override
    public void info(String message) {
        wrappedLogger.info(message);
        attachLogToAllure("[INFO] " + message);
    }

    @Override
    public void error(String message) {
        wrappedLogger.error(message);
        attachLogToAllure("[ERROR] " + message);
    }

    @Override
    public void warn(String message) {
        wrappedLogger.warn(message);
        attachLogToAllure("[WARN] " + message);
    }

    @Override
    public void debug(String message) {
        wrappedLogger.error(message);
        attachLogToAllure("[DEBUG] " + message);
    }

    @Attachment(value = "Test Log", type = "text/plain")
    public static String attachLogToAllure(String message) {
        return message;
    }
}
