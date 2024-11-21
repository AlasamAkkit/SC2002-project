package HMS.Patient;

import HMS.User.*;

/**
 * Represents a patient in the hospital management system, extending the User class with specific attributes
 * for a patient's medical and personal information.
 */
public class Patient extends User {
    private String patientID;
    private String name;
    private String dateOfBirth; // Format: YYYY-MM-DD
    private String gender; // Options: Male, Female, Other
    private String contactNumber;
    private String emailAddress;
    private String bloodType;

    /**
     * Constructs a Patient instance with detailed personal and contact information.
     * @param hospitalID The unique identifier for the patient.
     * @param name The full name of the patient.
     * @param dateOfBirth The patient's date of birth in YYYY-MM-DD format.
     * @param gender The patient's gender.
     * @param contactNumber The patient's contact phone number.
     * @param emailAddress The patient's email address.
     * @param bloodType The blood type of the patient.
     * @param password The patient's account password.
     * @param loginCount The number of times the patient has logged into the system.
     */
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
    
    @Override
    /**
     * gets the patient's name.
     * @return the patient's name.
     */
    public String getName() { return name; }

    /**
     * gets the patient's date of birth.
     * @return the patient's date of birth.
     */
    public String getDateOfBirth() { return dateOfBirth; }

    /**
     * gets the patient's gender.
     * @return the patient's gender.
     */
    public String getGender() { return gender; }

    /**
     * gets the patient's contact number.
     * @return the patient's number.
     */
    public String getContactNumber() { return contactNumber; }

    /**
     * gets the patient's email address.
     * @return the patient's email.
     */
    public String getEmailAddress() { return emailAddress; }

    /**
     * gets the patient's blood type.
     * @return the patient's blood type.
     */
    public String getBloodType() { return bloodType; }

    /**
     * Sets the patient's contact number.
     * @param contactNumber The new contact number.
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * Sets the patient's email address.
     * @param emailAddress The new email address.
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Displays the medical record for the patient, listing all personal details managed within the system.
     */
    public void viewMedicalRecord() {
        System.out.println("Medical Record:");
        System.out.println("Patient ID: " + getHospitalID());
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Blood Type: " + bloodType);
        // System.out.println("Past Diagnoses: " + String.join(", ", pastDiagnoses));
        // System.out.println("Treatments: " + String.join(", ", treatments));
    }

    @Override
    public String toString() {
        return "Staff [ID=" + getPatientID() + ", Name=" + getName() + ", Role=" + getRole() + ", Gender=" + gender + "]";
    }
}
