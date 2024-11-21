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
    @SuppressWarnings("FieldMayBeFinal")
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime appointmentTime;
    private Status status;
    @SuppressWarnings("FieldMayBeFinal")
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

    // Getters and Setters

    /**
     * Returns the appointment ID.
     * @return the appointment ID.
     */
    public String getAppointmentID() { return appointmentID; }

    /**
     * Returns the patient ID.
     * @return the patient ID.
     */
    public String getPatientID() { return patientID; }

    /**
     * Returns the doctor ID.
     * @return the doctor ID.
     */
    public String getDoctorID() { return doctorID; }

    /**
     * Returns the appointment time.
     * @return the appointment time as LocalDateTime.
     */
    public LocalDateTime getAppointmentTime() { return appointmentTime; }

    /**
     * Returns the status of the appointment.
     * @return the appointment status.
     */
    public Status getStatus() { return status; }

    /**
     * Sets the patient ID for this appointment.
     * @param patientID the patient ID to be set.
     */
    public void setPatientID(String patientID) { this.patientID = patientID; }

    /**
     * Sets the doctor ID for this appointment.
     * @param doctorID the doctor ID to be set.
     */
    public void setDoctorID(String doctorID) { this.doctorID = doctorID; }

    /**
     * Sets the appointment time.
     * @param appointmentTime the time to set the appointment to.
     */
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }

    /**
     * Sets the status of the appointment.
     * @param status the status to set for this appointment.
     */
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

    /**
     * Returns the consultation notes.
     *
     * @return consultation notes
     */
    public String getConsultationNotes() { return consultationNotes; }

    /**
     * Sets the consultation notes.
     * @param consultationNotes the notes to set for the appointment.
     */
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
