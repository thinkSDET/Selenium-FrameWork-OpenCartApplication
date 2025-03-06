/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

/**
 * Yes, it should be thread-safe, but currently, it is not fully thread-safe because of how Properties is used.
 * Let’s analyze why and how to improve it.
 */
/**
 * Static Initialization Block (static {}) is Thread-Safe
 * The static block ensures that the properties are loaded only once when the class is loaded.
 * Java ensures that static initialization is executed by one thread only, so this part is safe.
 */
/**
 * you can just use synchronization while loading properties but not during read operations.
 *
 * Ensures Only One Thread Loads Properties → synchronized (ConfigReader.class) {}
 * Reading Properties is Already Thread-Safe (No extra complexity)
 * Uses Try-With-Resources (try (...)) → Ensures the file stream closes properly
 */
package testBase;
import customExcpetion.FrameworkException;
import utils.LoggerUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();
    private static final LoggerUtil logger = LoggerUtil.getLogger(ConfigReader.class); // For static block


    static {
        synchronized (ConfigReader.class) {  // Ensures only one thread loads the properties
            try (FileInputStream file = new FileInputStream("src/test/resources/config.properties")) {
                logger.info("Configuration file loaded successfully.");
                properties.load(file);
            } catch (IOException e) {
                // LOGGER.error("Failed to load configuration file: {}", e.getMessage());
                throw new RuntimeException("Failed to load configuration file: " + e.getMessage());
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);  // Reading is thread-safe
    }

    public static String getBaseUrl() {
        String env = System.getProperty("ENV"); // Read from System Properties first
        if (env == null || env.isEmpty()) {
            env = getProperty("ENV"); // Fallback to config.properties if not set
        }

        if (env == null || env.isEmpty()) {
            logger.error("ENV property is missing. Please provide a valid environment.");
            // For learning purposes, demonstrating how we can use a custom exception in our framework.
            throw new FrameworkException("'ENV' property is required");
        }

        String baseUrl;
        switch (env.toLowerCase()) {
            case "qa":
                baseUrl = getProperty("QA_URL");
                logger.info("Launched the QA URL: " + baseUrl);
                break;
            case "preprod":
                baseUrl = getProperty("PREPROD_URL");
                logger.info("Launched the PREPROD URL: " + baseUrl);
                break;
            case "prod":
                baseUrl = getProperty("PROD_URL");
                logger.info("Launched the PROD URL: " + baseUrl);
                break;
            default:
                logger.error("Invalid ENV value provided: [" + env + "]");
                /**
                 *  If the ENV value is missing or incorrect, the test setup cannot proceed correctly.
                 * Since the environment determines the execution context,
                 * throwing an exception ensures that tests do not run with an undefined or incorrect configuration.
                 */
                throw new FrameworkException("Invalid ENV value: " + env);
        }
        return baseUrl;
    }

}
