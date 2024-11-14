package HMS.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import HMS.Appointment.*;
import HMS.Manager.PatientManager;
import HMS.Patient.*;
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

    // Set availability slots
    public void setAvailability(List<String> slots) {
        availabilitySlots.clear();
        availabilitySlots.addAll(slots);
        System.out.println("Availability updated.");
    }

    // Respond to appointment requests
    public void respondToAppointmentRequest(String appointmentID, boolean isAccepted) {
        Optional<Appointment> appointment = appointments.stream()
            .filter(a -> a.getAppointmentID().equals(appointmentID))
            .findFirst();
    
        if (appointment.isPresent()) {
            if (isAccepted) {
                appointment.get().setStatus("Confirmed");
            } else {
                appointment.get().setStatus("Cancelled");
            }
            System.out.println("Appointment " + (isAccepted ? "confirmed" : "cancelled") + ".");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    // View upcoming appointments
    public void viewUpcomingAppointments() {
        System.out.println("Upcoming Appointments:");
        appointments.stream()
            .filter(a -> a.getStatus().equals("Scheduled") || a.getStatus().equals("Confirmed"))
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() +
                                             ", Patient ID: " + a.getPatientID() +
                                             ", Time: " + a.getAppointmentTime()));
    }

    // Record outcome of an appointment
    public void recordAppointmentOutcome(String appointmentID, String serviceType, String medication, String consultationNotes) {
        Optional<Appointment> appointment = appointments.stream()
            .filter(a -> a.getAppointmentID().equals(appointmentID))
            .findFirst();

        if (appointment.isPresent()) {
            appointment.get().setStatus("Completed");
            System.out.println("Appointment completed on: " + appointment.get().getAppointmentTime());
            System.out.println("Type of Service: " + serviceType);
            System.out.println("Medication Prescribed: " + medication);
            System.out.println("Consultation Notes: " + consultationNotes);
        } else {
            System.out.println("Appointment not found.");
        }
    }

    public void viewConfirmedAppointments() {
        System.out.println("Confirmed Appointments:");
        for (Appointment appointment : appointments) {
            if ("Confirmed".equals(appointment.getStatus())) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                                   ", Patient ID: " + appointment.getPatientID() +
                                   ", Date: " + appointment.getAppointmentTime() +
                                   ", Status: " + appointment.getStatus());
            }
        }
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