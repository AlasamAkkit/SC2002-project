package HMS;

import java.util.List;
import java.util.Scanner;
import HMS.User.*;
import HMS.Patient.*;

public class LoginHandler {
    private List<User> users; // List of all users in the system

    // Constructor to initialize with the list of users
    public LoginHandler(List<User> users) {
        this.users = users;
    }

    // Method to perform login process
    public User login(Scanner scanner) {
        System.out.println("Welcome to the Hospital Management System");
        System.out.print("Enter your hospital ID: ");
        String hospitalID = scanner.nextLine();
        System.out.print("Enter your role (Patient, Doctor, Pharmacist, Administrator): ");
        String role = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Attempt login
        User user = authenticate(hospitalID, role, password);
        if (user != null) {
            System.out.println("Login successful!");

            // Handle first-time login if the user is a patient
            if (role.equalsIgnoreCase("Patient") && PatientManager.isFirstTimeLogin(hospitalID)) {
                handleFirstTimeLogin(hospitalID, scanner);
            } else {
                System.out.println("Welcome back!");
            }
        } else {
            System.out.println("Invalid login. Please try again.\n");
        }

        return user;
    }

    // Method to authenticate user based on ID, role, and password
    private User authenticate(String hospitalID, String role, String password) {
        for (User user : users) {
            if (user.getHospitalID().equals(hospitalID) && user.getRole().equalsIgnoreCase(role)) {
                if (user.login(password)) {
                    return user;
                } else {
                    System.out.println("Invalid password.");
                    return null;
                }
            }
        }
        System.out.println("User not found or role does not match.");
        return null;
    }

    // Handle first-time login for patients
    private void handleFirstTimeLogin(String hospitalID, Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Enter your Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter your Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your Blood Type: ");
        String bloodType = scanner.nextLine();

        // Create and save the new patient information
        Patient newPatient = new Patient(hospitalID, name, dob, gender, contact, email, bloodType);
        PatientManager.addOrUpdatePatient(newPatient,users);
        System.out.println("Patient information saved. You are now logged in.");
    }
}
