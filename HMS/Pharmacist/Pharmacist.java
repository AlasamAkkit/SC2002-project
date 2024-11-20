package HMS.Pharmacist;

import HMS.Staff.*;
import java.util.*;

/**
 * Represents a pharmacist, a staff member responsible for managing medications and prescriptions.
 */
public class Pharmacist extends Staff {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Medication> inventory; // Inventory of medications by name

    /**
     * Constructs a new Pharmacist object.
     *
     * @param hospitalID Unique identifier for the hospital staff.
     * @param role The role of the staff member, should be "Pharmacist".
     * @param name The name of the pharmacist.
     * @param gender The gender of the pharmacist.
     * @param age The age of the pharmacist.
     * @param inventory A map of medications available to the pharmacist.
     * @param password The login password for the pharmacist.
     * @param loginCount The count of how many times the pharmacist has logged in.
     */
    public Pharmacist(String hospitalID, String role, String name, String gender, String age, Map<String, Medication> inventory, String password, int loginCount) {
        super(hospitalID, role, name, gender, age, password, loginCount);
        this.inventory = inventory;
    }

    /**
     * Returns the inventory of medications.
     *
     * @return A map of medication names to Medication objects.
     */
    public Map<String, Medication> getInventory() {
        return inventory;
    }
    
}