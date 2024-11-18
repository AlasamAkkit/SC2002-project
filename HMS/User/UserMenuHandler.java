package HMS.User;

import HMS.Admin.*;
import HMS.Appointment.Appointment;
import HMS.Doctor.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.Staff.Staff;
import java.util.List;

/**
 * Handles the user menu based on the type of user logged in.
 */
public class UserMenuHandler {

    /**
     * Displays the appropriate menu for the logged-in user based on their role.
     *
     * @param loggedInUser           The user who is logged in.
     * @param patients               List of all patients in the system.
     * @param appointments           List of all appointments.
     * @param replenishmentRequests  List of all medication replenishment requests.
     */
    public void handleUserMenu(User loggedInUser, List<Patient> patients, List<Appointment> appointments,
        List<ReplenishmentRequest> replenishmentRequests) {
        if (loggedInUser instanceof Patient) {
            Patient patient = (Patient) loggedInUser;
            PatientMenu patientMenu = new PatientMenu(patient);
            patientMenu.displayMenu();
        } else if (loggedInUser instanceof Staff) {
            Staff staffMember = (Staff) loggedInUser;
            chooseStaffMenu(staffMember, patients, appointments, replenishmentRequests);
        } else {
            System.out.println("Role does not have a display menu.");
        }
    }

    /**
     * Chooses and displays the specific menu based on the staff member's role.
     *
     * @param staff                   The staff member whose menu to display.
     * @param patients                List of patients to be managed by the staff.
     * @param appointments            List of appointments relevant to the staff.
     * @param replenishmentRequests   List of replenishment requests relevant to the staff.
     */
    private void chooseStaffMenu(Staff staff, List<Patient> patients, List<Appointment> appointments, 
        List<ReplenishmentRequest> replenishmentRequests) {
        String role = staff.getRole();

        switch (role.toLowerCase()) {
            case "doctor":
                if (staff instanceof Doctor) {
                    System.out.println("Launching Doctor Menu...");
                    DoctorMenu doctorMenu = new DoctorMenu((Doctor) staff, patients);
                    doctorMenu.displayMenu();
                } else {
                    System.out.println("Error: Staff member is not a doctor.");
                }
                break;

            case "pharmacist":
                System.out.println("Launching Pharmacist Menu...");
                PharmacistMenu pharmacistMenu = new PharmacistMenu((Pharmacist) staff, replenishmentRequests);
                pharmacistMenu.displayMenu();
                break;

            case "administrator":
                System.out.println("Launching Admin Menu...");
                AdminMenu adminMenu = new AdminMenu((Administrator) staff, appointments);
                adminMenu.displayMenu();
                break;

            default:
                System.out.println("Unknown role, no menu available.");
                break;
        }
    }
}
