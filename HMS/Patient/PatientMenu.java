package HMS.Patient;

import java.time.LocalDateTime;
import java.util.Scanner;
import HMS.Appointment.*;
import HMS.User.*;

public class PatientMenu implements UserMenu {
    private Patient patient;
    private Scanner scanner;

    public PatientMenu(Patient patient) {
        this.patient = patient;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        int choice;
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
                    patient.viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation();
                    break;
                case 3:
                    patient.viewAppointments(); // Assuming this method shows available slots
                    break;
                case 4:
                    System.out.println("Enter details for new appointment:");
                    // Details input is assumed for demonstration
                    LocalDateTime dateTime = LocalDateTime.now(); // Replace with actual input and parsing
                    Appointment newAppointment = new Appointment("NewAppID", patient.getHospitalID(), "DoctorID", dateTime);
                    patient.scheduleAppointment(newAppointment);
                    break;
                case 5:
                    System.out.println("Enter the appointment ID to reschedule:");
                    String appIdReschedule = scanner.next();
                    System.out.println("Enter new date and time (yyyy-MM-ddTHH:mm):");
                    String newDateTimeStr = scanner.next();
                    LocalDateTime newDateTime = LocalDateTime.parse(newDateTimeStr);
                    patient.rescheduleAppointment(appIdReschedule, newDateTime);
                    break;
                case 6:
                    System.out.println("Enter the appointment ID to cancel:");
                    String appIdCancel = scanner.next();
                    patient.cancelAppointment(appIdCancel);
                    break;
                case 7:
                    patient.viewAppointments();
                    break;
                case 8:
                    patient.viewAppointmentOutcomes();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 9);
    }

    private void updatePersonalInformation() {
        System.out.println("Enter new contact number:");
        String newContact = scanner.next();
        System.out.println("Enter new email address:");
        String newEmail = scanner.next();

        patient.setContactNumber(newContact);
        patient.setEmailAddress(newEmail);
        System.out.println("Personal information updated.");
    }
}

