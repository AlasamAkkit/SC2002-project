package HMS.User;

public class User {
    private String hospitalID;
    private String password;
    private String role; // Roles can be Patient, Doctor, Pharmacist, Administrator
    private String name;
    private int loginCount;

    // Constructor
    public User(String hospitalID, String role, String name, String password, int loginCount) {
        this.hospitalID = hospitalID;
        this.password = password; // Default password set as per the assignment requirement
        this.role = role;
        this.name = name;
        this.loginCount = loginCount;
    }

    // Getter for Hospital ID
    public String getHospitalID() {
        return hospitalID;
    }

    public String getPassword() {
        return password;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void incrementLoginCount() {
        this.loginCount++;
    }

    public String getName() {
        return name;
    }
    
    // Getter for Role
    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Setter for Role
    public void setRole(String role) {
        this.role = role;
        System.out.println("Role updated to " + role + " for user: " + hospitalID);
    }

    // Login method that checks the password and updates login status
    public boolean login(String inputPassword) {
        if (this.password.equals(inputPassword)) {
            return true; // Login successful
        }
        return false; // Login failed
    }
}
