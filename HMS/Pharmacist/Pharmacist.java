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

    // View prescription orders (from completed appointments with pending prescriptions)
    /* 
    public void viewPrescriptionOrders() {
        List<Appointment> appointments = AppointmentManager.getAppointments();
        appointments.stream()
            .filter(a -> a.getStatus().equals("Completed") && a.hasPendingPrescriptions())
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() + 
                                            ", Prescription: " + a.getPrescriptionDetails()));
    } */

     
    public void viewPrescriptionOrders(List<Prescription> prescriptions) {
        //List<Prescription> prescriptions = PrescriptionManager.getPrescriptions();
        prescriptions.stream()
            //.filter(a -> a.getStatus().equals("Pending"))
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() + 
                                            ", Prescription: " + a.getMedicationName() +
                                            ", Status: "+ a.getStatus()));
    }


    // Update prescription status
    public void updatePrescriptionStatus(List<Prescription> prescriptions) {
        // Get input of appointment ID 
        // Dispense medicine and update inventory
        // Mark prescription as dispensed

        // get list of all appointments/prescriptions
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
            return;
        }
        PrescriptionManager.addOrUpdatePrescription(tempP);

    }


    // View current inventory
    public void viewInventory() {
        //System.out.println("Medication Inventory:");
        //inventory.values().forEach(med -> System.out.println(med.getMedicationName() + 
        //                                                     ", Stock Level: " + med.getStockLevel()));
        inventory.values().stream()
        .forEach(a -> System.out.println("Medication: " + a.getMedicationName() + 
        ", Stock: " + a.getStockLevel()));
    }

    // Submit replenishment request for low-stock medications
    public void submitReplenishmentRequests(List<ReplenishmentRequest> replenishmentRequests) {
        //inventory.values().stream()
        //    .filter(Medication::isBelowThreshold)
        //   .forEach(med -> System.out.println("Replenishment request submitted for " + med.getMedicationName()));
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Medication Name to submit Replenishment Request : ");
        String medication_name = sc.nextLine();
        System.out.println("Enter Request ID: ");
        String ID = sc.nextLine();

        ReplenishmentRequest repReq = new ReplenishmentRequest(ID, medication_name, "Pending");
        
        ReplenishManager.addOrUpdateReplenishment(repReq);
        System.out.println("Replenishment Request submitted for "+ medication_name);

    }

    public void viewReplenishmentRequests(List<ReplenishmentRequest> replenishmentRequests) {
        replenishmentRequests.stream()
        
        .forEach(a -> System.out.println("Request ID: " + a.getID() + 
                                        ", Medication: " + a.getMedicationName() +
                                        ", Status: "+ a.getStatus()));

    }




    public Map<String, Medication> getInventory() {
        return inventory;
    }
}