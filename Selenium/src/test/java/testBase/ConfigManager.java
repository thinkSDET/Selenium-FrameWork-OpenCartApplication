/*
 * *
 *  * Copyright (c) 2025 [Vivek Srivastava]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package testBase;

public class ConfigManager {

    // Private constructor to prevent instantiation
    //Private Constructor: Prevents creating an instance of ConfigManager, as it's a utility class.
    private ConfigManager() {}

    //Default Values: Ensures the framework has sensible defaults (chrome for the browser and false for headless mode).

    /**
     * Browser configuration
     * System Properties: Reads values using System.getProperty(), making it easy to override at runtime.
     */
    private static final String BROWSER = System.getProperty("browser", "chrome"); // Default to Chrome
    /**
     Headless mode configuration
    System Properties: Reads values using System.getProperty(), making it easy to override at runtime.
    */
    private static final boolean HEADLESS_MODE = Boolean.parseBoolean(System.getProperty("HEADLESS", "false")); // Default to headed mode

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

    // Getters for configuration properties
    public static String getBrowser() {
        return BROWSER;
    }

    public static boolean isHeadlessMode() {
        return HEADLESS_MODE;
    }

    public static String getUserName(){
        return userName;
    }

    public static String getPassword(){
        return password;
    }
}
