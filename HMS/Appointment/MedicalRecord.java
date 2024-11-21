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
    /**
     * Returns the appointment ID.
     * @return the appointment ID.
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID for this appointment.
     * @param appointmentID the patient ID to be set.
     */
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Returns the patient ID.
     * @return the patient ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient ID for this appointment.
     * @param patientID the patient ID to be set.
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Returns the doctor ID.
     * @return the doctor ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the doctor ID for this appointment.
     * @param doctorID the doctor ID to be set.
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Returns the appointment time.
     * @return the appointment time as LocalDateTime.
     */
    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * Sets the appointment time.
     * @param appointmentTime the time to set the appointment to.
     */
    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * Returns the status of the appointment.
     * @return the appointment status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the appointment.
     * @param status the status to set for this appointment.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the diagnosis of the appointment.
     * @return the diagnosis.
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the diagnosis of the appointment.
     * @param diagnosis the status to set for this appointment.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Returns the service provided of the appointment.
     * @return the service provided.
     */
    public String getServicesProvided() {
        return servicesProvided;
    }

    /**
     * Sets the services provided of the appointment.
     * @param servicesProvided the status to set for this appointment.
     */
    public void setServicesProvided(String servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    /**
     * Returns the treatment of the appointment.
     * @return the treatment.
     */
    public String getTreatment() {
        return treatment;
    }

    /**
     * Sets the treatment of the appointment.
     * @param treatment the status to set for this appointment.
     */
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    /**
     * Returns the prescription of the appointment.
     * @return the prescription.
     */
    public String getPrescription() {
        return prescription;
    }

    /**
     * Sets the prescription of the appointment.
     * @param prescription the status to set for this appointment.
     */
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    /**
     * Returns the consultation notes.
     * @return the consultation notes as a String.
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Sets the consultation notes for this appointment.
     * @param consultationNotes the consultation notes to be set.
     */
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

