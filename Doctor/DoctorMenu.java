package HMS.Doctor;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import HMS.Patient.*;
import HMS.Staff.StaffMenu;
import java.util.InputMismatchException;

/**
 * Provides an interactive menu interface for doctors.
 * Allows a doctor to perform actions like viewing and updating medical records, setting availability,
 * responding to appointment requests, and recording outcomes.
 */
public class DoctorMenu implements StaffMenu{
    private Doctor doctor;
    private Scanner scanner;

    /**
     * Constructor for DoctorMenu.
     * @param doctor The doctor instance this menu will manage.
     * @param patients List of patients (not directly used here but useful if menu needs patient details).
     */
    public DoctorMenu(Doctor doctor, List<Patient> patients) {
        this.doctor = doctor;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the interactive menu for doctor actions and handles user input.
     */
    @Override
    public void displayMenu() {
        int choice = 0;
        do {
            try {
                System.out.println("\n--- Doctor Menu ---");
                System.out.println("1. View Patient Medical Records");
                System.out.println("2. Update Patient Medical Records");
                System.out.println("3. View Personal Schedule");
                System.out.println("4. Set Availability for Appointments");
                System.out.println("5. Accept or Decline Appointment Requests");
                System.out.println("6. View Upcoming Appointments");
                System.out.println("7. Record Appointment Outcome");
                System.out.println("8. Logout");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
    
                switch (choice) {
                    case 1:
                        viewPatientMedicalRecords();
                        break;
                    case 2:
                        updatePatientMedicalRecords();
                        break;
                    case 3:
                        doctor.viewPersonalSchedule();
                        break;
                    case 4:
                        setAvailability();
                        break;
                    case 5:
                        acceptOrDeclineAppointments();
                        break;
                    case 6:
                        doctor.viewConfirmedAppointments();
                        break;
                    case 7:
                        recordAppointmentOutcome();
                        break;
                    case 8:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException ime) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
            }
        } while (choice != 8);
    }

    /**
     * Handles the display and management of viewing a patient's medical records.
     */
    private void viewPatientMedicalRecords() {
        System.out.println("Enter patient ID:");
        String patientId = scanner.next();
        // Instead of passing the Patient object, pass the patientId directly to the method
        doctor.viewPatientMedicalRecord(patientId);
    }

    /**
     * Handles the process for updating a patient's medical records.
     */
    private void updatePatientMedicalRecords() {
        System.out.println("Enter patient ID to update medical records:");
        String patientId = scanner.next();
        doctor.updatePatientMedicalRecord(patientId);
    }

    /**
     * Handles the setting of available time slots for appointments.
     */
    private void setAvailability() {
        System.out.println("Enter available slots (comma-separated, format 'YYYY-MM-DD HH:MM'): ");
        String slotsInput = scanner.nextLine();
        List<String> slots = Arrays.asList(slotsInput.split(","));
        doctor.setAvailability(slots);
    }

    /**
     * Allows the doctor to accept or decline an appointment request.
     */
    private void acceptOrDeclineAppointments() {
        System.out.println("Enter the appointment ID:");
        String appointmentID = scanner.next();
        System.out.println("Accept this appointment? (yes/no):");
        String decision = scanner.next();
        boolean isAccepted = decision.equalsIgnoreCase("yes");
        doctor.respondToAppointmentRequest(appointmentID, isAccepted);
    }

    /**
     * Handles the recording of the outcome for a specific appointment.
     */
    private void recordAppointmentOutcome() {
        System.out.println("Enter appointment ID:");
        String appointmentID = scanner.next();
        scanner.nextLine();  // Consume the lingering newline.
    
        System.out.println("Enter diagnosis:");
        String diagnosis = scanner.nextLine();  // Ensuring full line input for potentially multi-word input
    
        System.out.println("Enter type of service provided:");
        String serviceType = scanner.nextLine();  // As above
    
        System.out.println("Enter treatment:");
        String treatment = scanner.nextLine();  // As above
    
        System.out.println("Enter medication prescribed:");
        String medication = scanner.nextLine();  // As above
    
        System.out.println("Enter consultation notes:");
        String consultationNotes = scanner.nextLine();  // As above
    
        // Passing all collected data to the doctor's method
        doctor.recordAppointmentOutcome(appointmentID, diagnosis, serviceType, treatment, medication, consultationNotes);
    }    
}



