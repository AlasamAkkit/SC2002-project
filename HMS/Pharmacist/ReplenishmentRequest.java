package HMS.Pharmacist;

public class ReplenishmentRequest {

        private String ID;
        private String medicationName;
        private String status; // e.g., "Pending", "Completed"
    
        // Constructor
        public ReplenishmentRequest(String ID,String medicationName, String status) {
            this.ID = ID;
            this.medicationName = medicationName;
            this.status = "Pending"; // Default status
        }
    
        // Getters and setters


        public String getID() { return ID; }

        public String getMedicationName() { return medicationName; }
        
        public String getStatus() { return status; }
    
        public void setStatus(String status) { this.status = status; }
}


