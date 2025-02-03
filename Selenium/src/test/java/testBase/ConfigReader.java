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
import org.apache.logging.log4j.LogManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigReader {

    private static final Properties properties = new Properties();
    private static final Logger LOGGER = Logger.getLogger(ConfigReader.class.getName());

    static {
        synchronized (ConfigReader.class) {  // Ensures only one thread loads the properties
            try (FileInputStream file = new FileInputStream("src/test/resources/config.properties")) {
                LOGGER.info("Configuration file loaded successfully.");
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
        String env = getProperty("ENV").toLowerCase();
        String url;

        switch (env) {
            case "qa":
                url = getProperty("QA_URL");
                break;
            case "preprod":
                url = getProperty("PREPROD_URL");
                break;
            case "prod":
                url = getProperty("PROD_URL");
                break;
            default:
              //  LOGGER.error("Invalid ENV value: {}", env);
                throw new RuntimeException("Invalid ENV value in config.properties");
        }
        LOGGER.info("Launching tests on environment: " + env.toUpperCase() + " | URL: " + url);
        return url;
    }
}
