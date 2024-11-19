package HMS.App;

import HMS.Appointment.*;
import HMS.Manager.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.User.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The entry point for the Healthcare Management System (HMS).
 * This class initializes the system's data and handles the main application flow,
 * including user login and menu handling.
 */
public class MainApp {
    private static List<User> users = new ArrayList<>(); // All users in the system
    private static List<Patient> patients = new ArrayList<>(); // All patients
    private static Map<String, Medication> inventory = new HashMap<>(); // Medication inventory
    private static List<MedicalRecord> medicalRecords = new ArrayList<>(); // All completed medical records

    /**
     * The main method that initiates the HMS application.
     * It loads all necessary data for the application to run, handles user login, and directs to appropriate user menus.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Initialize all necessary data for the application from storage
        DataInitializer.initialize(users, patients, inventory, medicalRecords); 

        // Retrieve updated lists after initialization
        List<Appointment> appointments = AppointmentManager.getAppointments();
        List<ReplenishmentRequest> replenishmentRequests = ReplenishManager.getReplenishmentRequests();

        // Create an empty set to store existing patient IDs
        Set<String> existingPatientIDs = new HashSet<>();
        
        // Populate existing patient IDs from the existing patients list
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
        } else {
            // Populate existing patient IDs from the existing patients list
            for (Patient patient : patients) {
                String patientID = patient.getPatientID();
                if (patientID != null && !patientID.isEmpty()) {
                    existingPatientIDs.add(patientID);
                }
            }
            // Debugging: Verify the existing IDs
            System.out.println("Existing Patient IDs: " + existingPatientIDs);
        }

        System.out.println("Existing Patient IDs: " + medicalRecords);
        // Initialize the IDGenerator with the existing patient IDs
        IDGenerator idGenerator = new IDGenerator(existingPatientIDs);

        // Create an instance of SelectionMenu and call the display method
        SelectionMenu selectionMenu = new SelectionMenu(users, patients, new InputHandler(), idGenerator);
        User loggedInUser = selectionMenu.display(users, patients);  // Call the non-static display method

        // Determine if the login was successful and direct the user to the appropriate menu
        if (loggedInUser != null) {
            System.out.println("Login successful! User: " + loggedInUser.getClass().getSimpleName());
            new UserMenuHandler().handleUserMenu(loggedInUser, patients, appointments, replenishmentRequests);
        } else {
            System.out.println("Login failed.");
        }
    }
}
