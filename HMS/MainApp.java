package HMS;

import java.time.LocalDateTime;
import java.util.*;
import HMS.Doctor.*;
import HMS.Admin.*;
import HMS.Appointment.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.User.*;

public class MainApp {
    private static List<User> users = new ArrayList<>(); // All users in the system
    private static List<Patient> patients = new ArrayList<>(); // All patients
    private static List<Appointment> appointments = new ArrayList<>(); // All appointments
    private static Map<String, Medication> inventory = new HashMap<>(); // Medication inventory

    public static void main(String[] args) {
        // Initialize sample data and load patient data from CSV
        PatientManager.loadPatients(users);
        initializeData();

        Scanner scanner = new Scanner(System.in);
        LoginHandler loginHandler = new LoginHandler(users);

        // Simulate a login process
        while (true) {
            User user = loginHandler.login(scanner);
            if (user != null) {
                launchUserMenu(user);
            }
        }
    }

    private static void launchUserMenu(User user) {
        if (user instanceof Patient) {
            PatientMenu menu = new PatientMenu((Patient) user);
            menu.displayMenu();
        } else if (user instanceof Doctor) {
            DoctorMenu menu = new DoctorMenu((Doctor) user, patients);
            menu.displayMenu();
        } else if (user instanceof Pharmacist) {
            PharmacistMenu menu = new PharmacistMenu((Pharmacist) user, appointments);
            menu.displayMenu();
        } else if (user instanceof Administrator) {
            AdminMenu menu = new AdminMenu((Administrator) user);
            menu.displayMenu();
        }
    }

    private static void initializeData() {
        // Add sample patients
        //Patient patient1 = new Patient("P001", "Alice", "1990-01-01", "Female", "1234567890", "alice@example.com", "A+");
        //Patient patient2 = new Patient("P002", "Bob", "1985-05-05", "Male", "0987654321", "bob@example.com", "O-");
        //users.add(patient1);
        //users.add(patient2);

        // Load sample data (like doctors, pharmacists, admins, etc.)
        Doctor doctor1 = new Doctor("D001", "Doctor");
        Doctor doctor2 = new Doctor("D002", "Doctor");
        users.add(doctor1);
        users.add(doctor2);

        // Add sample pharmacists
        Pharmacist pharmacist1 = new Pharmacist("PH001", "Pharmacist", inventory);
        users.add(pharmacist1);

        // Add sample administrator
        Administrator admin = new Administrator("A001", "Administrator", users, appointments, inventory);
        users.add(admin);

        // Add sample medications
        inventory.put("Aspirin", new Medication("Aspirin", 100, 10));
        inventory.put("Ibuprofen", new Medication("Ibuprofen", 200, 20));

        // Add sample appointments (e.g. loaded from file or generated)
        Appointment appointment1 = new Appointment("AP001", "P001", "D001", LocalDateTime.now().plusDays(1));
        Appointment appointment2 = new Appointment("AP002", "P002", "D002", LocalDateTime.now().plusDays(2));
        appointments.add(appointment1);
        appointments.add(appointment2); 

        System.out.println("Sample data initialized.");
    }
}
