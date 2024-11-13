package HMS;

import java.io.*;
import java.util.*;
import HMS.Patient.*;
import HMS.User.*;

public class PatientManager {
    private static final String CSV_FILE = "Patient_List.csv"; // Path to the CSV file
    private static List<Patient> patients = new ArrayList<>(); // List to store patient data

    // Load patient data from CSV file
    public static void loadPatients(List<User> users) {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file does not exist at path: " + file.getAbsolutePath());
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] patientData = line.split(",");
                if (patientData.length == 7) {
                    String patientID = patientData[0];
                    String name = patientData[1];
                    String dob = patientData[2];
                    String gender = patientData[3];
                    String contact = patientData[4];
                    String email = patientData[5];
                    String bloodType = patientData[6];

                    // Print out the loaded patient for debugging
                    System.out.println("Loaded Patient: " + patientID + ", " + name);

                    // Set role as "Patient"
                    String role = "Patient"; // Assuming all users in the CSV are patients
                    Patient patient = new Patient(patientID, name, dob, gender, contact, email, bloodType);
                    
                    users.add(patient);
                    patients.add(patient);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    // Check if the patient's login is valid by checking hospitalID, role, and password
    public static boolean isValidLogin(String hospitalID, String role, String inputPassword) {
        // Check if the patient exists and role matches
        for (Patient patient : patients) {
            if (patient.getHospitalID().trim().equals(hospitalID.trim())) {
                // Check if the role matches the provided role
                if (patient.getRole().equals(role)) {
                    if (patient.login(inputPassword)) {
                        return true; // Successful login
                    } else {
                        System.out.println("Invalid password.");
                        return false; // Incorrect password
                    }
                } else {
                    System.out.println("Role does not match.");
                    return false; // Role mismatch
                }
            }
        }
        System.out.println("User not found.");
        return false; // Patient not found
    }

    // Add or update a patient
    public static void addOrUpdatePatient(Patient patient, List<User> users) {
        // Check if the patient exists
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientID().equals(patient.getPatientID())) {
                patients.set(i, patient);  // Update existing patient
                savePatients();  // Save updated data to CSV
                return;
            }
        }

        // If patient does not exist, add a new one
        patients.add(patient);
        users.add(patient);
        savePatients();  // Save new data to CSV
    }

    // Save the patients' data back to the CSV file
    public static void savePatients() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Patient patient : patients) {
                String patientData = patient.getPatientID() + "," +
                        patient.getName() + "," +
                        patient.getDateOfBirth() + "," +
                        patient.getGender() + "," +
                        patient.getContactNumber() + "," +
                        patient.getEmailAddress() + "," +
                        patient.getBloodType();
                bw.write(patientData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    // Check if the patient is logging in for the first time
    public static boolean isFirstTimeLogin(String patientID) {
        // Check if the patient exists in the list (which was populated from CSV)
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientID)) {
                return false; // Patient exists, not first-time login
            }
        }
        return true; // Patient doesn't exist, first-time login
    }
}
