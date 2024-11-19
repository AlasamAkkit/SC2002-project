package HMS.Patient;

import java.util.Set;

public class IDGenerator implements IDGeneratorInterface {
    private static Set<String> existingIDs;

    public IDGenerator(Set<String> existingIDs) {
        this.existingIDs = existingIDs;
    }

    public String generateUniqueID() {
        int patientCount = existingIDs.size();
        String patientID;
        do {
            patientCount++;
            patientID = "P" + String.format("%04d", patientCount);
        } while (existingIDs.contains(patientID));
        return patientID;
    }
}
