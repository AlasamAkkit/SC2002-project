package HMS;

import java.util.List;
import java.util.Scanner;

public class PharmacistMenu implements UserMenu {
    private Pharmacist pharmacist;
    private List<Appointment> appointments; // List of appointments for prescription orders
    private Scanner scanner;

    public PharmacistMenu(Pharmacist pharmacist, List<Appointment> appointments) {
        this.pharmacist = pharmacist;
        this.appointments = appointments;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n--- Pharmacist Menu ---");
            System.out.println("1. View Prescription Orders");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    pharmacist.viewPrescriptionOrders(appointments);
                    break;
                case 2:
                    updatePrescriptionStatus();
                    break;
                case 3:
                    pharmacist.viewInventory();
                    break;
                case 4:
                    pharmacist.submitReplenishmentRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 5);
    }

    private void updatePrescriptionStatus() {
        System.out.println("Enter medication name to update prescription status:");
        String medicationName = scanner.nextLine();
        pharmacist.updatePrescriptionStatus(medicationName);
    }
}

