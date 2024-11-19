package HMS.Pharmacist;

import HMS.Appointment.MedicalRecord;
import HMS.Manager.MedicalRecordManager;
import HMS.Manager.ReplenishManager;
import HMS.Staff.*;
import java.util.*;

/**
 * Represents a pharmacist, a staff member responsible for managing medications and prescriptions.
 */
public class Pharmacist extends Staff {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Medication> inventory; // Inventory of medications by name

    /**
     * Constructs a new Pharmacist object.
     *
     * @param hospitalID Unique identifier for the hospital staff.
     * @param role The role of the staff member, should be "Pharmacist".
     * @param name The name of the pharmacist.
     * @param gender The gender of the pharmacist.
     * @param age The age of the pharmacist.
     * @param inventory A map of medications available to the pharmacist.
     * @param password The login password for the pharmacist.
     * @param loginCount The count of how many times the pharmacist has logged in.
     */
    public Pharmacist(String hospitalID, String role, String name, String gender, String age, Map<String, Medication> inventory, String password, int loginCount) {
        super(hospitalID, role, name, gender, age, password, loginCount);
        this.inventory = inventory;
    }

    /**
     * Views the list of pending prescription orders.
     */
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

    /**
     * Updates the status of a prescription to "DISPENSED" if the medication is available.
     */
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
        while (record == null)
        {
            System.out.println("Appointment ID does not exist!");
            System.out.println("Enter Appointment ID for dispensing medicine : ");
            appointment_ID = sc.nextLine();
            record = MedicalRecordManager.findRecordByAppointmentId(appointment_ID);
        }


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

    /**
     * Displays the current inventory of medications.
     */
    public void viewInventory() {

        inventory.values().stream()
        .forEach(a -> System.out.println("Medication: " + a.getMedicationName() + 
        ", Stock: " + a.getStockLevel()));
    }

    /**
     * Submits a replenishment request for medications that are low in stock.
     *
     * @param replenishmentRequests A list of current replenishment requests.
     */
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

    /**
     * Displays all current replenishment requests.
     *
     * @param replenishmentRequests A list of replenishment requests to display.
     */
    public void viewReplenishmentRequests(List<ReplenishmentRequest> replenishmentRequests) {
        // Read information from ArrayList
        replenishmentRequests.stream()
        .forEach(a -> System.out.println("Request ID: " + a.getID() + 
                                        ", Medication: " + a.getMedicationName() +
                                        ", Status: "+ a.getStatus()));
    }

    /**
     * Returns the inventory of medications.
     *
     * @return A map of medication names to Medication objects.
     */
    public Map<String, Medication> getInventory() {
        return inventory;
    }
    
}