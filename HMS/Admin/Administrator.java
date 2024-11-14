package HMS.Admin;

import java.util.List;
import java.util.Map;
import HMS.Manager.*;
import HMS.Appointment.*;
import HMS.Pharmacist.*;
import HMS.Staff.*;
import HMS.User.User;

public class Administrator extends Staff {
    private List<Staff> staff;
    private List<Appointment> appointments;
    private Map<String, Medication> inventory;

    public Administrator(String hospitalID, String role, String name, String gender, String age, List<Staff> staff, List<Appointment> appointments, Map<String, Medication> inventory, String password, int loginCount) {
        super(hospitalID, role, name, gender, age, password, loginCount);
        this.staff = staff;
        this.appointments = appointments;
        this.inventory = inventory;
    }

    public List<Staff> getStaff() {
        return staff; // Returns the list of staff
    }

    // Staff Management Methods
    public void addStaff(Staff user) {
        staff.add(user);
        System.out.println("Staff member added: " + user.getHospitalID());
    }

    public void removeStaff(String hospitalID) {
        staff.removeIf(user -> user.getHospitalID().equals(hospitalID));

        // Also remove from the main staff list in StaffManager
        List<Staff> mainStaffList = StaffManager.getStaffList();
        mainStaffList.removeIf(user -> user.getHospitalID().equals(hospitalID));

        System.out.println("Staff member removed: " + hospitalID);
    }

    public void updateStaffRole(String hospitalID, String newRole) {
        for (User user : staff) {
            if (user.getHospitalID().equals(hospitalID)) {
                user.setRole(newRole);
                System.out.println("Staff member's role updated: " + hospitalID);
                return;
            }
        }
        System.out.println("Staff member not found.");
    }

    public void listStaffByRole(String role) {
        System.out.println("Listing staff with role: " + role);
        staff.stream()
            .filter(user -> user.getRole().equals(role))
            .forEach(user -> System.out.println(user.getHospitalID() + " - " + user.getRole() + " - " + user.getName()));
    }

    // Appointment Management Methods
    public void viewAppointments() {
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments to display.");
        } else {
            System.out.println("Appointments:");
            appointments.forEach(System.out::println); // Print each appointment
        }
    }

    // Inventory Management Methods
    public void updateMedicationStock(String medicationName, int newStockLevel) {
        Medication medication = inventory.get(medicationName);
        if (medication != null) {
            medication.setStockLevel(newStockLevel);
            System.out.println("Updated stock for " + medicationName);
        } else {
            System.out.println("Medication not found.");
        }
    }

    public void updateLowStockThreshold(String medicationName, int newThreshold) {
        Medication medication = inventory.get(medicationName);
        if (medication != null) {
            medication.setLowStockThreshold(newThreshold);
            System.out.println("Updated low stock threshold for " + medicationName);
        } else {
            System.out.println("Medication not found.");
        }
    }

    public void approveReplenishmentRequest(String medicationName, int quantity) {
        Medication medication = inventory.get(medicationName);
        if (medication != null) {
            medication.replenishStock(quantity);
            System.out.println("Replenishment approved for " + medicationName);
        } else {
            System.out.println("Medication not found.");
        }
    }
}