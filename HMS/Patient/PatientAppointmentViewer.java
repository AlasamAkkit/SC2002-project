package HMS.Patient;

import java.util.List;

import HMS.Appointment.Appointment;
import HMS.Appointment.MedicalRecord;
import HMS.Manager.AppointmentManager;
import HMS.Manager.MedicalRecordManager;

/**
 * This class provides functionalities for patients to view their appointments,
 * including viewing available slots, past appointments 
 * and viewing past medical records.
 */
public class PatientAppointmentViewer{
    
    /**
     * Displays available appointment slots that have not yet been booked.
     * @return A list of available appointments.
     */
    public static List<Appointment> viewAvailableAppointmentSlots(){
        List<Appointment> all_appointments;
        all_appointments = AppointmentManager.findAppointmentsByPatientId("NA");
        System.out.println("Available Appointment Slots: ");
        for (Appointment appointment: all_appointments){
            printAppointment(appointment);
        }
        return all_appointments;
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
            printAppointmentWithStatus(appointment);
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

    /**
     * Displays medical records for the patient.
     * @param patient The patient whose medical records are being displayed.
     */
    public static void viewMedicalRecord(Patient patient) {
        patient.viewMedicalRecord();
        // System.out.println("Past Diagnoses: " + String.join(", ", pastDiagnoses));
        // System.out.println("Treatments: " + String.join(", ", treatments));
        List<MedicalRecord> patientRecords = MedicalRecordManager.findRecordsByPatientId(patient.getPatientID());
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
