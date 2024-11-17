package HMS.App;

import HMS.Appointment.MedicalRecord;
import HMS.Manager.*;
import HMS.Patient.Patient;
import HMS.Pharmacist.Medication;
import HMS.User.User;
import java.util.List;
import java.util.Map;

/**
 * Provides methods to initialize data for the HMS system by loading data
 * from storage into the application's runtime environment.
 */
public class DataInitializer {
    /**
     * Initializes the application's data structures with data loaded from various CSV files.
     * It loads users, patients, medications, appointments, and medical records into the system.
     *
     * @param users A list where loaded user data will be stored.
     * @param patients A list where loaded patient data will be stored.
     * @param inventory A map where loaded medication data will be stored.
     * @param medicalRecords A list where loaded medical record data will be stored.
     */
    public static void initialize(List<User> users, List<Patient> patients, Map<String, Medication> inventory,  List<MedicalRecord> medicalRecords) {
        // Loading patients and linking them to their user profiles
        PatientManager.loadPatients(users);

        // Loading medication details into the system
        MedicineManager.loadMedicines();

        // Loading appointment details
        AppointmentManager.loadAppointments();

        // Loading replenishment details for medications
        ReplenishManager.loadReplenishments();

        // Loading medical records
        MedicalRecordManager.loadMedicalRecords();

        // Compiling the complete inventory from loaded medication details
        inventory.putAll(MedicineManager.getInventory());

        // Loading staff members and associating them with their respective appointments and roles
        StaffManager.loadStaff(users, AppointmentManager.getAppointments(), inventory);
    }
}
