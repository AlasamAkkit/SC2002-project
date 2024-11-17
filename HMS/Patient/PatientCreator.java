package HMS.Patient;

import HMS.Manager.PatientManager;
import HMS.User.User;
import java.util.List;
import java.util.Scanner;

/**
 * This class provides functionalities to create new patient accounts in the hospital management system.
 */
public class PatientCreator {

       /**
        * Prompts the user to input details and creates a new patient account.
        * Adds the new patient to the list of users and patients and updates the patient database.
        *
        * @param users A list of all users in the system, including patients.
        * @param patients A list of all patients.
        */
        public static void createPatientAccount(List<User> users, List<Patient> patients) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Create a new patient account.");

        // Generate a unique Patient ID
        String patientID = generateUniquePatientID(patients);

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Date of Birth: ");
        String dob = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Blood Type: ");
        String bloodtype = scanner.nextLine();

        // Create a new Patient object
        Patient newPatient = new Patient(patientID, name, dob, gender, contactNumber, email, bloodtype, "password", 0);
        patients.add(newPatient);
        users.add(newPatient);

        PatientManager.addOrUpdatePatient(newPatient, users);

        // Let the patient know their generated Patient ID
        System.out.println("Your Patient ID is: " + patientID);
        System.out.println("Account created successfully!");
    }

    /**
     * Generates a unique patient ID by checking existing IDs.
     *
     * @param patients The list of existing patients.
     * @return A unique Patient ID.
     */
    private static String generateUniquePatientID(List<Patient> patients) {
        int patientCount = patients.size();
        String patientID;

        // Generate a new Patient ID (e.g., P0001, P0002, P0003...)
        do {
            patientCount++;
            patientID = "P" + String.format("%04d", patientCount); // PXXXX format
        } while (isPatientIDExists(patientID, patients)); // Check if the ID already exists

        return patientID;
    }

    /**
     * Checks if a patient ID already exists in the list of patients.
     *
     * @param patientID The patient ID to check.
     * @param patients The list of existing patients.
     * @return true if the ID exists, false otherwise.
     */
    private static boolean isPatientIDExists(String patientID, List<Patient> patients) {
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientID)) {
                return true; // ID exists
            }
        }
        return false; // ID is unique
    }
}
