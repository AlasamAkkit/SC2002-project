package HMS.Pharmacist;

/**
 * Represents a prescription associated with a medical appointment.
 */
public class Prescription {
    @SuppressWarnings("FieldMayBeFinal")
    private String appointmentID;
    private String medicationName;
    private String status; // e.g., "Pending", "Dispensed"

    /**
     * Constructs a new Prescription object.
     *
     * @param appointmentID The unique identifier for the appointment associated with this prescription.
     * @param medicationName The name of the medication prescribed.
     * @param status The status of the prescription, e.g., "Pending" or "Dispensed".
     */
    public Prescription(String appointmentID, String medicationName, String status) {
        this.appointmentID = appointmentID;
        this.medicationName = medicationName;
        this.status = status; 
    }

    /**
     * Gets the appointment ID associated with this prescription.
     *
     * @return The appointment ID.
     */
    public String getAppointmentID() { 
        return appointmentID; 
    }

    /**
     * Gets the name of the medication prescribed.
     *
     * @return The medication name.
     */
    public String getMedicationName() { 
        return medicationName; 
    }

    /**
     * Sets the medication name for this prescription.
     *
     * @param medicationName The new name of the medication.
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
    
    /**
     * Gets the current status of the prescription.
     *
     * @return The status of the prescription.
     */
    public String getStatus() { return status; }

    /**
     * Sets the status of the prescription.
     *
     * @param status The new status of the prescription, e.g., "Dispensed".
     */
    public void setStatus(String status) { this.status = status; }
}


