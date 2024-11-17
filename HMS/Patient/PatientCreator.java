package HMS.Patient;

import HMS.Manager.PatientManager;
import HMS.User.User;
import java.util.List;
import java.util.Scanner;

public class PatientCreator {
        public static void createPatientAccount(List<User> users, List<Patient> patients) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Create a new patient account.");

        System.out.print("Enter Patient ID: ");
        String patientID = scanner.nextLine();

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Date of Birth: ");
        String dob = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Blood Type: ");
        String bloodtype = scanner.nextLine();

        // Create a new Patient object
        Patient newPatient = new Patient(patientID, name, dob, gender, contactNumber, email, bloodtype, "password", 0);
        patients.add(newPatient);
        users.add(newPatient);

        PatientManager.addOrUpdatePatient(newPatient, users);

        System.out.println("Account created successfully!");
    }
}
