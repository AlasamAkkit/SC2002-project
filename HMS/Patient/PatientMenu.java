package HMS.Patient;

import HMS.Appointment.*;
import HMS.Manager.MedicalRecordManager;
import HMS.Manager.PatientManager;
import HMS.Staff.StaffMenu;
import HMS.User.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a menu interface for patients to interact with various functionalities related
 * to their medical records, appointments, and personal information.
 */
public class PatientMenu implements StaffMenu {
    private Patient patient;
    private Scanner scanner;
    private List<Appointment> all_appointments;
    private List<MedicalRecord> patientRecords;

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
        int choice;
        Boolean successful;
        do {
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
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    PatientAppointmentController.viewAvailableAppointmentSlots();
                    break;
                case 4:
                    PatientAppointmentController.appointmentSchedule(patient);

                    break;
                case 5:
                    PatientAppointmentController.appointmentReschedule(patient);

                    break;
                case 6:
                    PatientAppointmentController.appointmentCancel(patient);

                    break;
                case 7:
                    PatientAppointmentController.appointmentView(patient);
                    break;
                case 8:
                    PatientAppointmentController.pastAppointments(patient);
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 9);
    }

    /**
     * Displays the medical record of the patient using this menu.
     */
    private void viewMedicalRecord(){
        patient.viewMedicalRecord();
        patientRecords = MedicalRecordManager.findRecordsByPatientId(patient.getPatientID());
        if (patientRecords.isEmpty())
        {
            System.out.println("No previous visits found");
        }
        else{
            System.out.printf("\nPrevious Visits\n");
            for (MedicalRecord records : patientRecords){
                System.out.printf("%s, Diagnosis: %s, Treatment: %s\n", 
                records.getAppointmentTime(), records.getDiagnosis(), records.getTreatment());
            }
        }
    }

    private void updateContactNumber(){
        System.out.print("Enter new contact number: ");
        String newContact = scanner.next();
        patient.setContactNumber(newContact);
        System.out.println("Contact number successfully changed");
        List<User> all_users = new ArrayList<>();
        PatientManager.addOrUpdatePatient(patient, all_users);

    }

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


    /**
     * Utility method to print the details of an appointment.
     *
     * @param appointment The appointment to print.
     */
    public static void printAppointment(Appointment appointment) {

        System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                            ", DoctorID: " + appointment.getDoctorID() +
                           ", Date: " + appointment.getAppointmentTime());
                        
    } 

    /**
     * Utility method to print the details of an appointment along with its status.
     *
     * @param appointment The appointment to print with status.
     */
    public static void printAppointmentWithStatus(Appointment appointment) {

        System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                            ", DoctorID: " + appointment.getDoctorID() +
                           ", Date: " + appointment.getAppointmentTime() +
                           ", Status: " + appointment.getStatus());
                        
    } 
}