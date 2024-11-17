package HMS.Pharmacist;

import HMS.Appointment.MedicalRecord;
import HMS.Manager.MedicalRecordManager;
import HMS.Manager.ReplenishManager;
import HMS.Staff.*;
import java.util.*;

public class Pharmacist extends Staff {
    private Map<String, Medication> inventory; // Inventory of medications by name

    // Constructor
    public Pharmacist(String hospitalID, String role, String name, String gender, String age, Map<String, Medication> inventory, String password, int loginCount) {
        super(hospitalID, role, name, gender, age, password, loginCount);
        this.inventory = inventory;
    }

    
    
    // Test Case 16
    public void viewPrescriptionOrders() {
        //Retrieve the medical records
        List<MedicalRecord> pendingPrescription = MedicalRecordManager.getAllCompletedRecords();
        
        // Display Appointment Outcomes & Prescription 
        pendingPrescription.stream().forEach(p -> {
        // "COMPLETED" status means completed appointment so display as "PENDING" for medicine prescription
        String status = p.getStatus().equals("COMPLETED") ? "PENDING" : p.getStatus();
        System.out.printf("Appointment ID: %s | Diagnosis: %s | Prescription: %s | Status: %s |\nTreatment: %s|\n",
            p.getAppointmentID(),
            p.getDiagnosis(),
            p.getPrescription(),
            status, 
            p.getTreatment());});
    }


    // Test Case 17
    // Update prescription status
    public void updatePrescriptionStatus() {
        // Get input of appointment ID 
        // Dispense medicine and update inventory
        // Mark prescription as dispensed

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Appointment ID for dispensing medicine : ");
        String appointment_ID = sc.nextLine();
        
        //List<MedicalRecord> pendingPrescription = MedicalRecordManager.getAllCompletedRecords();

        MedicalRecord record = MedicalRecordManager.findRecordByAppointmentId(appointment_ID);
        if (record.getStatus().equalsIgnoreCase("DISPENSED")){
            System.out.println("Prescription already dispensed!");
            return;
        }

        // Update the MEDICATION INVENTORY & dispense medicine
        String medicationName = record.getPrescription();
        Medication medication = inventory.get(medicationName);
        if (medication != null && medication.getStockLevel() > 0) {
            medication.dispense(); // This method reduces stock of medicine by 1
            System.out.println("Prescription for " + appointment_ID + " dispensed - " + medicationName);
            record.setStatus("DISPENSED");
        } else {
            System.out.println("Insufficient stock or medication not found.");   
        }

        MedicalRecordManager.saveMedicalRecords();
    }

    // Test Case 18
    // View current inventory
    public void viewInventory() {

        inventory.values().stream()
        .forEach(a -> System.out.println("Medication: " + a.getMedicationName() + 
        ", Stock: " + a.getStockLevel()));
    }

    // Test Case 19
    // Submit replenishment request for low-stock medications
    public void submitReplenishmentRequests(List<ReplenishmentRequest> replenishmentRequests) {
        
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Medication Name to submit Replenishment Request : ");
        String medication_name = sc.nextLine();
        String ID;
        
        // Check that duplicate ID is not sent
        Boolean exist;
        do{
            System.out.println("Enter Request ID: ");
            ID = sc.nextLine();
            exist = false;
            for (ReplenishmentRequest replenishmentRequest: replenishmentRequests)
            {
                if (replenishmentRequest.getID().equals(ID)){
                    System.out.println("ID already exists");
                    exist = true;
                    break;
                }      
            }
        }while (exist);

        // Update Info to CSV
        ReplenishmentRequest repReq = new ReplenishmentRequest(ID, medication_name, "Pending");
        ReplenishManager.addOrUpdateReplenishment(repReq);
        System.out.println("Replenishment Request submitted for "+ medication_name);
        //sc.close();
    }

    
    // Test Case 19
    public void viewReplenishmentRequests(List<ReplenishmentRequest> replenishmentRequests) {
        // Read information from ArrayList
        replenishmentRequests.stream()
        .forEach(a -> System.out.println("Request ID: " + a.getID() + 
                                        ", Medication: " + a.getMedicationName() +
                                        ", Status: "+ a.getStatus()));
    }

    
    public Map<String, Medication> getInventory() {
        return inventory;
    }
    
}