package HMS.Patient;

import HMS.Manager.PatientManager;
import HMS.Staff.StaffMenu;
import HMS.User.User;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a menu interface for patients to interact with various functionalities related
 * to their medical records, appointments, and personal information.
 */
public class PatientMenu implements StaffMenu {
    private Patient patient;
    private Scanner scanner;

    /**
     * Constructs a PatientMenu for the specified patient.
     *
     * @param patient The patient who will be using this menu.
     */
    public PatientMenu(Patient patient) {
        this.patient = patient;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the menu and handles user input for different functionalities.
     */
    @Override
    public void displayMenu() {
        int choice = 0;
        do {
            try {
            System.out.println("\n--- Patient Menu ---");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    PatientAppointmentViewer.viewMedicalRecord(patient);
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    PatientAppointmentViewer.viewAvailableAppointmentSlots();
                    break;
                case 4:
                    PatientAppointmentScheduler.appointmentSchedule(patient);

                    break;
                case 5:
                    PatientAppointmentScheduler.appointmentReschedule(patient);

                    break;
                case 6:
                    PatientAppointmentScheduler.appointmentCancel(patient);

                    break;
                case 7:
                    PatientAppointmentViewer.appointmentView(patient);
                    break;
                case 8:
                    PatientAppointmentViewer.pastAppointments(patient);
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }catch (InputMismatchException ime) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }

        } while (choice != 9);
    }


    /**
     * Updates the contact number for the patient associated with this menu.
     */
    private void updateContactNumber(){
        System.out.print("Enter new contact number: ");
        String newContact = scanner.next();
        patient.setContactNumber(newContact);
        System.out.println("Contact number successfully changed");
        List<User> all_users = new ArrayList<>();
        PatientManager.addOrUpdatePatient(patient, all_users);

    }

    /**
     * Updates the email address for the patient associated with this menu.
     */
    private void updateEmailAddress(){
        System.out.print("Enter new email address: ");
        String newEmail = scanner.next();
        patient.setEmailAddress(newEmail);
        System.out.println("Email Address successfully changed");
        List<User> all_users = new ArrayList<>();
        PatientManager.addOrUpdatePatient(patient, all_users);

    }

    /**
     * Allows the patient to update their contact number and email address.
     */
    private void updatePersonalInformation() {
        System.out.printf("Current Contact Number: %s\n", patient.getContactNumber());
        System.out.printf("Current Email Address: %s\n", patient.getEmailAddress());
        System.out.println("1. Update Contact Number");
        System.out.println("2. Update Email Address");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                updateContactNumber();
                break;
            case 2:
                updateEmailAddress();
                break;
            default:
                System.out.println("Number entered is not any of the options");;
        }
    }



}