/**
 * Copyright (c) 2025 [thinkSDET]
 * Unauthorized copying, distribution, modification, or use of this file, via any medium, is strictly prohibited.
 * Proprietary and confidential.
 */

package testData;

import dataClasses.PersonalDetails;

public class PersonalDetailsTestData {

    public static PersonalDetails getPersonalDetailsData(){
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.firstName="Ankit";
        personalDetails.middleName="Kumar";
        personalDetails.lastName="Singh";
        personalDetails.empId="9876";
        personalDetails.otherId="009988";
        personalDetails.drivingLicId="ddL1234";
        personalDetails.licenseExpDate="2029-20-04";
        personalDetails.nationality="Dutch";
        personalDetails.maritalStatus="Married";
        return personalDetails;
    }
}
