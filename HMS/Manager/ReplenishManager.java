package HMS.Manager;

import HMS.Pharmacist.ReplenishmentRequest;
import java.io.*;
import java.util.*;

public class ReplenishManager {

    private static final String CSV_FILE = "HMS/Data/Replenish_List.csv"; // Path to the CSV file
    private static List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();

    // Load medicines from the CSV file
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

    // Save the prescriptions' data back to the CSV file
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


    
    // Find replenishmentRequest by ID
    public static ReplenishmentRequest findRequestById(String requestID) {
        for (ReplenishmentRequest replenishmentRequest : replenishmentRequests) {
            if (replenishmentRequest.getID().equals(requestID)) {
                return replenishmentRequest;
            }
        }
        return null; // Return null if no req found
    }

    // Update replenishmentRequest status
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

    public static List<ReplenishmentRequest> getReplenishmentRequests() {
        return replenishmentRequests;
    }
}