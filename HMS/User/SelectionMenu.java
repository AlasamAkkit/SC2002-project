package HMS.User;

import java.util.List;
import java.util.Scanner;

import HMS.Patient.*;

public class SelectionMenu {
        public static User display(List<User> users, List<Patient> patients) {
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null; // To store the logged-in user

        while (loggedInUser == null){
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
        }

        return loggedInUser;
    }
}
