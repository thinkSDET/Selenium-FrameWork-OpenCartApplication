/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
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
import utils.BaseLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static boolean isBaseUrlLogged = false; //Prevent duplicate logging
    private static final Properties properties = new Properties();
    private ConfigManager(){
   // Prevent instantiation
    }
     /**
       The use of a static block in the ConfigReader class is a deliberate design choice to ensure that the
       configuration properties are loaded only once when the class is first loaded into memory.
            */
    static {
        synchronized (ConfigManager.class) {
            loadProperties();
        }
    }

    private static void loadProperties() {
        String env = System.getProperty("ENV", "qa"); // Default to "qa" if ENV is not set
        String filePath = "src/test/resources/config-" + env + ".properties";
        try (FileInputStream file = new FileInputStream(filePath)) {
            properties.load(file);
            BaseLogger.info("Loaded config file: " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file: " + filePath + ". Error: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        String baseUrl = getProperty("BASE_URL");
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new FrameworkException("Base URL is missing in the config file!");
        }
        // Log Base URL only once
        if (!isBaseUrlLogged) {
            BaseLogger.info("Using Base URL: " + baseUrl);
            isBaseUrlLogged = true;
        }
        return baseUrl;
    }
    /**
     * Fetches credentials from environment variables to enhance security and avoid hardcoding sensitive data.

     * Environment Variables:
     *  USER_NAME: Username for authentication.
     *  USER_PASSWORD: Corresponding password.
     *
     *  Note: If running locally, ensure these variables are set on your machine,otherwise, both values will be null.
     *
     * Set variables before running:
     * Windows: set USER_NAME=myUser & set USER_PASSWORD=myPass
     */
    private static final String userName = System.getenv("USER_NAME");
    private static final String password = System.getenv("USER_PASSWORD");

    /**
     * Browser configuration
     * System Properties: Reads values using System.getProperty(), making it easy to override at runtime.
     */
    public static String getBrowser() {
        return System.getProperty("browser", ConfigManager.getProperty("BROWSER"));
    }
    /**
     Headless mode configuration
     System Properties: Reads values using System.getProperty(), making it easy to override at runtime.
     */
    public static boolean isHeadlessMode() {
        return Boolean.parseBoolean(System.getProperty("HEADLESS", ConfigManager.getProperty("HEADLESS")));
    }
    /**
     * Fetches username from environment variables for security reasons.
     * @return Username
     */
    public static String getUserName(){
        return userName;
    }
    /**
     * Fetches password from environment variables for security reasons.
     * @return Password
     */
    public static String getPassword(){
        return password;
    }
}
