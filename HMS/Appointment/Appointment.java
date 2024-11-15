package HMS.Appointment;

import HMS.Pharmacist.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Appointment {

    public enum Status{
        SCHEDULED, CANCELLED, PENDING, EMPTY, COMPLETED
    }
    

    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime appointmentTime;
    private Status status; // e.g., "Scheduled", "Completed", "Canceled"
    private List<Prescription> prescriptions; // List of prescriptions related to this appointment
    private String consultationNotes;

    // Constructor
    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime appointmentTime, Status status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = appointmentTime;
        this.status = status; // Default status
        this.prescriptions = new ArrayList<>(); // Initialize prescriptions list
    }

    // Getters and setters
    public String getAppointmentID() { return appointmentID; }
    public String getPatientID() { return patientID; }
    public String getDoctorID() { return doctorID; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public Status getStatus() { return status; }


    public void setPatientID(String patientID) { this.patientID = patientID; }
    public void setDoctorID(String doctorID) { this.doctorID = doctorID; }
    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setStatus(Status status) { this.status = status; }

    public String getAppointmentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return appointmentTime.format(formatter);
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    // Method to add a prescription to the appointment
    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    // Check if there are any pending prescriptions
    public boolean hasPendingPrescriptions() {
        return prescriptions.stream().anyMatch(p -> p.getStatus().equals("Pending"));
    }

    // Get details of prescriptions for this appointment
    public String getPrescriptionDetails() {
        StringBuilder details = new StringBuilder();
        for (Prescription prescription : prescriptions) {
            details.append("Medication: ").append(prescription.getMedicationName())
                   .append(", Status: ").append(prescription.getStatus()).append("\n");
        }
        return details.toString();
    }

    public String getMedicationName() {
        
        StringBuilder details = new StringBuilder();
        for (Prescription prescription : prescriptions) {
            details.append(prescription.getMedicationName())
                   ;
        }
        return details.toString();
    }
}

