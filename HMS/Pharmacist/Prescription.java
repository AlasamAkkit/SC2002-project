package HMS.Pharmacist;

public class Prescription {
    @SuppressWarnings("FieldMayBeFinal")
    private String appointmentID;
    private String medicationName;
    private String status; // e.g., "Pending", "Dispensed"

    // Constructor
    public Prescription(String appointmentID, String medicationName, String status) {
        this.appointmentID = appointmentID;
        this.medicationName = medicationName;
        this.status = status; 
    }

    // Getters and setters

    public String getAppointmentID() { 
        return appointmentID; 
    }

    public String getMedicationName() { 
        return medicationName; 
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
    
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}


