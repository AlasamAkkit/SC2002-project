package HMS.Pharmacist;

import HMS.Manager.PrescriptionManager;
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
    public void viewPrescriptionOrders(List<Prescription> prescriptions) {
        prescriptions.stream()
            //.filter(a -> a.getStatus().equals("Pending"))
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() + 
                                            ", Prescription: " + a.getMedicationName() +
                                            ", Status: "+ a.getStatus()));
    }


    // Test Case 17
    // Update prescription status
    public void updatePrescriptionStatus(List<Prescription> prescriptions) {
        // Get input of appointment ID 
        // Dispense medicine and update inventory
        // Mark prescription as dispensed

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Appointment ID for dispensing medicine : ");
        String appointment_ID = sc.nextLine();
        Prescription tempP = null;
        
        for (Prescription prescription : prescriptions){
            //System.out.println(appointment.getAppointmentID()); //debug
            if (prescription.getAppointmentID().equals(appointment_ID)){
                tempP = prescription;
            }
            }
        if (tempP == null)
        {
            System.out.println("Prescription not found");
            //sc.close();
            return;
        }

        String medicationName = tempP.getMedicationName();
        System.out.println(medicationName);
        Medication medication = inventory.get(medicationName);
        if (medication != null && medication.getStockLevel() > 0) {
            medication.dispense(); // This method reduces stock of medicine by 1
            System.out.println("Prescription for " + appointment_ID + " dispensed - " + medicationName);
            for (Prescription prescription : prescriptions){
                if (prescription.getAppointmentID().equals(appointment_ID)){
                    prescription.setStatus("Dispensed");
                }
                }
        } else {
            System.out.println("Insufficient stock or medication not found.");
            //sc.close();
            return;
        }
        PrescriptionManager.addOrUpdatePrescription(tempP);
        //sc.close();
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
        Boolean exist = false;
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