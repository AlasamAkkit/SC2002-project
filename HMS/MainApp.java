package HMS;

import java.time.LocalDateTime;
import java.util.*;

public class MainApp {
    private static List<User> users = new ArrayList<>(); // All users in the system
    private static List<Patient> patients = new ArrayList<>(); // All patients
    private static List<Appointment> appointments = new ArrayList<>(); // All appointments
    private static Map<String, Medication> inventory = new HashMap<>(); // Medication inventory

    public static void main(String[] args) {
        // Initialize sample data
        initializeData();

        Scanner scanner = new Scanner(System.in);

        // Simulate a login process
        while (true) {
            System.out.println("Welcome to the Hospital Management System");
            System.out.print("Enter your hospital ID: ");
            String hospitalID = scanner.nextLine();
            System.out.print("Enter your role (Patient, Doctor, Pharmacist, Administrator): ");
            String role = scanner.nextLine();

            // Find user by hospital ID and role
            User user = login(hospitalID, role);
            if (user != null) {
                System.out.println("Login successful!\n");
                launchUserMenu(user);
            } else {
                System.out.println("Invalid login. Please try again.\n");
            }
        }
    }

    private static User login(String hospitalID, String role) {
        for (User user : users) {
            if (user.getHospitalID().equals(hospitalID) && user.getRole().equalsIgnoreCase(role)) {
                return user;
            }
        }
        return null; // User not found
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
        Patient patient1 = new Patient("P001", "Alice", "1990-01-01", "Female", "1234567890", "alice@example.com", "A+");
        Patient patient2 = new Patient("P002", "Bob", "1985-05-05", "Male", "0987654321", "bob@example.com", "O-");
        patients.add(patient1);
        patients.add(patient2);

        // Add sample doctors
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

        // Add sample appointments
        Appointment appointment1 = new Appointment("AP001", patient1.getHospitalID(), doctor1.getHospitalID(), LocalDateTime.now().plusDays(1));
        Appointment appointment2 = new Appointment("AP002", patient2.getHospitalID(), doctor2.getHospitalID(), LocalDateTime.now().plusDays(2));
        appointments.add(appointment1);
        appointments.add(appointment2);

        System.out.println("Sample data initialized.");
    }
}

