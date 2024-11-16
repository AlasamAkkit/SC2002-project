package HMS.App;

import java.util.*;
import HMS.Manager.*;
import HMS.Appointment.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.User.*;

public class MainApp {
    private static List<User> users = new ArrayList<>(); // All users in the system
    private static List<Patient> patients = new ArrayList<>(); // All patients
    private static Map<String, Medication> inventory = new HashMap<>(); // Medication inventory
    private static List<MedicalRecord> medicalRecords = new ArrayList<>(); // All completed medical records

    public static void main(String[] args) {
        DataInitializer.initialize(users, patients, inventory, medicalRecords); 

        System.out.println("Completed Medical Records: ");
        for (MedicalRecord record : medicalRecords) {
            System.out.println(record);
        }
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
