package testData;

import dataClasses.PersonalDetails;

public class PersonalDetailsTestData {

    public static PersonalDetails getPersonalDetailsData(){
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.firstName="Ankit";
        personalDetails.middleName="Kumar";
        personalDetails.lastName="Singh";
        return personalDetails;
    }
}
