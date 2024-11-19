package HMS.Admin;

import HMS.Appointment.*;
import HMS.Doctor.*;
import HMS.Manager.*;
import HMS.Pharmacist.*;
import HMS.Staff.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * AdminMenu provides an interactive menu for administrators to manage staff, appointments, and inventory.
 * It handles user input through a console interface, offering a variety of administrative functions.
 */
public class AdminMenu implements StaffMenu {
    private Administrator admin;
    private List<Appointment> appointments;
    private Scanner scanner;

    /**
     * Constructs an AdminMenu with the specified administrator and list of appointments.
     *
     * @param admin the administrator interacting with this menu
     * @param appointments the list of appointments to be managed
     */
    public AdminMenu(Administrator admin, List<Appointment> appointments) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
        this.appointments = appointments;
    }

    /**
     * Displays the administrative menu and handles user input to perform various administrative tasks.
     */
    public void displayMenu() {
        int choice = 0;
        do {
            try{
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Staff Member");
            System.out.println("2. Remove Staff Member");
            System.out.println("3. Update Staff Member");
            System.out.println("4. List Staff by Role");
            System.out.println("5. View Appointments");
            System.out.println("6. Update Medication Stock");
            System.out.println("7. Update Low Stock Threshold");
            System.out.println("8. Approve Replenishment Request");
            System.out.println("9. Display Medication Status");
            System.out.println("10. Logout");
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
                    displayAppointments();
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
                    displayMedications();
                    break;
                case 10:
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

        } while (choice != 10);
    }

    /**
     * Adds a new staff member to the system by collecting necessary information
     * through user input. The staff member is created based on their role, which can
     * be either 'Doctor' or 'Pharmacist'.
     */
    private void addStaffMember() {
        System.out.println("Enter staff ID:");
        String staffID = scanner.nextLine();
        System.out.println("Enter role (Doctor, Pharmacist):");
        String role = scanner.nextLine();
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter gender:");
        String gender = scanner.nextLine();
        System.out.println("Enter age:");
        String age = scanner.nextLine();

        Staff newUser = null;

        if (role.equalsIgnoreCase("Doctor")) {
            newUser = new Doctor(staffID, name, role, gender, age, "password", 0) ;
        } else if (role.equalsIgnoreCase("Pharmacist")) {
            Map<String, Medication> inventory = new HashMap<>();
            newUser = new Pharmacist(staffID, name, role, gender, age, inventory, "password", 0);
        }

        if (newUser != null) {
            admin.addStaff(newUser);
            // Save updated staff list to CSV
            StaffManager.saveStaff();  
        }
    }

    /**
     * Removes a staff member from the system based on the provided staff ID.
     */
    private void removeStaffMember() {
        System.out.println("Enter staff ID to remove:");
        String staffID = scanner.nextLine();
        admin.removeStaff(staffID);
        StaffManager.saveStaff();
    }

    /**
     * Updates the role of a staff member. The new role details are collected through user input.
     */
    private void updateStaffRole() {
        System.out.println("Enter staff ID to update:");
        String staffID = scanner.nextLine();

        Staff updatedStaff = null;
        for (Staff staff : admin.getStaff()) {
            if (staff.getHospitalID().equals(staffID)) {
                updatedStaff = staff;
                break;
            }
        }

        // Update staff details
        updatedStaff.setName(updatePrompt("name", updatedStaff.getName()));
        updatedStaff.setGender(updatePrompt("gender", updatedStaff.getGender()));
        updatedStaff.setAge(updatePrompt("age", updatedStaff.getAge()));

        // Save changes
        StaffManager.saveStaff();
        System.out.println("Staff member updated successfully.");
    }

    private String updatePrompt(String field, String currentValue) {
        System.out.println("Enter new " + field + " (current: " + currentValue + "):");
        String newValue = scanner.nextLine();
        return newValue.isEmpty() ? currentValue : newValue;
    }

    /**
     * Displays a list of staff by a specified role.
     */
    private void listStaffByRole() {
        System.out.println("Enter role to filter (Doctor, Pharmacist):");
        String role = scanner.nextLine();
        admin.listStaffByRole(role);
    }

    /**
     * Displays all scheduled appointments in the system.
     */
    private void displayAppointments() {
        System.out.println("\n--- Appointments List ---");
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            for (Appointment appointment : appointments) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID());
                System.out.println("Patient ID: " + appointment.getPatientID());
                System.out.println("Doctor ID: " + appointment.getDoctorID());
                System.out.println("Date and Time: " + appointment.getAppointmentDateTime());
                System.out.println("Status: " + appointment.getStatus());
                System.out.println("-------------------------");
            }
        }
    }
    
    /**
     * Displays all medications in the inventory along with their current stock levels.
     */
    private void displayMedications() {
        System.out.println("\n--- Medications List ---");

        Map<String, Medication> inventory =  MedicineManager.getInventory();
    
        if (inventory.isEmpty()) {
            System.out.println("No medications available in the inventory.");
        } else {
            for (Map.Entry<String, Medication> entry : inventory.entrySet()) {
                Medication medication = entry.getValue();
                System.out.println("Medication Name: " + entry.getKey());
                System.out.println("Stock Level: " + medication.getStockLevel());
                System.out.println("-------------------------");
            }
        }
    }

    /**
     * Updates the stock level of a specific medication.
     */
    private void updateMedicationStock() {
        System.out.println("Enter medication name:");
        String medicationName = scanner.nextLine();
        System.out.println("Enter new stock level:");
        int newStockLevel = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        MedicineManager.updateMedicationStock(medicationName, newStockLevel);
    }

    /**
     * Updates the low stock threshold for a specific medication.
     */
    private void updateLowStockThreshold() {
        System.out.println("Enter medication name:");
        String medicationName = scanner.nextLine();
        System.out.println("Enter new low stock threshold:");
        int newThreshold = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        MedicineManager.updateLowStockThreshold(medicationName, newThreshold);
    }

    /**
     * Handles the approval or rejection of a medication replenishment request.
     */
    private void approveReplenishmentRequest() {
        System.out.println("Enter replenishment request ID:");
        String requestID = scanner.nextLine();
        
        // Find replenishment request
        ReplenishmentRequest request = ReplenishManager.findRequestById(requestID); 
    
        System.out.println("Approve or Reject Request:");
        String status = scanner.nextLine();
        
        // Update replenishment request status
        ReplenishManager.updateReplenishmentStatus(requestID, request.getMedicationName(), status);
        
        if ("Approve".equalsIgnoreCase(status)) {
            System.out.println("Enter quantity to replenish:");
            int replenishQuantity = scanner.nextInt();
            scanner.nextLine(); 

            if (ReplenishManager.replenishMedicationStock(request.getMedicationName(), replenishQuantity, admin.getStaff())) {
                System.out.println("Replenished " + replenishQuantity + " units of " + request.getMedicationName());
            } else {
                System.out.println("Medication not found or inventory issue.");
            }
        } else {
            System.out.println("Request rejected.");
        }
    }
}
