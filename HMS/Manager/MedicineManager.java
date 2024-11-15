package HMS.Manager;

import HMS.Pharmacist.Medication;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MedicineManager {
    private static final String CSV_FILE = "HMS/Data/Medicine_List.csv"; // Path to the CSV file
    private static Map<String, Medication> inventory = new HashMap<>();

    // Load medicines from the CSV file
    public static void loadMedicines() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file does not exist at path: " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header row
            while ((line = br.readLine()) != null) {
                String[] medicineData = line.split(",");
                if (medicineData.length == 3) {
                    String medicineName = medicineData[0].trim();
                    int initialStock = Integer.parseInt(medicineData[1].trim());
                    int lowStockLevelAlert = Integer.parseInt(medicineData[2].trim());

                    Medication medicine = new Medication(medicineName, initialStock, lowStockLevelAlert);
                    inventory.put(medicineName, medicine);
                    System.out.println("Loaded Medicine: " + medicine);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    // Add or update a medicine
    public static void addOrUpdateMedicine(Medication newMedicine) {
        inventory.put(newMedicine.getMedicationName(), newMedicine); // Update or add the medicine
        saveMedicines();
    }

    public static void updateMedicationStock(String medicationName, int newStockLevel) {
        Medication medicine = inventory.get(medicationName);
        if (medicine != null) {
            medicine.setStockLevel(newStockLevel); // Update the stock level
            System.out.println("Updated stock for " + medicationName + " to " + newStockLevel);
            saveMedicines(); // Save changes back to the CSV
        } else {
            System.out.println("Medicine not found: " + medicationName);
        }
    }

    // Save the medicines data back to the CSV file
    public static void saveMedicines() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            // Write header
            bw.write("Medicine Name,Initial Stock,Low Stock Level Alert");
            bw.newLine();
            for (Medication medicine : inventory.values()) {
                String medicineData = medicine.getMedicationName() + "," +
                        medicine.getStockLevel() + "," +
                        medicine.getLowStockThreshold();
                bw.write(medicineData);
                bw.newLine();
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    // Check if a medicine is low in stock based on its lowStockLevelAlert
    public static void checkLowStock() {
        for (Medication medicine : inventory.values()) {
            if (medicine.getStockLevel() <= medicine.getLowStockThreshold()) {
                System.out.println("Low stock alert for " + medicine.getMedicationName());
            }
        }
    }

    // Display all medicines
    public static void displayAllMedicines() {
        for (Medication medicine : inventory.values()) {
            System.out.println(medicine);
        }
    }

    public static Map<String, Medication> getInventory() {
        return inventory;
    }

}
