package HMS.Doctor;

import HMS.Appointment.*;
import HMS.Manager.*;
import HMS.Patient.*;
import HMS.Staff.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Doctor extends Staff {
    private List<Appointment> appointments;
    private List<String> availabilitySlots;
    private List<Patient> patients;

    // Constructor
    public Doctor(String hospitalID, String name, String role, String gender, String age, String password, int loginCount) {
        super(hospitalID, name, role, gender, age, password, loginCount);
        this.appointments = new ArrayList<>();
        this.availabilitySlots = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getAppointment() {
        return appointments;
    }

    public Patient findPatientById(String patientId) {
        return PatientManager.findPatientById(patientId); // Assumes PatientManager has a static method to find patients
    }

    public void refreshAppointments() {
        this.appointments = AppointmentManager.getAppointments();
    }

    // Method to view medical records of a patient
    public void viewPatientMedicalRecord(String patientId) {
        // First, retrieve the patient's details
        Patient patient = PatientManager.findPatientById(patientId);
        if (patient != null) {
            patient.viewMedicalRecord(); // Shows personal info from Patient List
    
            // Then, retrieve the medical records
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

    // Method to update patient medical record
    public void updatePatientMedicalRecord(String patientId) {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Enter the Appointment ID to update:");
        String appointmentId = inputScanner.nextLine();
        MedicalRecord record = MedicalRecordManager.findRecordByAppointmentId(appointmentId);

        if (record != null && record.getDoctorID().equals(this.getHospitalID())) {
            System.out.println("Enter new diagnosis:");
            String diagnosis = inputScanner.nextLine();
            System.out.println("Enter new treatment:");
            String treatment = inputScanner.nextLine();

            // Update the record
            record.setDiagnosis(diagnosis);
            record.setTreatment(treatment);

            // Save the updated record
            MedicalRecordManager.addOrUpdateRecord(record);
            System.out.println("Medical record updated successfully.");
        } else {
            System.out.println("No record found for this appointment ID or not authorized to modify it.");
        }
    }

    // View personal schedule including available slots and scheduled appointments
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
    

    // Method to set availability
    public void setAvailability(List<String> slots) {
        this.availabilitySlots.clear();
        for (String slot : slots) {
            LocalDateTime dateTime = LocalDateTime.parse(slot, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String appointmentId = AppointmentManager.generateNextAppointmentId(); // Ensure this method exists to generate unique IDs
            Appointment newAppointment = new Appointment(appointmentId, "NA", getHospitalID(), dateTime, Appointment.Status.EMPTY);
            
            appointments.add(newAppointment);
            this.availabilitySlots.add(dateTime.toString());
            
            AppointmentManager.addOrUpdateAppointment(newAppointment);
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

    // Respond to appointment requests
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

    // Record outcome of an appointment
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
            

            // Save changes
            AppointmentManager.saveAppointments();
            MedicalRecordManager.saveMedicalRecords();
            
            System.out.println("Appointment outcome recorded and updated successfully.");
        } else {
            System.out.println("Appointment not found, not scheduled, or does not belong to this doctor.");
        }
    }

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

    // Helper method to retrieve a Patient object by ID
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