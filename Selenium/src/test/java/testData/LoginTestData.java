package testData;

import org.testng.annotations.DataProvider;

public class LoginTestData {

    @DataProvider(name = "loginWithAdmin")
    public static Object[][] loginWithAdmin(){
        Object [][] testData = new Object[1][2];
        testData[0][0] = "Admin";
        testData[0][1] = "admin123";
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
