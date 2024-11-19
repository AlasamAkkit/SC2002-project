package HMS.Pharmacist;

import HMS.Staff.StaffMenu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * A menu for pharmacists to interact with and perform various tasks related to pharmacy management.
 */
public class PharmacistMenu implements StaffMenu {
    @SuppressWarnings("FieldMayBeFinal")
    private Pharmacist pharmacist;
    List<ReplenishmentRequest> replenishmentRequests;
    @SuppressWarnings("FieldMayBeFinal")
    private Scanner scanner;

    /**
     * Constructs a new PharmacistMenu.
     *
     * @param pharmacist The pharmacist who is using this menu.
     * @param replenishmentRequests A list of pending replenishment requests that can be managed through this menu.
     */
    public PharmacistMenu(Pharmacist pharmacist, List<ReplenishmentRequest> replenishmentRequests) {
        this.pharmacist = pharmacist;
        this.replenishmentRequests = replenishmentRequests;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the interactive menu for the pharmacist, handling user input to navigate different functionalities.
     */
    @Override
    public void displayMenu() {
        int choice;
        do {
            try {
            System.out.println("\n--- Pharmacist Menu ---");
            System.out.println("1. View Prescription Orders");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. View Replenishment Requests");
            System.out.println("5. Submit Replenishment Requests");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    pharmacist.viewPrescriptionOrders();
                    break;
                case 2:
                    pharmacist.updatePrescriptionStatus();
                    break;
                case 3:
                    pharmacist.viewInventory();
                    break;
                case 4:
                    pharmacist.viewReplenishmentRequests(replenishmentRequests);
                    break;
                case 5:
                    pharmacist.submitReplenishmentRequests(replenishmentRequests);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        catch (InputMismatchException ime) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
        }
        } while (choice != 6);
    }
}

