package HMS.Patient;

import HMS.Appointment.Appointment;
import HMS.Appointment.MedicalRecord;
import HMS.Manager.AppointmentManager;
import HMS.Manager.MedicalRecordManager;
import java.util.List;
import java.util.Scanner;

/**
 * This class provides functionalities for patients to manage their appointments,
 * including viewing available slots, scheduling new appointments, cancelling or rescheduling existing ones,
 * and viewing past medical records.
 */
public class PatientAppointmentController{

    /**
     * Displays available appointment slots that have not yet been booked.
     * @return A list of available appointments.
     */
    public static List<Appointment> viewAvailableAppointmentSlots(){
        List<Appointment> all_appointments;
        all_appointments = AppointmentManager.findAppointmentsByPatientId("NA");
        System.out.println("Available Appointment Slots: ");
        for (Appointment appointment: all_appointments){
            PatientMenu.printAppointment(appointment);
        }  
        return all_appointments;
    }

    /**
     * Schedules an appointment for the patient if a valid appointment ID is selected.
     * @param patient The patient who is scheduling the appointment.
     * @return true if the appointment is successfully booked, false otherwise.
     */
    public static boolean appointmentSchedule(Patient patient){
        List<Appointment> all_appointments = viewAvailableAppointmentSlots();
        System.out.println("Select an appointmentID to schedule an appointment: ");
        Scanner scanner = new Scanner(System.in);
        String appID = scanner.next();
        for (Appointment appointment : all_appointments){
            if (appointment.getAppointmentID().equals(appID)){
                appointment.setPatientID(patient.getHospitalID());
                appointment.setStatus(Appointment.Status.PENDING);
                AppointmentManager.addOrUpdateAppointment(appointment);
                System.out.println("Appointment Successfully Booked");
                return true;
            }
        }
        System.out.println("Inappropriate Appointment ID Selected");   
        return false;
    }

    /**
     * Cancels an existing appointment if the specified appointment ID belongs to the patient.
     * @param patient The patient who is cancelling the appointment.
     * @return true if the appointment is successfully cancelled, false otherwise.
     */
    public static boolean appointmentCancel(Patient patient){
        
        List<Appointment> all_appointments = appointmentView(patient);

        System.out.println("Select an appointmentID to cancel");
        Scanner scanner = new Scanner(System.in);
        String appCancel = scanner.next();
        for (Appointment appointment : all_appointments)
        {
            if (appointment.getAppointmentID().equals(appCancel)){
                if (appointment.getPatientID().equals(patient.getHospitalID())){
                    appointment.setPatientID("NA");
                    appointment.setStatus(Appointment.Status.CANCELLED);
                    AppointmentManager.addOrUpdateAppointment(appointment);
                    System.out.println("Appointment Sucessfully Cancelled");
                    return true;
                }
                else{
                    break;
                }
            }
        }
        System.out.println("Inappropriate Appointment ID Selected. Please Try Again.");
        return false;
    }

    /**
     * Reschedules an existing appointment to a new slot selected by the patient.
     * @param patient The patient who is rescheduling the appointment.
     * @return true if the appointment is successfully rescheduled, false otherwise.
     */
    public static boolean appointmentReschedule(Patient patient){

        appointmentView(patient);
        System.out.println("Enter an appointment ID to reschedule:");
        Scanner scanner = new Scanner(System.in);
        String appIdReschedule = scanner.next();
        Appointment appointment = AppointmentManager.findAppointmentById(appIdReschedule);
        if (appointment.getPatientID().equals(patient.getPatientID())){
            Boolean successful = appointmentSchedule(patient);
            if (successful){
                appointment.setPatientID("NA");
                appointment.setStatus(Appointment.Status.CANCELLED);
                AppointmentManager.addOrUpdateAppointment(appointment);
                return true;
            }
        }
        else{
            System.out.println("Inappropriate Appointment ID Selected. Please Try Again.");
        }
        return false;
    }

    /**
     * Displays all pending or scheduled appointments for the patient.
     * @param patient The patient whose appointments are being viewed.
     * @return A list of all appointments associated with the patient.
     */
    public static List<Appointment> appointmentView(Patient patient){
        List<Appointment> all_appointments;
        all_appointments = AppointmentManager.findAppointmentsByPatientId(patient.getPatientID());
        System.out.println("All currently pending or scheduled appointments:");
        for (Appointment appointment : all_appointments){
            PatientMenu.printAppointmentWithStatus(appointment);
        }
        return all_appointments;
    }

    /**
     * Displays past medical appointments and records for the patient.
     * @param patient The patient whose past appointments are being displayed.
     */
    public static void pastAppointments(Patient patient){
        List<MedicalRecord> patientRecords;
        patientRecords = MedicalRecordManager.findRecordsByPatientId(patient.getPatientID());
        for (MedicalRecord records : patientRecords){
            System.out.printf("Appointment ID: %s\nDate: %s\nDoctor: %s\nDiagnosis: %s\nServices Provided: %s\nPrescriptions: %s\nDoctor Notes: %s\n\n", 
                                records.getAppointmentID(),records.getAppointmentTime(), records.getDoctorID(), records.getDiagnosis(), 
                                records.getServicesProvided() ,records.getPrescription() ,records.getConsultationNotes());
        }
        if (patientRecords.isEmpty()){
            System.out.println("No Past Appointments Found.");
        }
    }
}