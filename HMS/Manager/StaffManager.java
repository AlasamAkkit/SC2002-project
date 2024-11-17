package HMS.Manager;

import java.io.*;
import java.util.*;

import HMS.Admin.Administrator;
import HMS.Doctor.Doctor;
import HMS.Pharmacist.*;
import HMS.Staff.*;
import HMS.User.*;
import HMS.Appointment.*;

/**
 * Manages staff operations such as loading, saving, and validating staff login credentials.
 */
public class StaffManager {
    private static final String CSV_FILE = "HMS/Data/Staff_List.csv"; // Path to the CSV file for staff data
    private static List<Staff> staffList = new ArrayList<>(); // List to store staff data

    /**
     * Loads staff data from a CSV file and populates the system's user and staff lists.
     * @param users the system-wide list of users (both staff and patients)
     * @param appointments the list of system-wide appointments
     * @param inventory the medication inventory map
     */
    public static void loadStaff(List<User> users, List<Appointment> appointments, Map<String, Medication> inventory) {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            System.err.println("CSV file does not exist at path: " + file.getAbsolutePath());
            return;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] staffData = line.split(",");
                if (staffData.length == 7) { // Assuming 5 columns: staffID, name, role, contact, email
                    String staffID = staffData[0];
                    String name = staffData[1];
                    String role = staffData[2];
                    String gender = staffData[3];
                    String age = staffData[4];
                    String password = staffData[5];
                    int loginCount = Integer.parseInt(staffData[6]);

                    Staff staff = null;
                    char firstLetter = staffID.charAt(0); // Get the first letter of the staffID
                    
                    switch (Character.toUpperCase(firstLetter)) {
                        case 'D': // If the staffID starts with "D", it's a Doctor
                            staff = new Doctor(staffID, name, role, gender, age, password, loginCount);
                            break;
                        case 'P': // If the staffID starts with "P", it's a Pharmacist
                            staff = new Pharmacist(staffID, name, role, gender, age, inventory, password, loginCount);
                            break;
                        case 'A': // If the staffID starts with "A", it's an Administrator
                            staff = new Administrator(staffID, name, role, gender, age, staffList, appointments, inventory, password, loginCount);
                            break;
                        default:
                            staff = new Staff(staffID, name, role, gender, age, password, loginCount); // Default to generic Staff if unknown
                            break;
                    }

                    users.add(staff);
                    staffList.add(staff);
                    System.out.println("Loaded Staff: " + staffID + ", " + name + ", Role: " + role); // Debugging line
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    /**
     * Validates staff login credentials against stored data.
     * @param staffID the ID of the staff member attempting to log in
     * @param role the role of the staff member
     * @param inputPassword the password provided during login
     * @return true if credentials are valid, false otherwise
     */
    public static boolean isValidLogin(String staffID, String role, String inputPassword) {
        for (Staff staff : staffList) {
            System.out.println("Checking staff ID: " + staff.getHospitalID() + ", Role in record: " + staff.getRole());  // Debugging line
            if (staff.getHospitalID().trim().equals(staffID.trim())) {
                if (staff.getRole().equals(role)) {
                    if (staff.login(inputPassword)) {
                        return true; // Successful login
                    } else {
                        System.out.println("Invalid password.");
                        return false; // Incorrect password
                    }
                } else {
                    System.out.println("Role does not match. Expected: " + role + ", Found: " + staff.getRole());
                    return false; // Role mismatch
                }
            }
        }
        System.out.println("User not found.");
        return false; // Staff not found
    }

    /**
     * Adds or updates a staff member in the list and saves the list to the CSV file.
     * @param staff the staff member to add or update
     */
    public static void addOrUpdateStaff(Staff staff, List<Staff> users) {
        // Check if the staff exists
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getHospitalID().equals(staff.getHospitalID())) {
                staffList.set(i, staff);  // Update existing staff
                saveStaff();  // Save updated data to CSV
                return;
            }
        }

        // If staff does not exist, add a new one
        staffList.add(staff);
        users.add(staff);
        saveStaff();  // Save new data to CSV
    }

    /**
     * Saves all staff data back to the CSV file.
     */
    public static void saveStaff() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            bw.write("HospitalID,Name,Role,Gender,Age,Password,loginCount");
            bw.newLine();
            for (Staff staff : staffList) {
                String staffData = staff.getHospitalID() + "," +
                        staff.getName() + "," +
                        staff.getRole() + "," +
                        staff.getGender() + "," +
                        staff.getAge() + "," +
                        staff.getPassword() + "," +  // Make sure to include password
                        staff.getLoginCount();
                bw.write(staffData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }
    }

    /**
     * Check if the staff is logging in for the first time
     */
    public static boolean isFirstTimeLogin(String staffID) {
        // Check if the staff exists in the list
        for (Staff staff : staffList) {
            if (staff.getStaffID().equals(staffID)) {
                return false; // Staff exists, not first-time login
            }
        }
        return true; // Staff doesn't exist, first-time login
    }

    /**
     * Retrieves the list of all staff members.
     * @return a list containing all registered staff members
     */
    public static List<Staff> getStaffList() {
        return staffList;  // Return the list of staff
    }
    
}