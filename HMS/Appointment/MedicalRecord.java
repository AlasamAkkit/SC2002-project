package HMS.Appointment;

import java.time.LocalDateTime;

/**
 * Represents a medical record associated with an appointment within the healthcare management system.
 * It holds comprehensive details about the patient's medical encounter including diagnosis and treatment.
 */
public class MedicalRecord {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private LocalDateTime appointmentTime;
    private String status;
    private String diagnosis;
    private String servicesProvided;
    private String treatment;
    private String prescription;
    private String consultationNotes;

    /**
     * Constructs a MedicalRecord with detailed information about a medical appointment.
     *
     * @param appointmentID      Unique identifier for the appointment.
     * @param patientID          Identifier of the patient.
     * @param doctorID           Identifier of the doctor.
     * @param appointmentTime    Time of the appointment.
     * @param status             Current status of the medical record.
     * @param diagnosis          Diagnosis made during the appointment.
     * @param servicesProvided   Services provided during the appointment.
     * @param treatment          Treatment prescribed.
     * @param prescription       Prescription details.
     * @param consultationNotes  Notes from the consultation.
     */
    // Constructor
    public MedicalRecord(String appointmentID, String patientID, String doctorID, LocalDateTime appointmentTime,
                         String status, String diagnosis, String servicesProvided, String treatment,
                         String prescription, String consultationNotes) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.diagnosis = diagnosis;
        this.servicesProvided = servicesProvided;
        this.treatment = treatment;
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(String servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    // Override toString() for better debugging and display
    /**
     * Provides a string representation of the medical record.
     * Useful for logging and displaying the record.
     *
     * @return String that represents the medical record.
     */
    @Override
    public String toString() {
        return "MedicalRecord{" +
                "appointmentID='" + appointmentID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", doctorID='" + doctorID + '\'' +
                ", appointmentTime=" + appointmentTime +
                ", status='" + status + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", servicesProvided='" + servicesProvided + '\'' +
                ", treatment='" + treatment + '\'' +
                ", prescription='" + prescription + '\'' +
                ", consultationNotes='" + consultationNotes + '\'' +
                '}';
    }
}

