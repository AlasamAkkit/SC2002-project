package HMS.Patient;

import HMS.Appointment.Appointment;
import HMS.Manager.AppointmentManager;
import java.util.List;
import java.util.Scanner;

/**
 * This class provides functionalities for patients to manage their appointments,
 * including viewing available slots, scheduling new appointments, cancelling or rescheduling existing ones,
 * and viewing past medical records.
 */
public class PatientAppointmentScheduler{

    /**
     * Schedules an appointment for the patient if a valid appointment ID is selected.
     * @param patient The patient who is scheduling the appointment.
     * @return true if the appointment is successfully booked, false otherwise.
     */
    public static boolean appointmentSchedule(Patient patient){
        List<Appointment> all_appointments = PatientAppointmentViewer.viewAvailableAppointmentSlots();
        System.out.println("Select an appointmentID to schedule an appointment: ");
        @SuppressWarnings("resource")
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
        
        List<Appointment> all_appointments = PatientAppointmentViewer.appointmentView(patient);

        System.out.println("Select an appointmentID to cancel");
        @SuppressWarnings("resource")
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

        PatientAppointmentViewer.appointmentView(patient);
        System.out.println("Enter an appointment ID to reschedule:");
        @SuppressWarnings("resource")
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

}