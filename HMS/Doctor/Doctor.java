package HMS.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import HMS.Appointment.*;
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
        for (Patient patient : patients) {
            if (patient.getPatientID().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    // Method to view medical records of a patient
    public void viewPatientMedicalRecord(Patient patient) {
        patient.viewMedicalRecord();
    }

    // Method to update patient medical record
    public void updatePatientMedicalRecord(Patient patient, String diagnosis, String treatment, String medication) {
        patient.addDiagnosis(diagnosis);
        patient.addTreatment(treatment);
        // Assume medications are handled elsewhere
        System.out.println("Medical records updated for patient ID: " + patient.getHospitalID());
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
            appointment.get().setStatus(isAccepted ? "Confirmed" : "Declined");
            System.out.println("Appointment " + (isAccepted ? "confirmed" : "declined") + ".");
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