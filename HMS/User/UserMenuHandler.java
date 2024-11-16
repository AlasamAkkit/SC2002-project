package HMS.User;

import HMS.Admin.*;
import HMS.Appointment.Appointment;
import HMS.Doctor.*;
import HMS.Patient.*;
import HMS.Pharmacist.*;
import HMS.Staff.Staff;
import java.util.List;

public class UserMenuHandler {
    public void handleUserMenu(User loggedInUser, List<Patient> patients, List<Appointment> appointments,
            List<Prescription> prescriptions, List<ReplenishmentRequest> replenishmentRequests) {
        if (loggedInUser instanceof Patient) {
            Patient patient = (Patient) loggedInUser;
            PatientMenu patientMenu = new PatientMenu(patient);
            patientMenu.displayMenu();
        } else if (loggedInUser instanceof Staff) {
            Staff staffMember = (Staff) loggedInUser;
            chooseStaffMenu(staffMember, patients, appointments, prescriptions, replenishmentRequests);
        } else {
            System.out.println("Role does not have a display menu.");
        }
    }

    private void chooseStaffMenu(Staff staff, List<Patient> patients, List<Appointment> appointments, 
            List<Prescription> prescriptions, List<ReplenishmentRequest> replenishmentRequests) {
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
                PharmacistMenu pharmacistMenu = new PharmacistMenu((Pharmacist) staff, prescriptions, replenishmentRequests);
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
