package HMS.App;

import HMS.Appointment.*;
import HMS.Manager.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.User.*;
import java.util.*;

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
        List<Prescription> prescriptions = PrescriptionManager.getPrescriptions();
        List<ReplenishmentRequest> replenishmentRequests = ReplenishManager.getReplenishmentRequests();

        // Display login and selection menu, and process user login
        User loggedInUser = SelectionMenu.display(users, patients);

        // Determine if the login was successful and direct the user to the appropriate menu
        if (loggedInUser != null) {
            System.out.println("Login successful! User: " + loggedInUser.getClass().getSimpleName());
            new UserMenuHandler().handleUserMenu(loggedInUser, patients, appointments, prescriptions, replenishmentRequests);
        } else {
            System.out.println("Login failed.");
        }
    }
}
