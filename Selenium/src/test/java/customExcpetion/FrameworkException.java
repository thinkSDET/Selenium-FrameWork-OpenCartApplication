/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package customExcpetion;

public class FrameworkException extends RuntimeException{

    public FrameworkException(String message) {
        super(message);
    }
    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
