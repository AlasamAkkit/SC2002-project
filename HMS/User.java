package HMS;

public class User {
    private String hospitalID;
    private String password;
    private String role; // Roles can be Patient, Doctor, Pharmacist, Administrator
    private boolean isFirstLogin;

    // Constructor
    public User(String hospitalID, String role) {
        this.hospitalID = hospitalID;
        this.password = "password"; // Default password set as per the assignment requirement
        this.role = role;
        this.isFirstLogin = true; // Flag to enforce password change on first login
    }

    // Getter for Hospital ID
    public String getHospitalID() {
        return hospitalID;
    }

    // Getter for Role
    public String getRole() {
        return role;
    }

    // Setter for Role
    public void setRole(String role) {
        this.role = role;
        System.out.println("Role updated to " + role + " for user: " + hospitalID);
    }

    // Login method that checks the password and updates login status
    public boolean login(String inputPassword) {
        if (this.password.equals(inputPassword)) {
            if (isFirstLogin) {
                System.out.println("First-time login detected - please change your password.");
            }
            return true; // Login successful
        }
        return false; // Login failed
    }

    // Method to change password
    public boolean changePassword(String oldPassword, String newPassword) {
        if (!this.password.equals(oldPassword)) {
            System.out.println("Error: Your old password is incorrect.");
            return false;
        } else if (oldPassword.equals(newPassword)) {
            System.out.println("New password must be different from the old password.");
            return false;
        } else {
            this.password = newPassword;
            this.isFirstLogin = false;
            System.out.println("Password successfully changed.");
            return true;
        }
    }

    // Method to reset the first login flag (useful for administrative purposes or resetting accounts)
    public void resetFirstLogin() {
        isFirstLogin = true;
    }
}

