/*
 * *
 *  * Copyright (c) 2025 [thinkSDET]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

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

    static {
        // Define log directory inside "target/logs/"
        String logDir = System.getProperty("user.dir") + "/target/logs/";
        new File(logDir).mkdirs();  // Create directory if not exists
        // Define log file inside "logs/log.txt"
        logFilePath = logDir + "log.txt";
    }


    public static void info(String message) {
        logger.info(message);
        writeToFile("[INFO] " + message);
    }


    public static void error(String message) {
        logger.error(message);
        writeToFile("[ERROR] " + message);
    }


    public static void warn(String message) {
        logger.warn(message);
        writeToFile("[WARN] " + message);
    }


    public static void debug(String message) {
        logger.debug(message);
        writeToFile("[DEBUG] " + message);
    }

    private static void writeToFile(String message) {
        synchronized (lock) { // Ensure only one thread writes at a time
            try (FileWriter writer = new FileWriter(logFilePath, true)) {
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                writer.write(timestamp + " " + message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
