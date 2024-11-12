package HMS;

public class Prescription {
    private String medicationName;
    private String status; // e.g., "Pending", "Dispensed"

    // Constructor
    public Prescription(String medicationName) {
        this.medicationName = medicationName;
        this.status = "Pending"; // Default status
    }

    // Getters and setters
    public String getMedicationName() { return medicationName; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}

