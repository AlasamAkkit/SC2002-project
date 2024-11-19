package HMS.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHandler implements InputInterface {
    private static final Scanner scanner = new Scanner(System.in);

    public InputHandler() {
    }

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    @Override
    public String getValidatedInput(String prompt, Validator validator) {
        String input;
        do {
            input = getInput(prompt);
            if (!validator.isValid(input)) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!validator.isValid(input));
        return input;
    }

    public String getValidPhoneNumber(String prompt) {
        String phoneRegex = "^[89]\\d{7}$";
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.matches(phoneRegex)) {
                return input;
            }
            System.out.println("Invalid phone number. Please enter an 8-digit phone number starting with 8 or 9.");
        }
    }

    public String getGender(String prompt) {
        System.out.println(prompt);
        String gender = scanner.nextLine();
        gender = gender.toUpperCase();
        while (!isValidGender(gender)) {
            System.out.println(gender + " is invalid genderType. Please enter again");
            gender = getString("Enter your gender again: ");
        }
        return gender;
    }

    
    public String getDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.println(prompt);
            String userInput = scanner.nextLine();
            try {
                LocalDate date = LocalDate.parse(userInput, formatter); // Parse as LocalDate
                return date.format(formatter); // Return formatted date
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
    }



    public String getValidBloodType(String prompt) {
        String[] validBloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().toUpperCase();
            for (String validType : validBloodTypes) {
                if (input.equals(validType)) {
                    return input;
                }
            }
            System.out.println("Invalid blood type. Please enter a valid blood type (e.g., A+, O-, etc.).");
        }
    }

    public boolean isValidGender(String gender) {
        String genderRegex = "^[MF]$";
        return gender.toUpperCase().matches(genderRegex);
    }

    public static String getString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}

