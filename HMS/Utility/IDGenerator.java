package HMS.Utility;

import java.util.Set;

public class IDGenerator implements IDGeneratorInterface {
    private Set<String> existingIDs;
    private static int patientCount = 0;

    public IDGenerator(Set<String> existingIDs) {
        this.existingIDs = existingIDs;
    }

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
