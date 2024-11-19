package HMS.Patient;

/**
 * Interface for generating unique identifiers within the system.
 * This interface is primarily used for creating unique IDs for patients, ensuring that each identifier
 * is distinct across the system.
 */
public interface IDGeneratorInterface{

    /**
     * Generates a unique identifier.
     *
     * @return A unique ID as a String.
     */
    String generateUniqueID();
}
