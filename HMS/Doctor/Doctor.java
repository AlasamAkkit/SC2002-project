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

    // Method to view personal schedule (Test Case 11)
    public void viewPersonalSchedule() {
        System.out.println("Personal Schedule for Doctor ID: " + getHospitalID());
        for (String slot : availabilitySlots) {
            System.out.println("Available Slot: " + slot);
        }
        System.out.println("Upcoming Appointments:");
        appointments.stream()
            .filter(a -> a.getStatus().equals("Scheduled") || a.getStatus().equals("Confirmed"))
            .forEach(a -> System.out.println("Appointment ID: " + a.getAppointmentID() +
                                             ", Patient ID: " + a.getPatientID() +
                                             ", Time: " + a.getAppointmentTime() +
                                             ", Status: " + a.getStatus()));
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
            String newStatus = isAccepted ? "Confirmed" : "Cancelled";
            appointment.setStatus(newStatus);
            AppointmentManager.updateAppointmentStatus(appointmentID, newStatus);
            this.refreshAppointments();  // Refresh appointments after update
            System.out.println("Appointment " + (isAccepted ? "confirmed" : "cancelled") + ".");
        } else {
            System.out.println("Appointment not found or does not belong to this doctor.");
        }
    }

    // View upcoming appointments
    public void viewUpcomingAppointments() {
        this.refreshAppointments(); // Ensure we're working with the latest data
        System.out.println("Doctor ID for filtering: " + this.getHospitalID());
        List<Appointment> filteredAppointments = this.appointments.stream()
            .filter(a -> a.getDoctorID().equals(this.getHospitalID()) &&
                         (a.getStatus().equals("Scheduled") || a.getStatus().equals("Confirmed")))
            .collect(Collectors.toList());
    
        if (filteredAppointments.isEmpty()) {
            System.out.println("No upcoming appointments found.");
        } else {
            for (Appointment a : filteredAppointments) {
                System.out.println("Appointment ID: " + a.getAppointmentID() + ", Patient ID: " + a.getPatientID() +
                                    ", Time: " + a.getAppointmentTime() + ", Status: " + a.getStatus());
            }
        }
    }

    // Record outcome of an appointment
    public void recordAppointmentOutcome(String appointmentID, String serviceType, String medication, String consultationNotes) {
    Appointment appointment = AppointmentManager.findAppointmentById(appointmentID);
    if (appointment != null && appointment.getDoctorID().equals(this.getHospitalID())) {
        appointment.setStatus("Completed");
        appointment.addPrescription(new Prescription(medication, "Pending"));  // Assuming Prescription class exists
        appointment.setConsultationNotes(consultationNotes);  // Assuming this setter exists
        AppointmentManager.updateAppointmentStatus(appointmentID, "Completed");
        System.out.println("Appointment completed on: " + appointment.getAppointmentTime());
        System.out.println("Type of Service: " + serviceType);
        System.out.println("Medication Prescribed: " + medication);
        System.out.println("Consultation Notes: " + consultationNotes);
        AppointmentManager.saveAppointments();  // Ensure all changes are written back to CSV
    } else {
        System.out.println("Appointment not found or does not belong to this doctor.");
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