package HMS.Patient;

import java.util.Set;

public class IDGenerator implements IDGeneratorInterface {
    private Set<String> existingIDs;
    private int patientCount;

    public IDGenerator(Set<String> existingIDs) {
        this.existingIDs = existingIDs;
        this.patientCount = existingIDs.size();
    }

    public String generateUniqueID() {
        System.out.println(patientCount);
        String patientID;
        do {
            patientCount++;
            patientID = "P" + String.format("%04d", patientCount);
        } while (existingIDs.contains(patientID));
        return patientID;
    }
}
