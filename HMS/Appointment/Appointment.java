package HMS.Appointment;

import HMS.Pharmacist.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an appointment within the healthcare management system.
 * It holds details about the appointment time, involved parties, and the status of the appointment.
 */
public class Appointment {

    public enum Status{
        SCHEDULED, CANCELLED, PENDING, EMPTY, COMPLETED
    }
    
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime appointmentTime;
    private Status status;
    private List<Prescription> prescriptions;
    private String consultationNotes;

    /**
     * Constructs an Appointment with specified details.
     *
     * @param appointmentID   the unique identifier for the appointment
     * @param patientID       the identifier for the patient involved in the appointment
     * @param doctorID        the identifier for the doctor overseeing the appointment
     * @param appointmentTime the scheduled time for the appointment
     * @param status          the current status of the appointment
     */
    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime appointmentTime, Status status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.prescriptions = new ArrayList<>();
    }

    // Getters and Setters with JavaDoc

    public String getAppointmentID() { return appointmentID; }
    public String getPatientID() { return patientID; }
    public String getDoctorID() { return doctorID; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public Status getStatus() { return status; }

    public void setPatientID(String patientID) { this.patientID = patientID; }
    public void setDoctorID(String doctorID) { this.doctorID = doctorID; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setStatus(Status status) { this.status = status; }

    /**
     * Formats and returns the appointment time as a string.
     *
     * @return a formatted date-time string for the appointment time
     */
    public String getAppointmentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return appointmentTime.format(formatter);
    }

    public String getConsultationNotes() { return consultationNotes; }
    public void setConsultationNotes(String consultationNotes) { this.consultationNotes = consultationNotes; }

    /**
     * Adds a prescription to the appointment's list of prescriptions.
     *
     * @param prescription the prescription to be added
     */
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    /**
     * Checks and returns whether any prescriptions associated with this appointment are pending.
     *
     * @return true if there are pending prescriptions, false otherwise
     */
    public boolean hasPendingPrescriptions() {
        return prescriptions.stream().anyMatch(p -> p.getStatus().equals("Pending"));
    }

    /**
     * Generates and returns a detailed string of all prescriptions associated with this appointment.
     *
     * @return a string containing details of all prescriptions
     */
    public String getPrescriptionDetails() {
        StringBuilder details = new StringBuilder();
        for (Prescription prescription : prescriptions) {
            details.append("Medication: ").append(prescription.getMedicationName())
                   .append(", Status: ").append(prescription.getStatus()).append("\n");
        }
        return details.toString();
    }

    /**
     * Retrieves and concatenates the medication names from all prescriptions associated with this appointment.
     *
     * @return a concatenated string of all medication names
     */
    public String getMedicationName() {
        StringBuilder details = new StringBuilder();
        for (Prescription prescription : prescriptions) {
            details.append(prescription.getMedicationName());
        }
        return details.toString();
    }
}
