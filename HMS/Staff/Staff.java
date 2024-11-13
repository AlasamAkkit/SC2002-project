package HMS.Staff;

import HMS.User.*;

public class Staff extends User {
    private String staffID;
    private String name;
    private String gender;
    private String age;

    public Staff(String staffID, String name, String role, String gender, String age) {
        super(staffID, role, name);
        this.gender = gender;
        this.age = age;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

}

