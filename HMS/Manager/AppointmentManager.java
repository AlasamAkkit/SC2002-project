package HMS.Manager;

import HMS.Appointment.Appointment;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AppointmentManager {
    private static final String CSV_FILE = "HMS/Data/Appointment_List.csv"; // Path to the CSV file
    private static List<Appointment> appointments = new ArrayList<>(); // List to store appointment data

    // Load appointment data from CSV file
    public static void loadAppointments() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file does not exist at path: " + file.getAbsolutePath());
            return;
        }

        appointments.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] appointmentData = line.split(",");
                if (appointmentData.length == 5) {
                    String appointmentID = appointmentData[0];
                    String patientID = appointmentData[1];
                    String doctorID = appointmentData[2];
                    LocalDateTime appointmentTime = LocalDateTime.parse(appointmentData[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    String status = appointmentData[4];
                    Appointment.Status s = Appointment.Status.valueOf(status);

                    // Create and add Appointment instance
                    Appointment appointment = new Appointment(appointmentID, patientID, doctorID, appointmentTime, s);
                    appointments.add(appointment);
                    
                    //System.out.println("Loaded Appointment: " + appointmentID + ", Patient: " + patientID + ", Doctor: " + doctorID);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    // Save the appointments' data back to the CSV file
    public static void saveAppointments() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            bw.write("Appointment ID,Patient ID,Doctor ID,appointmentTime,status");
            bw.newLine();
            for (Appointment appointment : appointments) {
                String appointmentData = appointment.getAppointmentID() + "," +
                        appointment.getPatientID() + "," +
                        appointment.getDoctorID() + "," +
                        appointment.getAppointmentTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "," +
                        appointment.getStatus();
                bw.write(appointmentData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    // Add or update an appointment
    public static void addOrUpdateAppointment(Appointment appointment) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentID().equals(appointment.getAppointmentID())) {
                appointments.set(i, appointment);  // Update existing appointment
                saveAppointments();  // Save updated data to CSV
                return;
            }
        }

        // If appointment does not exist, add a new one
        appointments.add(appointment);
        saveAppointments();  // Save new data to CSV
    }

    // Find appointment by ID
    public static Appointment findAppointmentById(String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        return null; // Return null if no appointment found
    }

    // List appointments by patient ID
    public static List<Appointment> findAppointmentsByPatientId(String patientID) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

    // Update appointment status
    public static void updateAppointmentStatus(String appointmentID, Appointment.Status status) {
        Appointment appointment = findAppointmentById(appointmentID);
        if (appointment != null) {
            appointment.setStatus(status);
            saveAppointments(); // Save updated data to CSV
            System.out.println("Updated status of appointment " + appointmentID + " to " + status);
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // Check if a specific time slot is available for a doctor
    public static boolean isTimeSlotAvailable(String doctorID, LocalDateTime dateTime) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorID().equals(doctorID) &&
                appointment.getAppointmentTime().equals(dateTime) &&
                appointment.getStatus().equals("Scheduled")) {
                return false; // Time slot is unavailable
            }
        }
        return true; // Time slot is available
    }

    public static List<Appointment> getAppointments() {
        return appointments;
    }
}