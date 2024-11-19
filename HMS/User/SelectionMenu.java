package HMS.User;

import HMS.Patient.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Provides a user interface for logging in or creating a new patient account.
 */
public class SelectionMenu {

    /**
     * Displays a menu for the user to either log in or create a new patient account.
     * 
     * @param users    A list of all users in the system, both staff and patients.
     * @param patients A list of all patients in the system.
     * @return The User object corresponding to the logged-in user, or null if the
     *         login was not successful or if the user creates a new patient account.
     */    
    public static User display(List<User> users, List<Patient> patients) {
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null; // To store the logged-in user

        while (loggedInUser == null){
            try {
            System.out.println("Welcome to the HMS System!");
            System.out.println("1. Login");
            System.out.println("2. Create New Patient Account");
            System.out.print("Enter your choice: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    loggedInUser = new LoginHandler(users).login(scanner); // Attempt login
                    break;
                case 2:
                    PatientCreator.createPatientAccount(users, patients); // Create a new patient account
                    break;
                default:
                    System.out.println("Invalid choice. Please restart the application.");
                    break;
            }
        }catch (InputMismatchException ime) {
                System.out.println("Invalid choice. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        }

        return loggedInUser;
    }
}
