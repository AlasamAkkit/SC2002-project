package HMS.Staff;

import HMS.User.*;

public class Staff extends User{
    private String gender;
    private String age;

    public Staff(String staffID, String name, String role, String gender, String age, String password, int loginCount) {
        super(staffID, role, name, password, loginCount);
        this.gender = gender;
        this.age = age;
    }

    public void displayMenu() {
        // Default implementation or leave it empty if not applicable
        System.out.println("Menu for generic staff is not defined.");
    }

    public String getStaffID() {
        return super.getHospitalID();
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return super.getName();
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String toString() {
        return "Staff [ID=" + getStaffID() + ", Name=" + getName() + ", Role=" + getRole() + ", Gender=" + gender + ", Age=" + age + "]";
    }
}

