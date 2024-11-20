package HMS.Admin;

import HMS.Appointment.*;
import HMS.Manager.*;
import HMS.Pharmacist.*;
import HMS.Staff.*;
import HMS.User.User;
import java.util.List;
import java.util.Map;

/**
 * Represents an administrator with management capabilities over staff, appointments, and inventory.
 */
public class Administrator extends Staff {
    @SuppressWarnings("FieldMayBeFinal")
    private List<Staff> staff;
    @SuppressWarnings("FieldMayBeFinal")
    private List<Appointment> appointments;
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Medication> inventory;

    /**
     * Constructs an Administrator with specified details and management capabilities.
     *
     * @param hospitalID   the hospital ID of the administrator
     * @param role         the role of the administrator within the hospital
     * @param name         the name of the administrator
     * @param gender       the gender of the administrator
     * @param age          the age of the administrator
     * @param staff        the list of staff members to manage
     * @param appointments the list of appointments to oversee
     * @param inventory    the inventory of medications to manage
     * @param password     the password for system access
     * @param loginCount   the count of logins for the administrator
     */
    public Administrator(String hospitalID, String role, String name, String gender, String age, List<Staff> staff, List<Appointment> appointments, Map<String, Medication> inventory, String password, int loginCount) {
        super(hospitalID, role, name, gender, age, password, loginCount);
        this.staff = staff;
        this.appointments = appointments;
        this.inventory = inventory;
    }

    /**
     * Returns the list of staff managed by the administrator.
     *
     * @return the list of staff
     */
    public List<Staff> getStaff() {
        return staff;
    }

    /**
     * Adds a new staff member to the management list.
     *
     * @param user the staff member to be added
     */
    public void addStaff(Staff user) {
        staff.add(user);
        System.out.println("Staff member added: " + user.getHospitalID());
    }

    /**
     * Removes a staff member from the management list based on their hospital ID.
     *
     * @param hospitalID the hospital ID of the staff member to remove
     */
    public void removeStaff(String hospitalID) {
        staff.removeIf(user -> user.getHospitalID().equals(hospitalID));

        // Also remove from the main staff list in StaffManager
        List<Staff> mainStaffList = StaffManager.getStaffList();
        mainStaffList.removeIf(user -> user.getHospitalID().equals(hospitalID));

        System.out.println("Staff member removed: " + hospitalID);
    }

    /**
     * Updates the role of a staff member identified by their hospital ID.
     *
     * @param hospitalID the hospital ID of the staff member whose role is to be updated
     * @param newRole    the new role to assign to the staff member
     */
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

    /**
     * Lists all staff members with a specific role.
     *
     * @param role the role to filter the staff members by
     */
    public void listStaffByRole(String role) {
        System.out.println("Listing staff with role: " + role);
        staff.stream()
            .filter(user -> user.getRole().equals(role))
            .forEach(user -> System.out.println(user.getHospitalID() + " - " + user.getRole() + " - " + user.getName()));
    }

    /**
     * Displays all managed appointments.
     */
    public void viewAppointments() {
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments to display.");
        } else {
            System.out.println("Appointments:");
            appointments.forEach(System.out::println); // Print each appointment
        }
    }

    /**
     * Updates the stock level of a specific medication.
     *
     * @param medicationName the name of the medication to update
     * @param newStockLevel  the new stock level for the medication
     */
    public void updateMedicationStock(String medicationName, int newStockLevel) {
        Medication medication = inventory.get(medicationName);
        if (medication != null) {
            medication.setStockLevel(newStockLevel);
            System.out.println("Updated stock for " + medicationName);
        } else {
            System.out.println("Medication not found.");
        }
    }

    /**
     * Updates the low stock threshold for a specific medication.
     *
     * @param medicationName the name of the medication
     * @param newThreshold   the new low stock threshold
     */
    public void updateLowStockThreshold(String medicationName, int newThreshold) {
        Medication medication = inventory.get(medicationName);
        if (medication != null) {
            medication.setLowStockThreshold(newThreshold);
            System.out.println("Updated low stock threshold for " + medicationName);
        } else {
            System.out.println("Medication not found.");
        }
    }

    /**
     * Approves a replenishment request for a specific medication.
     *
     * @param medicationName the name of the medication
     * @param quantity       the quantity to replenish
     */
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

