package HMS.Patient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import HMS.Appointment.*;
import HMS.User.*;

public class Patient extends User {
    private String patientID;
    private String name;
    private String dateOfBirth; // Format: YYYY-MM-DD
    private String gender; // Options: Male, Female, Other
    private String contactNumber;
    private String emailAddress;
    private String bloodType;
    private List<String> pastDiagnoses;
    private List<String> treatments;
    private List<Appointment> appointments;

    // Constructor
    public Patient(String hospitalID, String name, String dateOfBirth, String gender,
                   String contactNumber, String emailAddress, String bloodType, String password, int loginCount) {
        super(hospitalID, "Patient", name, password, loginCount);
        this.patientID = hospitalID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
        this.pastDiagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    // Getters
    public String getPatientID() { return patientID; }
    public String getName() { return name; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getContactNumber() { return contactNumber; }
    public String getEmailAddress() { return emailAddress; }
    public String getBloodType() { return bloodType; }
    public List<String> getPastDiagnoses() { return new ArrayList<>(pastDiagnoses); }
    public List<String> getTreatments() { return new ArrayList<>(treatments); }

    // Setters for updating personal information
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String toString() {
        return "Staff [ID=" + getPatientID() + ", Name=" + getName() + ", Role=" + getRole() + ", Gender=" + gender + "]";
    }

    // Functionality to add medical records (Diagnoses and Treatments)
    public void addDiagnosis(String diagnosis) {
        if (diagnosis != null && !diagnosis.isEmpty()) {
            pastDiagnoses.add(diagnosis);
        }
    }

    public void addTreatment(String treatment) {
        if (treatment != null && !treatment.isEmpty()) {
            treatments.add(treatment);
        }
    }

    // Method to display medical record
    public void viewMedicalRecord() {
        System.out.println("Medical Record:");
        System.out.println("Patient ID: " + getHospitalID());
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Gender: " + gender);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Past Diagnoses: " + String.join(", ", pastDiagnoses));
        System.out.println("Treatments: " + String.join(", ", treatments));
    }

    // Overriding methods to manage appointments
    public void scheduleAppointment(Appointment appointment) {
        // Check if the slot is available
        if (appointments.stream().noneMatch(a -> a.getAppointmentTime().equals(appointment.getAppointmentTime()))) {
            appointments.add(appointment);
            System.out.println("Appointment scheduled for " + appointment.getAppointmentTime());
        } else {
            System.out.println("This time slot is already booked.");
        }
    }

    // Method to view all scheduled and past appointments
    public void viewAppointments() {
        System.out.println("Scheduled Appointments:");
        appointments.stream()
            .filter(a -> a.getStatus().equals("Scheduled") || a.getStatus().equals("Rescheduled"))
            .forEach(a -> System.out.println("Appointment on " + a.getAppointmentTime() + " with Status: " + a.getStatus()));

        System.out.println("Past Appointments:");
        appointments.stream()
            .filter(a -> !a.getStatus().equals("Scheduled") && !a.getStatus().equals("Rescheduled"))
            .forEach(a -> System.out.println("Appointment on " + a.getAppointmentTime() + " with Status: " + a.getStatus()));
    }

    // Method to view and manage past appointment outcome records
    public void viewAppointmentOutcomes() {
        List<Appointment> completedAppointments = appointments.stream()
            .filter(a -> a.getStatus().equals("Completed"))
            .collect(Collectors.toList());

        if (completedAppointments.isEmpty()) {
            System.out.println("No completed appointments found.");
        } else {
            System.out.println("Completed Appointments:");
            for (Appointment appointment : completedAppointments) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                                   ", Date: " + appointment.getAppointmentTime() +
                                   ", Status: " + appointment.getStatus());
            }
        }
    }

    public void cancelAppointment(String appointmentID) {
        Optional<Appointment> appointmentToCancel = appointments.stream()
            .filter(a -> a.getAppointmentID().equals(appointmentID))
            .findFirst();

        if (appointmentToCancel.isPresent()) {
            appointmentToCancel.get().setStatus("Canceled");
            System.out.println("Appointment canceled.");
        } else {
            System.out.println("Appointment not found.");
        }
    }

    public void rescheduleAppointment(String appointmentID, LocalDateTime newTime) {
        Optional<Appointment> appointmentToReschedule = appointments.stream()
            .filter(a -> a.getAppointmentID().equals(appointmentID))
            .findFirst();

        if (appointmentToReschedule.isPresent()) {
            appointmentToReschedule.get().setAppointmentTime(newTime);
            appointmentToReschedule.get().setStatus("Rescheduled");
            System.out.println("Appointment rescheduled to " + newTime);
        } else {
            System.out.println("Appointment not found.");
        }
    }
}
