package HMS.User;

import HMS.Manager.*;
import HMS.Patient.*;
import HMS.Staff.Staff;
import java.util.List;
import java.util.Scanner;

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

            if (user.getLoginCount() == 0) {
                handleChangePassword(hospitalID, scanner);
            }

            if (user instanceof Patient) {
                // Handle login count and password change for patients
                Patient patient = (Patient) user;
                patient.setLoginCount(patient.getLoginCount() + 1);  // Increment loginCount for patients
                PatientManager.savePatients();  // Save updated data to CSV
            } else if (user instanceof Staff) {
                // Handle login count and password change for staff members
                Staff staff = (Staff) user;
                staff.setLoginCount(staff.getLoginCount() + 1);  // Increment loginCount for staff
                StaffManager.saveStaff();  // Save updated data for staff in the system
            }
            
            // Handle first-time login if the user is a patient
            if (role.equalsIgnoreCase("Patient")) {
                if (PatientManager.isFirstTimeLogin(hospitalID)) {
                    handleFirstTimeLogin(hospitalID, scanner);
                } else {
                    System.out.println("Welcome back, " + hospitalID + "!");
                }
            } else {
                System.out.println("Welcome back!" + role);
            }
        } else {
            System.out.println("Invalid login. Please try again.\n");
        }
        return user;
    }

    // Method to authenticate user based on ID, role, and password
    private User authenticate(String hospitalID, String role, String password) {
        for (User user : users) {
            if (user.getHospitalID().equalsIgnoreCase(hospitalID) && user.getRole().equalsIgnoreCase(role)) {
                if (user.login(password)) {
                    return user; // Return the correct user instance
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
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Create and save the new patient information
        Patient newPatient = new Patient(hospitalID, name, dob, gender, contact, email, bloodType, password, 0);
        PatientManager.addOrUpdatePatient(newPatient,users);
        System.out.println("Patient information saved.");
    }

    private void handleChangePassword(String hospitalID, Scanner scanner) {
        System.out.println("It is your first time logging in. Please change your password.");
        System.out.print("Enter a new password: ");
        String newPassword = scanner.nextLine();

        // Find the user and update their password
        for (User user : users) {
            if (user.getHospitalID().equalsIgnoreCase(hospitalID)) {
                user.setPassword(newPassword); // Assuming `setPassword` method exists in User class
                System.out.println("Password updated successfully.");

                if (user instanceof Patient) {
                    Patient patient = (Patient) user;
                    patient.setPassword(newPassword);  // Update password
                    PatientManager.savePatients();     // Save changes to CSV
                } else if (user instanceof Staff) {
                    Staff staff = (Staff) user;
                    staff.setPassword(newPassword);  // Update password
                    StaffManager.saveStaff();        // Save changes to CSV
                }
                break;
            }
        }
    }
}
