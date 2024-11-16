package HMS.App;

import java.util.List;
import java.util.Map;

import HMS.Appointment.MedicalRecord;
import HMS.Manager.*;
import HMS.Patient.Patient;
import HMS.Pharmacist.Medication;
import HMS.User.User;

public class DataInitializer {
        public static void initialize(List<User> users, List<Patient> patients, Map<String, Medication> inventory,  List<MedicalRecord> medicalRecords) {
        PatientManager.loadPatients(users);
        MedicineManager.loadMedicines();
        PrescriptionManager.loadPrescriptions();
        AppointmentManager.loadAppointments();
        ReplenishManager.loadReplenishments();
        MedicalRecordManager.loadMedicalRecords();
        inventory.putAll(MedicineManager.getInventory());
        StaffManager.loadStaff(users, AppointmentManager.getAppointments(), inventory);
    }
}
