package HMS.Patient;

import HMS.Manager.PatientManager;
import HMS.User.User;
import java.util.List;
import java.util.Scanner;

public class PatientCreator {
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

    // Method to check if the Patient ID already exists in the list
    private static boolean isPatientIDExists(String patientID, List<Patient> patients) {
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientID)) {
                return true; // ID exists
            }
        }
        return false; // ID is unique
    }
}
