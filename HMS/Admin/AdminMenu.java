package HMS.Admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import HMS.Manager.*;
import HMS.Appointment.*;
import HMS.Doctor.*;
import HMS.Pharmacist.*;
import HMS.Staff.*;

public class AdminMenu implements StaffMenu {
    private Administrator admin;
    private List<Appointment> appointments;
    private Scanner scanner;

    public AdminMenu(Administrator admin, List<Appointment> appointments) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
        this.appointments = appointments;
    }

    public void displayMenu() {
        int choice;
        do {
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
        } while (choice != 10);
    }

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

    private void removeStaffMember() {
        System.out.println("Enter staff ID to remove:");
        String staffID = scanner.nextLine();
        admin.removeStaff(staffID);
        StaffManager.saveStaff();
    }

    private void updateStaffRole() {
        System.out.println("Enter staff ID to update:");
        String staffID = scanner.nextLine();

        Staff staffToUpdate = null;
        for (Staff staff : admin.getStaff()) {
            if (staff.getHospitalID().equals(staffID)) {
                staffToUpdate = staff;
                break;
            }
        }

        // If staff member is not found
        if (staffToUpdate == null) {
            System.out.println("Staff member not found.");
            return;
        }

        // Update staff details
        System.out.println("Enter new name (current: " + staffToUpdate.getName() + "):");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            staffToUpdate.setName(newName);
        }

        System.out.println("Enter new role (current: " + staffToUpdate.getRole() + "):");
        String newRole = scanner.nextLine();
        if (!newRole.isEmpty()) {
            staffToUpdate.setRole(newRole);
        }

        System.out.println("Enter new gender (current: " + staffToUpdate.getGender() + "):");
        String newGender = scanner.nextLine();
        if (!newGender.isEmpty()) {
            staffToUpdate.setGender(newGender);
        }

        System.out.println("Enter new age (current: " + staffToUpdate.getAge() + "):");
        String newAge = scanner.nextLine();
        if (!newAge.isEmpty()) {
            staffToUpdate.setAge(newAge);
        }

        // Save the updated staff list to the CSV file
        StaffManager.saveStaff();
        System.out.println("Staff member updated successfully.");
    }

    private void listStaffByRole() {
        System.out.println("Enter role to filter (Doctor, Pharmacist):");
        String role = scanner.nextLine();
        admin.listStaffByRole(role);
    }

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
                System.out.println("-------------------------");
            }
        }
    }
    
    private void displayMedications() {
        System.out.println("\n--- Medications List ---");
        // Iterate over staff members to find pharmacists
        for (Staff staff : admin.getStaff()) {
            if (staff instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) staff;
                Map<String, Medication> inventory = pharmacist.getInventory();

                if (inventory.isEmpty()) {
                    System.out.println("No medications available in the inventory.");
                } else {
                    for (Map.Entry<String, Medication> entry : inventory.entrySet()) {
                        String medicationName = entry.getKey();
                        Medication medication = entry.getValue();
                        System.out.println("Medication Name: " + medicationName);
                        System.out.println("Stock Level: " + medication.getStockLevel());
                        System.out.println("-------------------------");
                    }
                }
            }
        }
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