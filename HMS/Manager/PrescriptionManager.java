package HMS.Manager;


import HMS.Pharmacist.Prescription;
import java.io.*;
import java.util.*;

public class PrescriptionManager {

    private static final String CSV_FILE = "HMS/Data/Prescription_List.csv"; // Path to the CSV file
    private static List<Prescription> prescriptions = new ArrayList<>();

    // Load medicines from the CSV file
    public static void loadPrescriptions() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file does not exist at path: " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] prescriptionData = line.split(",");
                if (prescriptionData.length == 3) {
                    String appointmentID = prescriptionData[0].trim();
                    String medicationName = prescriptionData[1].trim();
                    String status = prescriptionData[2].trim();

                    Prescription prescription = new Prescription(appointmentID, medicationName, status);
                    prescriptions.add(prescription);
                    System.out.println("Loaded prescription for appointment : " + appointmentID);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    // Save the prescriptions' data back to the CSV file
    public static void savePrescriptions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            bw.write("Appointment ID,MedicationName,Status");
            bw.newLine();
            for (Prescription prescription : prescriptions) {
                    String prescriptionData = prescription.getAppointmentID() + "," +
                    prescription.getMedicationName() + "," +
                    prescription.getStatus();
                bw.write(prescriptionData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    
    // Add or update an appointment
    public static void addOrUpdatePrescription(Prescription prescription) {
        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).getAppointmentID().equals(prescription.getAppointmentID())) {
                prescriptions.set(i, prescription);  // Update existing prescription
                savePrescriptions();  // Save updated data to CSV
                return;
            }
        }

        // If appointment does not exist, add a new one
        prescriptions.add(prescription);
        savePrescriptions();  // Save new data to CSV
    }
    
    // Find prescription by ID
    public static Prescription findPrescriptionById(String appointmentID) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getAppointmentID().equals(appointmentID)) {
                return prescription;
            }
        }
        return null; // Return null if no appointment found
    }

    // Update prescription status
    public static void updatePrescriptionStatus(String appointmentID, String status) {
        Prescription prescription = findPrescriptionById(appointmentID);
        if (prescription != null) {
            prescription.setStatus(status);
            savePrescriptions(); // Save updated data to CSV
            System.out.println("Updated status of prescription of appointment" + appointmentID + " to " + status);
        } else {
            System.out.println("Prescription not found.");
        }
    }

    public static List<Prescription> getPrescriptions() {
        return prescriptions;
    }
}
