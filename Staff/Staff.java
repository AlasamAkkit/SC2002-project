package HMS.Staff;

import HMS.User.*;

/**
 * Represents a generic staff member in the hospital management system.
 */
public class Staff extends User{
    private String gender;
    private String age;

    /**
     * Constructs a Staff object with all necessary details.
     *
     * @param staffID The unique identifier for the staff member.
     * @param name The name of the staff member.
     * @param role The role of the staff member within the hospital (e.g., Nurse, Administrator).
     * @param gender The gender of the staff member.
     * @param age The age of the staff member.
     * @param password The password for the staff member's account.
     * @param loginCount The number of times the staff member has logged in.
     */
    public Staff(String staffID, String name, String role, String gender, String age, String password, int loginCount) {
        super(staffID, role, name, password, loginCount);
        this.gender = gender;
        this.age = age;
    }

    /**
     * Displays the menu for actions the staff member can perform. This method should be overridden in subclasses.
     */
    public void displayMenu() {
        // Default implementation or leave it empty if not applicable
        System.out.println("Menu for generic staff is not defined.");
    }

    /**
     * Gets the unique staff ID.
     *
     * @return A string representing the staff member's unique identifier.
     */
    public String getStaffID() {
        return super.getHospitalID();
    }

    /**
     * Gets the gender of the staff member.
     *
     * @return The gender of the staff member.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Gets the age of the staff member.
     *
     * @return The age of the staff member.
     */
    public String getAge() {
        return age;
    }

    /**
     * Gets the name of the staff member.
     *
     * @return The name of the staff member.
     */
    public String getName() {
        return super.getName();
    }

    /**
     * Sets the gender of the staff member.
     *
     * @param gender The new gender to be set for the staff member.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the age of the staff member.
     *
     * @param age The new age to be set for the staff member.
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Provides a string representation of the staff member, including their ID, name, role, gender, and age.
     *
     * @return A string representation of the staff member.
     */
    public String toString() {
        return "Staff [ID=" + getStaffID() + ", Name=" + getName() + ", Role=" + getRole() + ", Gender=" + gender + ", Age=" + age + "]";
    }
}

