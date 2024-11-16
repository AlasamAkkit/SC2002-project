package HMS.App;

import HMS.Appointment.*;
import HMS.Manager.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.User.*;
import java.util.*;

public class MainApp {
    private static List<User> users = new ArrayList<>(); // All users in the system
    private static List<Patient> patients = new ArrayList<>(); // All patients
    private static Map<String, Medication> inventory = new HashMap<>(); // Medication inventory
    private static List<MedicalRecord> medicalRecords = new ArrayList<>(); // All completed medical records

    public static void main(String[] args) {
        DataInitializer.initialize(users, patients, inventory, medicalRecords); 

        List<Appointment> appointments = AppointmentManager.getAppointments();
        List<Prescription> prescriptions = PrescriptionManager.getPrescriptions();
        List<ReplenishmentRequest> replenishmentRequests = ReplenishManager.getReplenishmentRequests();

        User loggedInUser = SelectionMenu.display(users, patients);

        if (loggedInUser != null) {
            System.out.println("Login successful! User: " + loggedInUser.getClass().getSimpleName());
            new UserMenuHandler().handleUserMenu(loggedInUser, patients, appointments, prescriptions, replenishmentRequests);
        } else {
            System.out.println("Login failed.");
        }
    }
}
