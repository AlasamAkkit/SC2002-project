package HMS.Manager;

import HMS.Pharmacist.*;
import HMS.Staff.Staff;
import java.io.*;
import java.util.*;

/**
 * Manages replenishment requests for medications, including loading, saving, and updating requests.
 */
public class ReplenishManager {

    private static final String CSV_FILE = "HMS/Data/Replenish_List.csv"; // Path to the CSV file
    private static List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    /**
     * Loads replenishment requests from a CSV file.
     */
    public static void loadReplenishments() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file does not exist at path: " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] replenishmentData = line.split(",");
                if (replenishmentData.length == 3) {
                    String ID = replenishmentData[0].trim();
                    String medicationName = replenishmentData[1].trim();
                    String status = replenishmentData[2].trim();

                    ReplenishmentRequest replenishmentRequest = new ReplenishmentRequest(ID,medicationName, status);
                    replenishmentRequests.add(replenishmentRequest);
                    System.out.println("Loaded replenishment request : " + medicationName);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Saves all replenishment requests back to the CSV file.
     */
    public static void saveReplenishmentRequest() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            bw.write("ID,MedicationName,Status");
            bw.newLine();
            for (ReplenishmentRequest replenishmentRequest : replenishmentRequests) {
                    String replenishmentData = replenishmentRequest.getID() + "," +
                    replenishmentRequest.getMedicationName() + "," +
                    replenishmentRequest.getStatus();
                bw.write(replenishmentData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Adds or updates a replenishment request in the list and saves it.
     * @param replenishmentRequest the replenishment request to add or update
     */
    public static void addOrUpdateReplenishment(ReplenishmentRequest replenishmentRequest) {
        for (int i = 0; i < replenishmentRequests.size(); i++) {
            if (replenishmentRequests.get(i).getID().equals(replenishmentRequest.getID())) {
               replenishmentRequests.set(i, replenishmentRequest);  // Update existing req
                saveReplenishmentRequest();  // Save updated data to CSV
                return;
                
            }
        }

        // If req does not exist, add a new one
        replenishmentRequests.add(replenishmentRequest);
        saveReplenishmentRequest();  // Save new data to CSV
        
    }

    /**
     * Finds a replenishment request by ID.
     * @param requestID the ID of the replenishment request to find
     * @return the found ReplenishmentRequest object, or null if no request is found
     */
    public static ReplenishmentRequest findRequestById(String requestID) {
        for (ReplenishmentRequest replenishmentRequest : replenishmentRequests) {
            if (replenishmentRequest.getID().equals(requestID)) {
                return replenishmentRequest;
            }
        }
        return null; // Return null if no req found
    }

    /**
     * Updates the status of a replenishment request.
     * @param requestID the ID of the request to update
     * @param medicationName the name of the medication for logging purposes
     * @param status the new status to set for the request
     */
    public static void updateReplenishmentStatus(String requestID,String medicationName, String status) {
        ReplenishmentRequest replenishmentRequest = findRequestById(requestID);
        if (replenishmentRequest != null) {
            replenishmentRequest.setStatus(status);
            saveReplenishmentRequest(); // Save updated data to CSV
            System.out.println("Updated status of Replenishment Request " + medicationName + " to " + status);
        } else {
            System.out.println("Request not found.");
        }
    }

    /**
     * Retrieves all current replenishment requests.
     * @return a list of all replenishment requests
     */
    public static List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests;
    }

    /**
     * Replenishes the stock level of a specified medication, if possible.
     * @param medicationName the name of the medication to replenish
     * @param quantity the quantity to add to the medication's stock
     * @param staffList the list of staff members, used to locate pharmacists
     * @return true if the stock was successfully replenished, false otherwise
     */
    public static boolean replenishMedicationStock(String medicationName, int quantity, List<Staff> staffList) {
        Map<String, Medication> inventory = MedicineManager.getInventory();

        for (Staff staff : staffList) {
            if (staff instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) staff;
                Medication medication = inventory.get(medicationName);
                if (medication != null) {
                    int newStockLevel = medication.getStockLevel() + quantity;
                    medication.replenish(newStockLevel);
                    MedicineManager.updateMedicationStock(medicationName, newStockLevel);
                    return true;
                }
            }
        }
        return false;
    }
}
