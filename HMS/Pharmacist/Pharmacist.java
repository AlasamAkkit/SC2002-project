package HMS.Pharmacist;

import java.util.List;
import java.util.Map;
import HMS.Appointment.*;

import HMS.Staff.*;

public class Pharmacist extends Staff {
    private Map<String, Medication> inventory; // Inventory of medications by name

    // Constructor
    public Pharmacist(String hospitalID, String role, String name, String gender, String age, Map<String, Medication> inventory, String password, int loginCount) {
        super(hospitalID, role, name, gender, age, password, loginCount);
        this.inventory = inventory;
    }

    // View prescription orders (from completed appointments with pending prescriptions)
    public void viewPrescriptionOrders(List<Appointment> appointments) {
        appointments.stream()
            .filter(a -> a.getStatus().equals("Completed") && a.hasPendingPrescriptions())
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() + 
                                             ", Prescription: " + a.getPrescriptionDetails()));
    }

    // Update prescription status
    public void updatePrescriptionStatus(String medicationName) {
        Medication medication = inventory.get(medicationName);
        if (medication != null && medication.getStockLevel() > 0) {
            medication.dispense(); // Assume this method reduces stock and marks as dispensed
            System.out.println("Prescription for " + medicationName + " dispensed.");
        } else {
            System.out.println("Insufficient stock or medication not found.");
        }
    }

    // View current inventory
    public void viewInventory() {
        System.out.println("Medication Inventory:");
        inventory.values().forEach(med -> System.out.println(med.getMedicationName() + 
                                                             ", Stock Level: " + med.getStockLevel()));
    }

    // Submit replenishment request for low-stock medications
    public void submitReplenishmentRequests() {
        inventory.values().stream()
            .filter(Medication::isBelowThreshold)
            .forEach(med -> System.out.println("Replenishment request submitted for " + med.getMedicationName()));
    }

    public Map<String, Medication> getInventory() {
        return inventory;
    }
}