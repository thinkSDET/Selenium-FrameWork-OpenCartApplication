/*
 * *
 *  * Copyright (c) 2025 [thinkSDET]
 *  * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 *  * Proprietary and confidential.
 *
 *
 */

package utils;

// This will define the contract for all logging types.
public interface LoggerDecorator {
    void info(String message);
    void error(String message);
    void warn(String message);
    void debug(String message);
}
