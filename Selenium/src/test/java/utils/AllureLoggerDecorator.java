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
public class AllureLoggerDecorator {

    public static void info(String message) {
        BaseLogger.info(message);
        attachLogToAllure("[INFO] " + message);
    }

    public static void error(String message) {
        BaseLogger.error(message);
        attachLogToAllure("[ERROR] " + message);
    }

    public static void warn(String message) {
        BaseLogger.warn(message);
        attachLogToAllure("[WARN] " + message);
    }

    public static void debug(String message) {
       // BaseLogger.debug(message);
        attachLogToAllure("[DEBUG] " + message);
    }

    @Attachment(value = "Test Log", type = "text/plain")
    public static String attachLogToAllure(String message) {
        return message;
    }
}
