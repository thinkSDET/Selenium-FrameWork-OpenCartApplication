/*
 * *
 *  * Copyright (c) 2025 [thinkSDET]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package testData;

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.Random;

public class TestDataGenerator {

    //private static final String FILE_PATH = "src/test/resources/generated_test_data.properties";
    private static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*";

    public static String generateRandomUsername() {
        return "User" + new Random().nextInt(100000);
    }

    public static String generateRandomEmail() {
        return "test" + System.currentTimeMillis() + "@mail.com";
    }

    public static String generateRandomPassword(int length) {
        /**
         * SecureRandom is used instead of Random for cryptographically secure password generation.
         * PASSWORD_CHARS contains uppercase, lowercase, numbers, and special characters to ensure strong passwords.
         * The method generateRandomPassword(int length) picks random characters from PASSWORD_CHARS to create a secure password of the desired length.
         */
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(PASSWORD_CHARS.charAt(random.nextInt(PASSWORD_CHARS.length())));
        }
        return password.toString();
    }

/*    private static void saveTestData(String key, String value) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            Properties prop = new Properties();
            prop.setProperty(key, value);
            prop.store(writer, "Generated Test Data");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save test data: " + e.getMessage());
        }
    }*/
}
