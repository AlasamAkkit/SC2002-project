package HMS.Patient;

import java.util.Set;

/**
 * Generates unique patient IDs for the healthcare management system.
 * Ensures that each ID generated is unique by checking against a set of existing IDs.
 */
public class IDGenerator implements IDGeneratorInterface {
    private Set<String> existingIDs;
    private static int patientCount = 0;

    /**
     * Constructs an IDGenerator with a set of existing patient IDs.
     *
     * @param existingIDs A set containing already assigned patient IDs.
     */
    public IDGenerator(Set<String> existingIDs) {
        this.existingIDs = existingIDs;
    }

    /**
     * Generates a unique patient ID that is not present in the existing set of IDs.
     * It starts with "P" followed by a sequential four-digit number.
     *
     * @return A unique patient ID.
     */
    public String generateUniqueID() {
        //int patientCount = existingIDs.size();
        String patientID;
        do {
            patientCount++;
            patientID = "P" + String.format("%04d", patientCount);
        } while (existingIDs.contains(patientID));
        existingIDs.add(patientID);
        return patientID;
    }
}
