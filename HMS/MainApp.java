package HMS;

import java.util.*;
import HMS.Doctor.*;
import HMS.Manager.*;
import HMS.Admin.*;
import HMS.Appointment.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.Staff.*;
import HMS.User.*;

public class MainApp {
    private static List<User> users = new ArrayList<>(); // All users in the system
    private static List<Patient> patients = new ArrayList<>(); // All patients
    private static Map<String, Medication> inventory = new HashMap<>(); // Medication inventory

    public static void main(String[] args) {
        // Initialize sample data and load patient data from CSV
        PatientManager.loadPatients(users);
        MedicineManager.loadMedicines();
        AppointmentManager.loadAppointments();
        List<Appointment> appointments = AppointmentManager.getAppointments();
        StaffManager.loadStaff(users, appointments, inventory);

        LoginHandler loginHandler = new LoginHandler(users);
        Scanner scanner = new Scanner(System.in);

        User user = loginHandler.login(scanner);  // Login returns a User (could be Patient, Doctor, etc.)
        if (user != null) {
            System.out.println("Login successful! User: " + user.getClass().getSimpleName());
            
            // Handle menu display based on the user type
            if (user instanceof Patient) {
                Patient patient = (Patient) user;
                PatientMenu patientMenu = new PatientMenu(patient);
                patientMenu.displayMenu();
            } else if (user instanceof Staff) {
                Staff staffMember = (Staff) user;
                chooseMenu(staffMember, patients, appointments);
            } else {
                System.out.println("Role does not have a display menu.");
            }
        } else {
            System.out.println("Login failed.");
        }
    }

    public static void chooseMenu(Staff staff, List<Patient> patients, List<Appointment> appointments) {
        // Get the role of the staff from getRole()
        String role = staff.getRole();

        switch(role.toLowerCase()) {
            case "doctor":
                if (staff instanceof Doctor) {
                    System.out.println("Launching Doctor Menu..."); // Debugging: print message when launching doctor menu
                    DoctorMenu doctorMenu = new DoctorMenu((Doctor) staff, patients);
                    doctorMenu.displayMenu(); // This is the method that should display the menu
                } else {
                    System.out.println("Error: Staff member is not a doctor.");
                }
                break;
    
            case "pharmacist":
                System.out.println("Launching Pharmacist Menu...");
                PharmacistMenu pharmacistMenu = new PharmacistMenu((Pharmacist) staff, appointments);
                pharmacistMenu.displayMenu();
                break;
    
            case "administrator":
                System.out.println("Launching Admin Menu...");
                AdminMenu adminMenu = new AdminMenu((Administrator) staff, appointments);
                adminMenu.displayMenu();
                break;
    
            default:
                System.out.println("Unknown role, no menu available.");
                break;
        }
    }

    // private static void initializeData() {
    //     // Add sample appointments
    //     appointments.add(new Appointment("A001", "P001", "D001", LocalDateTime.now().plusDays(1)));
    //     appointments.add(new Appointment("A002", "P002", "D002", LocalDateTime.now().plusDays(2)));

    //     System.out.println("Sample data initialized.");
    // }
}
