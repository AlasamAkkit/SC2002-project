package HMS.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import HMS.Appointment.*;
import HMS.Manager.*;
import HMS.Patient.*;
import HMS.Pharmacist.Prescription;
import HMS.Staff.*;

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
    public void viewPatientMedicalRecord(Patient patient) {
        patient.viewMedicalRecord();
    }

    // Method to update patient medical record
    public void updatePatientMedicalRecord(String patientId, String diagnosis, String treatment, String medication) {
        Patient patient = PatientManager.findPatientById(patientId);
        if (patient != null) {
            patient.addDiagnosis(diagnosis);
            patient.addTreatment(treatment);
            System.out.println("Updated medical records for Patient ID: " + patientId);
        } else {
            System.out.println("Patient not found.");
        }
    }

    // View personal schedule including available slots and scheduled appointments
    public void viewPersonalSchedule() {
        refreshAppointments();  // Ensure the list is up-to-date
    
        System.out.println("Available Slots:");
        availabilitySlots.forEach(slot -> System.out.println(slot));
    
        System.out.println("\nScheduled Appointments:");
        if (appointments.isEmpty()) {
            System.out.println("No scheduled appointments found.");
        } else {
            appointments.stream()
                .filter(a -> a.getStatus() == Appointment.Status.SCHEDULED || a.getStatus() == Appointment.Status.CONFIRMED)
                .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() + ", Patient ID: " + a.getPatientID() +
                                                ", Time: " + a.getAppointmentTime() + ", Status: " + a.getStatus()));
        }
    }

    // Method to set availability (Test Case 12)
    public void setAvailability(List<String> slots) {
        this.availabilitySlots = new ArrayList<>(slots);
        System.out.println("Availability updated for Doctor ID: " + getHospitalID());
    }

    public List<String> getAvailability() {
        return new ArrayList<>(availabilitySlots);  // Return a copy to prevent external modifications
    }

    // Respond to appointment requests
    public void respondToAppointmentRequest(String appointmentID, boolean isAccepted) {
        Appointment appointment = AppointmentManager.findAppointmentById(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(this.getHospitalID())) {
            Appointment.Status newStatus = isAccepted ? Appointment.Status.CONFIRMED : Appointment.Status.CANCELLED;
            appointment.setStatus(newStatus);
            AppointmentManager.updateAppointmentStatus(appointmentID, newStatus);
            System.out.println("Appointment " + (isAccepted ? "confirmed" : "cancelled") + ".");
        } else {
            System.out.println("Appointment not found or does not belong to this doctor.");
        }
    }

    // Record outcome of an appointment
    public void recordAppointmentOutcome(String appointmentID, String serviceType, String medication, String consultationNotes) {
        Appointment appointment = AppointmentManager.findAppointmentById(appointmentID);
        if (appointment != null && appointment.getDoctorID().equals(this.getHospitalID())) {
            appointment.setStatus(Appointment.Status.COMPLETED);
            appointment.addPrescription(new Prescription(appointmentID, medication, "Pending"));  // Adjust according to the actual constructor
            appointment.setConsultationNotes(consultationNotes);
            AppointmentManager.updateAppointmentStatus(appointmentID, Appointment.Status.COMPLETED);
            System.out.println("Appointment completed on: " + appointment.getAppointmentTime());
            System.out.println("Type of Service: " + serviceType);
            System.out.println("Medication Prescribed: " + medication);
            System.out.println("Consultation Notes: " + consultationNotes);
        } else {
            System.out.println("Appointment not found or does not belong to this doctor.");
        }
    }

    public void viewConfirmedAppointments() {
        refreshAppointments();  // Make sure the appointment list is up-to-date
    
        System.out.println("Confirmed Appointments:");
        appointments.stream()
            .filter(a -> a.getStatus() == Appointment.Status.CONFIRMED)
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() + 
                                             ", Patient ID: " + a.getPatientID() + 
                                             ", Date: " + a.getAppointmentTime() + 
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