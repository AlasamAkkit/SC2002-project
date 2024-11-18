package HMS.User;

/**
 * Represents a user in the Hospital Management System.
 */
public class User {
    private String hospitalID;
    private String password;
    private String role; // Roles can be Patient, Doctor, Pharmacist, Administrator
    private String name;
    private int loginCount;

    /**
     * Constructs a new User with the specified details.
     *
     * @param hospitalID Unique identifier for the user.
     * @param role       The role of the user within the system.
     * @param name       The name of the user.
     * @param password   The password for the user's account.
     * @param loginCount The count of how many times the user has logged in.
     */
    public User(String hospitalID, String role, String name, String password, int loginCount) {
        this.hospitalID = hospitalID;
        this.password = password; // Default password set as per the assignment requirement
        this.role = role;
        this.name = name;
        this.loginCount = loginCount;
    }

    /**
     * Gets the hospital ID of the user.
     *
     * @return The hospital ID.
     */
    public String getHospitalID() {
        return hospitalID;
    }

    /**
     * Gets the user's password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the login count for the user.
     *
     * @return The login count.
     */
    public int getLoginCount() {
        return loginCount;
    }

    /**
     * Increments the login count by one.
     */
    public void incrementLoginCount() {
        this.loginCount++;
    }

    /**
     * Gets the name of the user.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the role of the user.
     *
     * @return The role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the login count for the user.
     *
     * @param loginCount The new login count.
     */
    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * Sets the password for the user.
     *
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The new role.
     */
    public void setRole(String role) {
        this.role = role;
        System.out.println("Role updated to " + role + " for user: " + hospitalID);
    }

    /**
     * Authenticates a user based on the password provided.
     *
     * @param inputPassword The password to verify.
     * @return true if the password matches, false otherwise.
     */
    public boolean login(String inputPassword) {
        if (this.password.equals(inputPassword)) {
            return true; // Login successful
        }
        return false; // Login failed
    }
}
