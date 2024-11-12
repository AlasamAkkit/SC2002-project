package HMS;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdminMenu implements UserMenu {
    private Administrator admin;
    private Scanner scanner;

    public AdminMenu(Administrator admin) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Staff Member");
            System.out.println("2. Remove Staff Member");
            System.out.println("3. Update Staff Role");
            System.out.println("4. List Staff by Role");
            System.out.println("5. View Appointments");
            System.out.println("6. Update Medication Stock");
            System.out.println("7. Update Low Stock Threshold");
            System.out.println("8. Approve Replenishment Request");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStaffMember();
                    break;
                case 2:
                    removeStaffMember();
                    break;
                case 3:
                    updateStaffRole();
                    break;
                case 4:
                    listStaffByRole();
                    break;
                case 5:
                    admin.viewAppointments();
                    break;
                case 6:
                    updateMedicationStock();
                    break;
                case 7:
                    updateLowStockThreshold();
                    break;
                case 8:
                    approveReplenishmentRequest();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 9);
    }

    private void addStaffMember() {
        System.out.println("Enter staff ID:");
        String staffID = scanner.nextLine();
        System.out.println("Enter role (Doctor, Pharmacist):");
        String role = scanner.nextLine();
        if (role.equalsIgnoreCase("Doctor")) {
            User newUser = new Doctor(staffID, role);
            admin.addStaff(newUser);
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            // Pass an empty or initialized inventory map as needed
            Map<String, Medication> inventory = new HashMap<>();
            User newUser = new Pharmacist(staffID, role, inventory);
            admin.addStaff(newUser);
        }
    }


    private void removeStaffMember() {
        System.out.println("Enter staff ID to remove:");
        String staffID = scanner.nextLine();
        admin.removeStaff(staffID);
    }

    private void updateStaffRole() {
        System.out.println("Enter staff ID:");
        String staffID = scanner.nextLine();
        System.out.println("Enter new role:");
        String newRole = scanner.nextLine();
        admin.updateStaffRole(staffID, newRole);
    }

    private void listStaffByRole() {
        System.out.println("Enter role to filter (Doctor, Pharmacist):");
        String role = scanner.nextLine();
        admin.listStaffByRole(role);
    }

    private void updateMedicationStock() {
        System.out.println("Enter medication name:");
        String medicationName = scanner.nextLine();
        System.out.println("Enter new stock level:");
        int newStockLevel = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        admin.updateMedicationStock(medicationName, newStockLevel);
    }

    private void updateLowStockThreshold() {
        System.out.println("Enter medication name:");
        String medicationName = scanner.nextLine();
        System.out.println("Enter new low stock threshold:");
        int newThreshold = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        admin.updateLowStockThreshold(medicationName, newThreshold);
    }

    private void approveReplenishmentRequest() {
        System.out.println("Enter medication name:");
        String medicationName = scanner.nextLine();
        System.out.println("Enter quantity to replenish:");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        admin.approveReplenishmentRequest(medicationName, quantity);
    }
}

