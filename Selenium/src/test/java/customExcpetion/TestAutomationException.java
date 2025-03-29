/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package customExcpetion;

public class TestAutomationException extends RuntimeException{

    public TestAutomationException(String message) {
        super(message);
    }
    public TestAutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}
