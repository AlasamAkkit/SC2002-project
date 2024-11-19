package HMS.Manager;

import HMS.Patient.*;
import HMS.User.*;
import java.io.*;
import java.util.*;

/**
 * Manages patient data, including loading from and saving to a CSV file, and validating patient login details.
 */
public class PatientManager {
    private static final String CSV_FILE = "HMS/Data/Patient_List.csv"; // Path to the CSV file
    private static List<Patient> patients = new ArrayList<>(); // List to store patient data

    /**
     * Loads patient data from a CSV file into the provided user list.
     * @param users the list of users where loaded patients will be added
     */
    public static void loadPatients(List<User> users, List<Patient> patients) {
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
                if (patientData.length == 9) {
                    String patientID = patientData[0];
                    String name = patientData[1];
                    String dob = patientData[2];
                    String gender = patientData[3];
                    String contact = patientData[4];
                    String email = patientData[5];
                    String bloodType = patientData[6];
                    String password = patientData[7];
                    int loginCount = Integer.parseInt(patientData[8]);  // Assuming loginCount is the last field


                    // Print out the loaded patient for debugging
                    Patient patient = new Patient(patientID, name, dob, gender, contact, email, bloodType, password, loginCount);
                    users.add(patient);
                    patients.add(patient);
                    System.out.println("Loaded Patient: " + patientID + ", " + name); // Debugging line
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Validates the login credentials of a patient.
     * @param hospitalID the hospital ID of the patient
     * @param role the role of the user attempting to log in
     * @param inputPassword the password provided by the user
     * @return true if the login credentials are valid, false otherwise
     */
    public static boolean isValidLogin(String hospitalID, String role, String inputPassword) {
        // Check if the patient exists and role matches
        for (Patient patient : patients) {
            if (patient.getHospitalID().equals(hospitalID)) {
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

    /**
     * Adds or updates a patient in the system.
     * @param patient the patient to add or update
     * @param users the list of all users to which the patient may be added
     */
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

    /**
     * Saves all patient data back to the CSV file.
     */
    public static void savePatients() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, true))) {  // 'true' for append mode
            // If the file is empty, write the header; otherwise, append data without the header
            File file = new File(CSV_FILE);
            if (file.length() == 0) {  // If file is empty, write the header
                bw.write("Patient ID,Name,Date of Birth,Gender,Contact Information,Email,Blood Type,Password,loginCount");
                bw.newLine();
            }
    
            // Write patient data
            for (Patient patient : patients) {
                String patientData = patient.getPatientID() + "," +
                        patient.getName() + "," +
                        patient.getDateOfBirth() + "," +
                        patient.getGender() + "," +
                        patient.getContactNumber() + "," +
                        patient.getEmailAddress() + "," +
                        patient.getBloodType() + "," +
                        patient.getPassword() + "," +
                        patient.getLoginCount(); // Assuming loginCount is a field
                bw.write(patientData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }
    

    /**
     * Determines if a patient is logging in for the first time.
     * @param hospitalID the hospital ID of the patient
     * @return true if it's the first login, false otherwise
     */
    public static boolean isFirstTimeLogin(String hospitalID) {
        for (Patient patient : patients) {
            System.out.println("Checking against patient: " + patient.getPatientID());
            if (patient.getPatientID().equals(hospitalID)) {
                System.out.println("Patient found: " + patient.getPatientID());
                
                // If loginCount is greater than 0, don't prompt for password change
                if (patient.getLoginCount() > 0) {
                    return false; // Not first-time login
                }
                return true; // First-time login
            }
        }
        return true;
    }

    /**
     * Finds a patient by their ID.
     * @param patientId the ID of the patient to find
     * @return the found Patient object, or null if no patient is found
     */
    public static Patient findPatientById(String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientId)) {
                return patient;
            }
        }
        return null; // Return null if no patient found
    }
}
