package HMS.Pharmacist;

/**
 * Represents a request for replenishment of a specific medication.
 */
public class ReplenishmentRequest {

        private String ID;
        private String medicationName;
        private String status; // e.g., "Pending", "Completed"
    
        /**
         * Constructs a new ReplenishmentRequest object.
         *
         * @param ID The unique identifier for the replenishment request.
         * @param medicationName The name of the medication for which replenishment is requested.
         * @param status The initial status of the replenishment request, by default set to "Pending".
         */
        public ReplenishmentRequest(String ID,String medicationName, String status) {
            this.ID = ID;
            this.medicationName = medicationName;
            this.status = "Pending"; // Default status
        }
    
        /**
         * Gets the unique identifier of the replenishment request.
         *
         * @return The ID of the replenishment request.
         */
        public String getID() { 
            return ID; 
        }

        /**
         * Gets the name of the medication requested for replenishment.
         *
         * @return The name of the medication.
         */
        public String getMedicationName() { 
            return medicationName; 
        }
        
        /**
         * Gets the current status of the replenishment request.
         *
         * @return The status of the replenishment request.
         */
        public String getStatus() { 
            return status; 
        }
    
        /**
         * Sets the status of the replenishment request.
         *
         * @param status The new status to set, e.g., "Completed".
         */
        public void setStatus(String status) { 
            this.status = status; 
        }
}


