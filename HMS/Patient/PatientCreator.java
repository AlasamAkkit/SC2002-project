package HMS.Patient;

import HMS.Manager.PatientManager;
import HMS.User.User;
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
        String dob = inputHandler.getInput("Enter Date of Birth (e.g., YYYY-MM-DD): ");
        String gender = inputHandler.getInput("Enter Gender (Male/Female/Other): ");
        String contactNumber = inputHandler.getValidatedInput("Enter Contact Number: ", new NumericValidator());
        String email = inputHandler.getValidatedInput("Enter Email: ", new EmailValidator());
        String bloodType = inputHandler.getInput("Enter Blood Type (e.g., A+, O-): ");
        String password = inputHandler.getInput("Enter Password: ");

        // Create Patient
        Patient newPatient = new Patient(patientID, name, dob, gender, contactNumber, email, bloodType, password, 0);
        patients.add(newPatient);
        users.add(newPatient);

        // Update database
        PatientManager.addOrUpdatePatient(newPatient, users);

        System.out.println("Account created successfully!");
        System.out.println("Your Patient ID is: " + patientID);
    }
}
