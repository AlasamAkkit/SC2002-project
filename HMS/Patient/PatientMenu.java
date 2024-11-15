package HMS.Patient;

import HMS.Appointment.*;
import HMS.Manager.AppointmentManager;
import HMS.Staff.StaffMenu;
import java.util.List;
import java.util.Scanner;

public class PatientMenu implements StaffMenu {
    private Patient patient;
    private Scanner scanner;
    private List<Appointment> all_appointments;

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
                    viewAvailableAppointmentSlots(); // Assuming this method shows available slots
                    break;
                case 4:
                    appointmentSchedule();
                    break;
                case 5:
                    appointmentReschedule();
                    break;
                case 6:
                    appointmentCancel();
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

    public void viewAvailableAppointmentSlots(){

        //Initialising some dummy variables. Remove after implementing doctor available slots
        all_appointments = AppointmentManager.getAppointments();
        for (Appointment appointment: all_appointments){
            if (appointment.getPatientID().equals("NA")){
                appointment.setStatus("EMPTY");
                AppointmentManager.addOrUpdateAppointment(appointment);
            }
        }
        // End of dummy variable code

        System.out.println("Available Appointment Slots: ");
        for (Appointment appointment: all_appointments){
            if (appointment.getStatus().equals("EMPTY") && appointment.getPatientID().equals("NA")){
                printAppointment(appointment);
            }
        }
    }

    public void appointmentSchedule(){
        viewAvailableAppointmentSlots();
        System.out.println("Select an appointmentID to schedule an appointment: ");
        String appID = scanner.next();
        for (Appointment appointment : all_appointments){
            if (appointment.getAppointmentID().equals(appID)){
                if (appointment.getPatientID().equals("NA") && appointment.getStatus().equals("EMPTY")){
                    appointment.setPatientID(patient.getHospitalID());
                    appointment.setStatus("Scheduled");
                    AppointmentManager.addOrUpdateAppointment(appointment);
                    System.out.println("Appointment slot successfully booked");
                }
                else{
                    System.out.println("Inappropriate Appointment slot selected");

                }
                break;
            }
        }
    }

    public void appointmentCancel(){
        
        appointmentView();

        System.out.println("Select an appointmentID to cancel");
        String appCancel = scanner.next();
        for (Appointment appointment : all_appointments)
        {
            if (appointment.getAppointmentID().equals(appCancel)){
                if (appointment.getPatientID().equals(patient.getHospitalID())){
                    appointment.setPatientID("NA");
                    appointment.setStatus("Cancelled");
                    AppointmentManager.addOrUpdateAppointment(appointment);
                }
                else{
                    System.out.println("Wrong Input");
                    
                }
                break;
            }
        }
    }

    public void appointmentReschedule(){

        appointmentView();

        System.out.println("Enter an appointment ID to reschedule:");
        String appIdReschedule = scanner.next();

        for (Appointment appointment : all_appointments){
            if (appointment.getAppointmentID().equals(appIdReschedule)){
                if (appointment.getPatientID().equals(patient.getHospitalID())){
                    appointment.setPatientID(patient.getHospitalID());
                    appointment.setStatus("Rescheduled");
                    AppointmentManager.addOrUpdateAppointment(appointment);
                    break;
                }
                else{
                    System.out.println("Error Occurred");
                    return;
                }
            }
        }

        viewAvailableAppointmentSlots();

        System.out.println("Enter new appointment ID to reschedule to:");
        String newAppID = scanner.next();

        for (Appointment appointment : all_appointments){
            if (appointment.getAppointmentID().equals(newAppID)){
                printAppointment(appointment);
                if (appointment.getPatientID().equals("NA") && appointment.getStatus().equals("EMPTY")){
                    appointment.setPatientID(patient.getHospitalID());
                    appointment.setStatus("Scheduled");
                    AppointmentManager.addOrUpdateAppointment(appointment);
                    System.out.println("Appointment successfully rescheduled");
                }
                else{
                    System.out.println("Appointment slot already booked or incorrect appointmentID");
                    System.out.println("Please rebook a new appointment as the previous one has already been cancelled");
                }
                break;
            }
        }
    }

    public void appointmentView(){
        System.out.println("All currently scheduled appointments:");
        all_appointments = AppointmentManager.getAppointments();


        //Initialising some dummy variables. Remove after implementing doctor available slots
        for (Appointment appointment: all_appointments){
            if (appointment.getPatientID().equals("NA")){
                appointment.setStatus("EMPTY");
            }
        }
        // End of dummy variable code
    
        for (Appointment appointment : all_appointments){
            if (appointment.getStatus().equals("Scheduled") && appointment.getPatientID().equals(patient.getHospitalID())){
                printAppointment(appointment);
            }
        }
    }




    public static void printAppointments(List<Appointment> appointments) {
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        
        System.out.println("Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                               ", Date: " + appointment.getAppointmentTime() +
                               ", Status: " + appointment.getStatus());
        }
    }

    public static void printAppointment(Appointment appointment) {

        System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                           ", Date: " + appointment.getAppointmentTime() +
                           ", Status: " + appointment.getStatus());
    }
          
}

