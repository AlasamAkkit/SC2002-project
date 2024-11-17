package HMS.Manager;

import HMS.Appointment.Appointment;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages the loading, saving, and manipulation of appointments within the hospital management system.
 */
public class AppointmentManager {
    private static final String CSV_FILE = "HMS/Data/Appointment_List.csv"; // Path to the CSV file
    private static List<Appointment> appointments = new ArrayList<>(); // List to store appointment data

    /**
     * Loads appointment data from a CSV file.
     */
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

    /**
     * Saves all appointments back to the CSV file.
     */
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

    /**
     * Adds a new appointment or updates an existing one in the list.
     * @param appointment The appointment to add or update.
     */
    public static void addOrUpdateAppointment(Appointment appointment) {
        boolean found = false;
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentID().equals(appointment.getAppointmentID())) {
                appointments.set(i, appointment); // Update existing appointment
                found = true;
                break;
            }
        }
        if (!found) {
            appointments.add(appointment); // Add new appointment if not found
        }
        saveAppointments(); // Save changes to the CSV file
    }

    /**
     * Generates a new, unique appointment ID.
     * @return A string representing the new appointment ID.
     */
    public static String generateNextAppointmentId() {
        int maxId = 0;
        for (Appointment appointment : appointments) {
            String id = appointment.getAppointmentID().substring(1); // Skip the 'A'
            try {
                int num = Integer.parseInt(id);
                if (num > maxId) {
                    maxId = num;
                }
            } catch (NumberFormatException e) {
                System.err.println("Error parsing appointment ID: " + id);
            }
        }
        return "A" + String.format("%04d", maxId + 1);
    }

    /**
     * Finds an appointment by its ID.
     * @param appointmentID The ID of the appointment to find.
     * @return The appointment if found, null otherwise.
     */
    public static Appointment findAppointmentById(String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        return null; // Return null if no appointment found
    }

    /**
     * Retrieves all appointments for a specific patient by their ID.
     * @param patientID The ID of the patient.
     * @return A list of appointments for the specified patient.
     */
    public static List<Appointment> findAppointmentsByPatientId(String patientID) {
        List<Appointment> patientAppointments = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                patientAppointments.add(appointment);
            }
        }
        return patientAppointments;
    }

    /**
     * Updates the status of a specific appointment.
     * @param appointmentID The ID of the appointment to update.
     * @param status The new status to set.
     */
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

    /**
     * Checks if a specific time slot is available for a doctor.
     * @param doctorID The ID of the doctor.
     * @param dateTime The date and time of the appointment to check.
     * @return true if the slot is available, false otherwise.
     */
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

    /**
     * Retrieves a list of all appointments.
     * @return A list of all appointments.
     */
    public static List<Appointment> getAppointments() {
        return appointments;
    }
}