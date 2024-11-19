package HMS.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Handles user input operations for the system, providing methods to retrieve and validate user input.
 * This class simplifies input tasks, including basic string inputs, validated data, and specific formats like phone numbers and dates.
 */
public class InputHandler implements InputInterface {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs an InputHandler object.
     */
    public InputHandler() {
    }

    /**
     * Retrieves a line of input from the user after displaying a prompt.
     *
     * @param prompt The prompt to display to the user.
     * @return The user input as a trimmed string.
     */
    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    /**
     * Retrieves validated input from the user, using a provided validator to ensure the input meets specific criteria.
     *
     * @param prompt The prompt to display to the user.
     * @param validator The validator to check the validity of the input.
     * @return The validated input.
     */
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

    /**
     * Retrieves a validated Singaporean phone number from the user.
     *
     * @param prompt The prompt to display to the user.
     * @return A valid phone number as per Singaporean standards.
     */
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

    /**
     * Retrieves a valid gender input from the user, ensuring it matches "M" or "F".
     *
     * @param prompt The prompt to display to the user.
     * @return A valid gender input.
     */
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

    /**
     * Retrieves a valid date input from the user, ensuring it is in the format "yyyy-MM-dd".
     *
     * @param prompt The prompt to display to the user.
     * @return A string representing the valid date.
     */
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

    /**
     * Retrieves a valid blood type from the user.
     *
     * @param prompt The prompt to display to the user.
     * @return A valid blood type as per standard ABO system.
     */
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

    /**
     * Validates the gender input against the accepted formats.
     *
     * @param gender The gender string to validate.
     * @return true if the gender is valid, false otherwise.
     */
    public boolean isValidGender(String gender) {
        String genderRegex = "^[MF]$";
        return gender.toUpperCase().matches(genderRegex);
    }

    /**
     * Retrieves a string input after displaying a prompt.
     *
     * @param prompt The prompt to display.
     * @return The string input from the user.
     */
    public static String getString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}

