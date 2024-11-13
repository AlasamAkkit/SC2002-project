package HMS;

//import java.time.LocalDateTime;
import java.util.*;
import HMS.Doctor.*;
import HMS.Admin.*;
import HMS.Appointment.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.Staff.Staff;
import HMS.User.*;
//import HMS.Staff.*;

public class MainApp {
    private static List<User> users = new ArrayList<>(); // All users in the system
    private static List<Patient> patients = new ArrayList<>(); // All patients
    //private static List<Staff> staff = new ArrayList<>(); // All patients
    private static List<Appointment> appointments = new ArrayList<>(); // All appointments
    //private static Map<String, Medication> inventory = new HashMap<>(); // Medication inventory

    public static void main(String[] args) {
        // Initialize sample data and load patient data from CSV
        PatientManager.loadPatients(users);
        StaffManager.loadStaff(users);

        LoginHandler loginHandler = new LoginHandler(users);
        Scanner scanner = new Scanner(System.in);

        User user = loginHandler.login(scanner);  // Login returns a User (could be Patient, Doctor, etc.)
        if (user != null) {
            System.out.println("Login successful! User: " + user.getClass().getSimpleName());
            System.out.println("User role: " + getRole(user));  // Print the role of the logged-in user
            launchUserMenu(user);
        } else {
            System.out.println("Login failed.");
        }
    }

    private static void launchUserMenu(User user) {
        System.out.println("Logged in as: " + user.getClass().getSimpleName());
        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            DoctorMenu menu = new DoctorMenu(doctor, patients);
            menu.displayMenu();
        } 
        // Check if the user is a Pharmacist
        else if (user instanceof Pharmacist) {
            Pharmacist pharmacist = (Pharmacist) user;
            PharmacistMenu menu = new PharmacistMenu(pharmacist, appointments);
            menu.displayMenu();
        }
        // Check if the user is an Administrator
        else if (user instanceof Administrator) {
            Administrator admin = (Administrator) user;
            AdminMenu menu = new AdminMenu(admin);
            menu.displayMenu();
        } 
        // Default for Patient
        else if (user instanceof Patient) {
            Patient patient = (Patient) user;
            PatientMenu menu = new PatientMenu(patient);
            menu.displayMenu();
        }
    }

    private static String getRole(User user) {
        if (user instanceof Patient) {
            return "Patient";
        } else if (user instanceof Doctor) {
            return "Doctor";
        } else if (user instanceof Pharmacist) {
            return "Pharmacist";
        } else if (user instanceof Administrator) {
            return "Administrator";
        } else {
            return "Unknown Role";
        }
    }



    /*private static void initializeData() {
        // Add sample patients
        //Patient patient1 = new Patient("P001", "Alice", "1990-01-01", "Female", "1234567890", "alice@example.com", "A+");
        //Patient patient2 = new Patient("P002", "Bob", "1985-05-05", "Male", "0987654321", "bob@example.com", "O-");
        //users.add(patient1);
        //users.add(patient2);

        // Add sample doctors
        Doctor doctor1 = new Doctor("D001", "Doctor", "Dr Tan");
        Doctor doctor2 = new Doctor("D002", "Doctor", "Dr Tim");
        users.add(doctor1);
        users.add(doctor2);

        // Add sample pharmacists
        Pharmacist pharmacist1 = new Pharmacist("PH001", "Pharmacist", "Dr Tom", inventory);
        users.add(pharmacist1);

        // Add sample administrator
        Administrator admin = new Administrator("A001", "Administrator", "Mr admin", users, appointments, inventory);
        users.add(admin);

        // Add sample medications
        inventory.put("Aspirin", new Medication("Aspirin", 100, 10));
        inventory.put("Ibuprofen", new Medication("Ibuprofen", 200, 20));

        // Add sample appointments
        if(!patients.isEmpty()){
            Appointment appointment1 = new Appointment("AP001", patients.get(0).getHospitalID(), doctor1.getHospitalID(), LocalDateTime.now().plusDays(1));
            Appointment appointment2 = new Appointment("AP002", patients.get(1).getHospitalID(), doctor2.getHospitalID(), LocalDateTime.now().plusDays(2));
            appointments.add(appointment1);
            appointments.add(appointment2);
        }
        

        System.out.println("Sample data initialized.");
    }*/
}
