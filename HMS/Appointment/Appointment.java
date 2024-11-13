package HMS.Appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import HMS.Pharmacist.*;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime appointmentTime;
    private String status; // e.g., "Scheduled", "Completed", "Canceled"
    private List<Prescription> prescriptions; // List of prescriptions related to this appointment

    // Constructor
    public Appointment(String appointmentID, String patientID, String doctorID, LocalDateTime appointmentTime) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = appointmentTime;
        this.status = "Scheduled"; // Default status
        this.prescriptions = new ArrayList<>(); // Initialize prescriptions list
    }

    // Getters and setters
    public String getAppointmentID() { return appointmentID; }
    public String getPatientID() { return patientID; }
    public String getDoctorID() { return doctorID; }
    public LocalDateTime getAppointmentTime() { return appointmentTime; }
    public String getStatus() { return status; }

    public void setAppointmentTime(LocalDateTime appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setStatus(String status) { this.status = status; }

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
}

