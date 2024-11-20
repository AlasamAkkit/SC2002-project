package HMS.Doctor;

import HMS.Appointment.*;
import HMS.Manager.*;
import HMS.Patient.*;
import HMS.Staff.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Represents a Doctor within the hospital management system, extending the Staff class.
 * This class manages doctor-specific functionalities such as handling appointments,
 * medical records, and availability scheduling.
 */
public class Doctor extends Staff {
    private List<Appointment> appointments;
    @SuppressWarnings("FieldMayBeFinal")
    private List<String> availabilitySlots;
    @SuppressWarnings("FieldMayBeFinal")
    private List<Patient> patients;

    /**
     * Constructs a Doctor with specified details.
     *
     * @param hospitalID Unique identifier for the hospital where the doctor is employed.
     * @param name       Doctor's full name.
     * @param role       Doctor's role within the hospital.
     * @param gender     Doctor's gender.
     * @param age        Doctor's age.
     * @param password   Password for system access.
     * @param loginCount Number of times the doctor has logged into the system.
     */
    // Constructor
    public Doctor(String hospitalID, String name, String role, String gender, String age, String password, int loginCount) {
        super(hospitalID, name, role, gender, age, password, loginCount);
        this.appointments = new ArrayList<>();
        this.availabilitySlots = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    /**
     * Adds a patient to the doctor's patient list.
     *
     * @param patient The patient to add.
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    /**
     * Returns a list of the doctor's patients.
     *
     * @return A list of patients.
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Adds an appointment to the doctor's schedule.
     *
     * @param appointment The appointment to add.
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Returns a list of the doctor's appointments.
     *
     * @return A list of appointments.
     */
    public List<Appointment> getAppointment() {
        return appointments;
    }

    /**
     * Finds a patient by their ID.
     *
     * @param patientId The ID of the patient to find.
     * @return The found patient, or null if no patient is found.
     */
    public Patient findPatientById(String patientId) {
        return PatientManager.findPatientById(patientId); // Assumes PatientManager has a static method to find patients
    }

    /**
     * Refreshes the doctor's list of appointments from the central appointment manager.
     */
    public void refreshAppointments() {
        this.appointments = AppointmentManager.getAppointments();
    }

    /**
     * Displays medical records for a specific patient.
     *
     * @param patientId The ID of the patient whose records to view.
     */
    public void viewPatientMedicalRecord(String patientId) {
        Patient patient = PatientManager.findPatientById(patientId);
        if (patient != null) {
            patient.viewMedicalRecord(); 
    
            List<MedicalRecord> records = MedicalRecordManager.findRecordsByPatientId(patient.getHospitalID()).stream()
            .filter(record -> record.getDoctorID().equals(this.getHospitalID()))  // Filter records by doctor ID
            .collect(Collectors.toList());

            if (records.isEmpty()) {
                System.out.println("No medical records found for this patient with this doctor.");
            } else {
                System.out.println("\nMedical Records:");
                for (MedicalRecord record : records) {
                    System.out.println("Appointment ID: " + record.getAppointmentID());
                    System.out.println("Appointment Time: " + record.getAppointmentTime());
                    System.out.println("Diagnosis: " + record.getDiagnosis());
                    System.out.println("Treatment: " + record.getTreatment());
                }
            }
        }
    }

    /**
     * Updates patient medical records by setting new diagnosis and treatment.
     *
     * @param patientId The ID of the patient whose record is to be updated.
     */
    public void updatePatientMedicalRecord(String patientId) {
        @SuppressWarnings("resource")
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Enter the Appointment ID to update:");
        String appointmentId = inputScanner.nextLine();
        MedicalRecord record = MedicalRecordManager.findRecordByAppointmentId(appointmentId);

        if (record != null && record.getDoctorID().equals(this.getHospitalID())) {
            System.out.println("Enter new diagnosis:");
            String diagnosis = inputScanner.nextLine();
            System.out.println("Enter new treatment:");
            String treatment = inputScanner.nextLine();

            record.setDiagnosis(diagnosis);
            record.setTreatment(treatment);

            MedicalRecordManager.addOrUpdateRecord(record);
            System.out.println("Medical record updated successfully.");
        } else {
            System.out.println("No record found for this appointment ID or not authorized to modify it.");
        }
    }

    /**
     * Views the personal schedule of the doctor, including available slots and pending appointments.
     */
    public void viewPersonalSchedule() {
        List<Appointment> allAppointments = AppointmentManager.getAppointments();
    
        // Filter appointments for this doctor
        List<Appointment> filteredAppointments = allAppointments.stream()
            .filter(a -> a.getDoctorID().equals(this.getHospitalID()))
            .collect(Collectors.toList());
    
        System.out.println("Personal Schedule for Doctor ID: " + this.getHospitalID());
        System.out.println("Available Slots:");
        filteredAppointments.stream()
            .filter(a -> a.getStatus() == Appointment.Status.EMPTY)
            .forEach(a -> System.out.println(a.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    
        System.out.println("\nPending Appointments:");
        filteredAppointments.stream()
            .filter(a -> a.getStatus() == Appointment.Status.PENDING)
            .forEach(a -> {
                System.out.println("Appointment ID: " + a.getAppointmentID() + 
                                   ", Patient ID: " + a.getPatientID() +
                                   ", Time: " + a.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                                   ", Status: " + a.getStatus());
            });
    }
    
    /**
     * Sets availability for the doctor by creating open appointment slots.
     *
     * @param slots A list of time slots in "yyyy-MM-dd HH:mm" format.
     */
    public void setAvailability(List<String> slots) {
        this.availabilitySlots.clear();
        for (String slot : slots) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(slot, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String appointmentId = AppointmentManager.generateNextAppointmentId(); // Ensure this method exists to generate unique IDs
                Appointment newAppointment = new Appointment(appointmentId, "NA", getHospitalID(), dateTime, Appointment.Status.EMPTY);
                appointments.add(newAppointment);
                this.availabilitySlots.add(dateTime.toString());
                AppointmentManager.addOrUpdateAppointment(newAppointment);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format for slot " + slot + ". Please use 'yyyy-MM-dd HH:mm' format.");
                continue; // Skip this iteration and proceed with the next slot
            }
        }
        AppointmentManager.saveAppointments(); // Save all changes after processing all slots
        System.out.println("Availability updated for Doctor ID: " + getHospitalID());
    }

    public List<String> getAvailability() {
        // Retrieve only those appointments that are EMPTY and belong to this doctor
        return AppointmentManager.getAppointments().stream()
            .filter(a -> a.getDoctorID().equals(this.getHospitalID()) && a.getStatus() == Appointment.Status.EMPTY)
            .map(a -> a.getAppointmentTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .collect(Collectors.toList());
    }

    /**
     * Responds to appointment requests by either scheduling or cancelling them.
     *
     * @param appointmentID The ID of the appointment to respond to.
     * @param isAccepted Whether the appointment is accepted (true) or not (false).
     */
    public void respondToAppointmentRequest(String appointmentID, boolean isAccepted) {
        Appointment appointment = AppointmentManager.findAppointmentById(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(this.getHospitalID())) {
            Appointment.Status newStatus = isAccepted ? Appointment.Status.SCHEDULED : Appointment.Status.CANCELLED;
            appointment.setStatus(newStatus);
            AppointmentManager.updateAppointmentStatus(appointmentID, newStatus);
            System.out.println("Appointment " + (isAccepted ? "scheduled" : "cancelled") + ".");
        } else {
            System.out.println("Appointment not found or does not belong to this doctor.");
        }
    }

    /**
     * Records the outcome of an appointment, marking it as completed and updating the medical record.
     *
     * @param appointmentID The ID of the appointment to record the outcome for.
     * @param diagnosis The diagnosis given during the appointment.
     * @param serviceType The type of service provided.
     * @param treatment The treatment administered.
     * @param medication Any medications prescribed.
     * @param consultationNotes Notes from the consultation.
     */
    public void recordAppointmentOutcome(String appointmentID, String diagnosis, String serviceType, String treatment, String medication, String consultationNotes) {
        Appointment appointment = AppointmentManager.findAppointmentById(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(this.getHospitalID()) && appointment.getStatus() == Appointment.Status.SCHEDULED) {
            // Update appointment status
            appointment.setStatus(Appointment.Status.COMPLETED);
            AppointmentManager.updateAppointmentStatus(appointmentID, Appointment.Status.COMPLETED);
            
            // Update or create a medical record
            MedicalRecord record = MedicalRecordManager.findRecordByAppointmentId(appointmentID);
            if (record != null) {
                // Update existing record
                record.setDiagnosis(diagnosis);
                record.setServicesProvided(serviceType);
                record.setTreatment(treatment);
                record.setPrescription(medication);
                record.setConsultationNotes(consultationNotes);
                MedicalRecordManager.addOrUpdateRecord(record);
            } else {
                // Create new record
                record = new MedicalRecord(appointmentID, appointment.getPatientID(), this.getHospitalID(),
                                           appointment.getAppointmentTime(), "COMPLETED", diagnosis, serviceType, 
                                           treatment, medication, consultationNotes);
                MedicalRecordManager.addOrUpdateRecord(record);
            }

            // Delete the appointment from the list
            AppointmentManager.deleteAppointmentById(appointmentID);

            // Save changes
            AppointmentManager.saveAppointments();
            MedicalRecordManager.saveMedicalRecords();
            
            System.out.println("Appointment outcome recorded and updated successfully.");
        } else {
            System.out.println("Appointment not found, not scheduled, or does not belong to this doctor.");
        }
    }

    /**
     * Views the confirmed appointments scheduled to the doctor.
     */
    public void viewConfirmedAppointments() {
        refreshAppointments();  // Make sure the appointment list is up-to-date
    
        System.out.println("Scheduled Appointments for Doctor ID: " + this.getHospitalID() + ":");
        appointments.stream()
            .filter(a -> a.getStatus() == Appointment.Status.SCHEDULED && a.getDoctorID().equals(this.getHospitalID()))
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() +
                                             ", Patient ID: " + a.getPatientID() +
                                             ", Date: " + a.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                                             ", Status: " + a.getStatus()));
    }

    /**
     * Retrieves a patient by their unique ID.
     *
     * @param patientId The unique ID of the patient to find.
     * @param patients List of patients to search from.
     * @return The found patient or null if no patient matches the ID.
     */
    public Patient findPatientById(String patientId, List<Patient> patients) {
        for (Patient patient : patients) {
            if (patient.getHospitalID().equals(patientId)) {
                return patient;
            }
        }
        System.out.println("Patient not found.");
        return null;
    }
}
