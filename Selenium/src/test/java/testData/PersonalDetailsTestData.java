/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testData;

import dataClasses.PersonalDetails;

import java.util.Random;

public class PersonalDetailsTestData {

    public static PersonalDetails getPersonalDetailsData(){
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.firstName = "User" + new Random().nextInt(10000);
        personalDetails.middleName = "Auto" + new Random().nextInt(5000);
        personalDetails.lastName = "Test" + new Random().nextInt(3000);
        personalDetails.empId = String.valueOf(new Random().nextInt(9999));
        personalDetails.otherId = String.valueOf(new Random().nextInt(9999));
        personalDetails.drivingLicId = "DL" + new Random().nextInt(100000);
        personalDetails.licenseExpDate = "2029-12-31";
        personalDetails.nationality = "Dutch";
        personalDetails.maritalStatus = "Married";
        return personalDetails;
    }
}
