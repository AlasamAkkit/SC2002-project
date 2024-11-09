package HMS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DoctorMenu implements UserMenu {
    private Doctor doctor;
    private List<Patient> patients; // List of patients that doctor can access
    private Scanner scanner;

    public DoctorMenu(Doctor doctor, List<Patient> patients) {
        this.doctor = doctor;
        this.patients = patients;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void displayMenu() {
        int choice;
        do {
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
                    doctor.viewUpcomingAppointments();
                    break;
                case 4:
                    setAvailability();
                    break;
                case 5:
                    acceptOrDeclineAppointments();
                    break;
                case 6:
                    doctor.viewUpcomingAppointments();
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
        } while (choice != 8);
    }

    private void viewPatientMedicalRecords() {
        System.out.println("Enter patient ID:");
        String patientId = scanner.next();
        Patient patient = doctor.findPatientById(patientId, patients);
        if (patient != null) {
            doctor.viewPatientMedicalRecord(patient);
        }
    }

    private void updatePatientMedicalRecords() {
        System.out.println("Enter patient ID:");
        String patientId = scanner.next();
        Patient patient = doctor.findPatientById(patientId, patients);
        if (patient != null) {
            System.out.println("Enter diagnosis:");
            String diagnosis = scanner.next();
            System.out.println("Enter treatment:");
            String treatment = scanner.next();
            System.out.println("Enter medication:");
            String medication = scanner.next();
            doctor.updatePatientMedicalRecord(patient, diagnosis, treatment, medication);
        }
    }

    private void setAvailability() {
        System.out.println("Enter available slots (comma-separated):");
        String slots = scanner.nextLine();
        List<String> availabilitySlots = new ArrayList<>(Arrays.asList(slots.split(",")));
        doctor.setAvailability(availabilitySlots);
    }

    private void acceptOrDeclineAppointments() {
        System.out.println("Enter the appointment ID:");
        String appointmentID = scanner.next();
        System.out.println("Accept this appointment? (yes/no):");
        String decision = scanner.next();
        boolean isAccepted = decision.equalsIgnoreCase("yes");
        doctor.respondToAppointmentRequest(appointmentID, isAccepted);
    }

    private void recordAppointmentOutcome() {
        System.out.println("Enter appointment ID:");
        String appointmentID = scanner.next();
        System.out.println("Enter type of service provided:");
        String serviceType = scanner.next();
        System.out.println("Enter medication prescribed:");
        String medication = scanner.next();
        System.out.println("Enter consultation notes:");
        String consultationNotes = scanner.next();
        doctor.recordAppointmentOutcome(appointmentID, serviceType, medication, consultationNotes);
    }
}



