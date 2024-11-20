package HMS.User;

/**
 * Defines the authentication mechanism for users within the system.
 */
public interface AuthenticatorInterface {

    /**
     * Authenticates a user based on their hospital ID, role, and password.
     * This method checks if the user credentials match the stored records and returns the corresponding user object if authentication is successful.
     *
     * @param hospitalID The hospital ID of the user, serving as a unique identifier.
     * @param role The user's role within the system (e.g., Doctor, Patient, Administrator).
     * @param password The user's password for logging into the system.
     * @return The authenticated User object if credentials are valid, otherwise null.
     */
    User authenticate(String hospitalID, String role, String password);
}