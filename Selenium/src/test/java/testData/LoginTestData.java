/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testData;

import common.CommonMethods;
import org.testng.annotations.DataProvider;
import testBase.ConfigManager;

public class LoginTestData {

    @DataProvider(name = "loginWithAdmin")
    public static Object[][] loginWithAdmin(){
        Object [][] testData = new Object[1][2];
        testData[0][0] = ConfigManager.getUserName();
        testData[0][1] = ConfigManager.getPassword();
        return testData;
    }

    @DataProvider(name = "WrongUserNameAndPassword")
    public static Object[][] WrongUserNameAndPassword(){
        Object[][] testData = new Object[1][2];
        testData[0][0] ="test";
        testData[0][1] = "test";
        return testData;
    }
}
