package HMS.Patient;

import HMS.Manager.PatientManager;
import HMS.User.User;
import HMS.Utility.EmailValidator;
import HMS.Utility.IDGenerator;
import HMS.Utility.InputHandler;
import java.util.*;

public class PatientCreator {
    private final InputHandler inputHandler;
    private final IDGenerator idGenerator;

    public PatientCreator(InputHandler inputHandler, IDGenerator idGenerator) {
        this.inputHandler = inputHandler;
        this.idGenerator = idGenerator;
    }

    public void createPatientAccount(List<User> users, List<Patient> patients) {
        System.out.println("Create a new patient account.");

        // Generate unique Patient ID
        String patientID = idGenerator.generateUniqueID();

        // Collect user inputs
        String name = inputHandler.getInput("Enter Name: ");
        String dob = inputHandler.getDate("Enter Date of Birth (e.g., YYYY-MM-DD): ");
        String gender = inputHandler.getGender("Enter Gender (M/F): ");
        String contactNumber = inputHandler.getValidPhoneNumber("Enter Contact Number: ");
        String email = inputHandler.getValidatedInput("Enter Email (123@123.com): ", new EmailValidator());
        String bloodType = inputHandler.getValidBloodType("Enter Blood Type (e.g., A+, O-): ");

        // Create Patient
        Patient newPatient = new Patient(patientID, name, dob, gender, contactNumber, email, bloodType, "password", 0);
        patients.add(newPatient);
        users.add(newPatient);

        // Update database
        PatientManager.addOrUpdatePatient(newPatient, users);

        System.out.println("Account created successfully!");
        System.out.println("Your Patient ID is: " + patientID);
        System.out.println("Your Default password is: password");
    }
}
