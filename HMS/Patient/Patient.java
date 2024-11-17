package HMS.Patient;

import HMS.User.*;

public class Patient extends User {
    private String patientID;
    private String name;
    private String dateOfBirth; // Format: YYYY-MM-DD
    private String gender; // Options: Male, Female, Other
    private String contactNumber;
    private String emailAddress;
    private String bloodType;

    // Constructor
    public Patient(String hospitalID, String name, String dateOfBirth, String gender,
                   String contactNumber, String emailAddress, String bloodType, String password, int loginCount) {
        super(hospitalID, "Patient", name, password, loginCount);
        this.patientID = hospitalID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
    }

    // Getters
    public String getPatientID() { return patientID; }
    public String getName() { return name; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getContactNumber() { return contactNumber; }
    public String getEmailAddress() { return emailAddress; }
    public String getBloodType() { return bloodType; }

    // Setters for updating personal information
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String toString() {
        return "Staff [ID=" + getPatientID() + ", Name=" + getName() + ", Role=" + getRole() + ", Gender=" + gender + "]";
    }
}
