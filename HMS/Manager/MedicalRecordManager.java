package HMS.Manager;

import HMS.Appointment.MedicalRecord;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages the loading, saving, and manipulation of medical records within the hospital management system.
 */
public class MedicalRecordManager {
    private static final String CSV_FILE = "HMS/Data/Medical_Records.csv"; // Path to the CSV file
    
    private static List<MedicalRecord> medicalRecords = new ArrayList<>(); // List to store medical records

    /**
     * Loads medical records from a CSV file.
     */
    public static void loadMedicalRecords() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file does not exist at path: " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] recordData = line.split(",");
                if (recordData.length == 10) {
                    String appointmentID = recordData[0];
                    String patientID = recordData[1];
                    String doctorID = recordData[2];
                    LocalDateTime appointmentTime = LocalDateTime.parse(recordData[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    String status = recordData[4];
                    String diagnosis = recordData[5];
                    String servicesProvided = recordData[6];
                    String treatment = recordData[7];
                    String prescription = recordData[8];
                    String consultationNotes = recordData.length > 9 ? recordData[9] : "NA";

                    // Create and add MedicalRecord instance
                    MedicalRecord record = new MedicalRecord(
                            appointmentID, patientID, doctorID, appointmentTime, status, 
                            diagnosis, servicesProvided, treatment, prescription, consultationNotes
                    );
                    medicalRecords.add(record);
                    System.out.println("Loaded Medical Record: " + appointmentID + " for Patient: " + patientID);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Saves all medical records back to the CSV file.
     */
    public static void saveMedicalRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            bw.write("Appointment ID,Patient ID,Doctor ID,appointmentTime,status,Diagnosis,ServicesProvided,Treatment,Prescription,ConsultationNotes");
            bw.newLine();
            for (MedicalRecord record : medicalRecords) {
                String recordData = record.getAppointmentID() + "," +
                        record.getPatientID() + "," +
                        record.getDoctorID() + "," +
                        record.getAppointmentTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "," +
                        record.getStatus() + "," +
                        record.getDiagnosis() + "," +
                        record.getServicesProvided() + "," +
                        record.getTreatment() + "," +
                        record.getPrescription() + "," +
                        record.getConsultationNotes();
                bw.write(recordData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Finds a medical record by appointment ID.
     * @param appointmentID The ID of the appointment to find the record for.
     * @return The medical record if found, null otherwise.
     */
    public static MedicalRecord findRecordByAppointmentId(String appointmentID) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getAppointmentID().equals(appointmentID)) {
                return record;
            }
        }
        return null; // Return null if no record found
    }

    /**
     * Retrieves all medical records for a specific patient by their ID.
     * @param patientID The ID of the patient.
     * @return A list of medical records for the specified patient.
     */
    public static List<MedicalRecord> findRecordsByPatientId(String patientID) {
        List<MedicalRecord> patientRecords = new ArrayList<>();
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientID().equals(patientID)) {
                patientRecords.add(record);
            }
        }
        return patientRecords;
    }

    /**
     * Adds a new medical record or updates an existing one in the list.
     * @param record The medical record to add or update.
     */
    public static void addOrUpdateRecord(MedicalRecord record) {
        for (int i = 0; i < medicalRecords.size(); i++) {
            if (medicalRecords.get(i).getAppointmentID().equals(record.getAppointmentID())) {
                medicalRecords.set(i, record);  // Update existing record
                saveMedicalRecords();  // Save updated data to CSV
                return;
            }
        }

        // If record does not exist, add a new one
        medicalRecords.add(record);
        saveMedicalRecords();  // Save new data to CSV
    }

    /**
     * Retrieves a list of all completed medical records.
     * @return A list of completed medical records.
     */
    public static List<MedicalRecord> getAllCompletedRecords() {
        List<MedicalRecord> completedRecords = new ArrayList<>();
        for (MedicalRecord record : medicalRecords) {
            if ("COMPLETED".equalsIgnoreCase(record.getStatus()) || "DISPENSED".equalsIgnoreCase(record.getStatus())) {
                completedRecords.add(record);
            }
        }
        return completedRecords;
    }
}

