package helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This Helper class provides various helper methods for input reading, validation,
 * formatting, and general utilities for console applications.
 * Includes methods for reading and validating user input, formatting dates, and more.
 */
public class Helper {
    /**
     * Shared Scanner instance for user input.
     */
    public static final Scanner sc = new Scanner(System.in);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Helper() {
        // Private constructor to prevent instantiation
    }


    /**
     * Reads a LocalDateTime input from the user in the format "yyyy-MM-dd HH:mm".
     * Continues to prompt until a valid date and time are entered.
     *
     * @return LocalDateTime object representing the entered date and time.
     */
    public static LocalDateTime readDate() {
        LocalDateTime date = null;
        boolean valid = false;
        while (!valid) {
            String input = Helper.readString();

            try {
                // Parse the input into LocalDateTime
                date = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                valid = true; // Input is valid, exit the loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD HH:MM.");
            }
        }
        return date;
    }

    /**
     * Reads a double from the user with a default prompt message.
     * Continues to prompt until a valid double is entered.
     *
     * @return The double entered by the user.
     */
    public static double readDouble() {
        while (true) {
            try {
                System.out.print("Enter a double: ");
                double userInput = sc.nextDouble();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input. Please enter a decimal number.");
            }
        }
    }

    /**
     * Reads a string input from the user with a given prompt.
     *
     * @param prompt The prompt message to display to the user.
     * @return The string entered by the user.
     */
    public static String readString(String prompt) {
        System.out.println(prompt);
        return sc.nextLine();
    }

    /**
     * Reads a string input from the user without a prompt.
     *
     * @return The string entered by the user.
     */
    public static String readString() {
        return sc.nextLine();
    }

    /**
     * Reads a valid email address from the user with a prompt.
     * Continues to prompt until a valid email format is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return A valid email address entered by the user.
     */
    public static String readEmail(String prompt) {
        System.out.println(prompt);
        String email = sc.nextLine();
        while (!Helper.isValidEmail(email)) {
            System.out.println("The email '" + email + "' is invalid. Please enter a new email:");
            email = Helper.readString("Enter a new email: ");
        }
        return email;
    }

    /**
     * Reads and validates a gender input from the user with a prompt.
     * Continues to prompt until a valid gender ("M" or "F") is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return The valid gender entered by the user.
     */
    public static String readGender(String prompt) {
        System.out.println(prompt);
        String gender = sc.nextLine();
        while (!Helper.isValidGender(gender)) {
            System.out.println(gender + " is invalid genderType. Please enter again");
            gender = Helper.readString("Enter your gender again: ");
        }
        return gender;
    }

    /**
     * Prompts the user to enter a valid blood type until the input is valid.
     * Acceptable blood types are: "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-".
     *
     * @param prompt The prompt message to display to the user.
     * @return The valid blood type entered by the user.
     */
    public static String readValidBloodType(String prompt) {
        String[] validBloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().toUpperCase();
            for (String validType : validBloodTypes) {
                if (input.equals(validType)) {
                    return input;
                }
            }
            System.out.println("Invalid blood type. Please enter a valid blood type (e.g., A+, O-, etc.).");
        }
    }

    /**
     * Prompts the user to enter a valid phone number until the input is valid.
     * A valid phone number must have exactly 8 digits and start with either 8 or 9.
     *
     * @param prompt The prompt message to display to the user.
     * @return The valid phone number entered by the user.
     */
    public static String readValidPhoneNumber(String prompt) {
        String phoneRegex = "^[89]\\d{7}$";
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            if (input.matches(phoneRegex)) {
                return input;
            }
            System.out.println("Invalid phone number. Please enter an 8-digit phone number starting with 8 or 9.");
        }
    }

    /**
     * Prompts the user for a confirmation with a yes/no question.
     *
     * @param message The confirmation message to display.
     * @return true if the user confirms with "yes"; false otherwise.
     */
    public static boolean promptConfirmation(String message) {
        System.out.println("Are you sure you want to " + message + "? (yes/no)");
        String userInput = sc.nextLine().trim().toLowerCase();
        return userInput.equals("yes");
    }

    /**
     * Generates a unique ID for a new entry in a HashMap.
     *
     * @param database The HashMap to generate a unique ID for.
     * @param <K>      The type of the HashMap's keys.
     * @param <V>      The type of the HashMap's values.
     * @return A unique ID based on the current size of the database.
     */
    public static <K, V> int generateUniqueId(HashMap<K, V> database) {
        return database.isEmpty() ? 1 : database.size() + 1;
    }

    /**
     * Returns the current date and time in "yyyy-MM-dd HH:mm" format.
     *
     * @return The current date and time as a formatted string.
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /**
     * Prompts the user to press Enter to continue.
     */
    public static void pressEnterToContinue() {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }

    /**
     * Clears the console screen (may not work in all environments).
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Reads a date input from the user in the specified format and returns a
     * LocalDateTime object.
     * Continues to prompt until a valid date is entered.
     * 
     * @param prompt The prompt message to display to the user.
     * @return LocalDateTime object representing the entered date and time.
     */
    public static LocalDateTime readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.println(prompt);
            String userInput = sc.nextLine();
            try {
                return LocalDateTime.parse(userInput + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME); // Time set
                                                                                                            // to
                                                                                                            // midnight
                                                                                                            // by
                                                                                                            // default
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
    }

    /**
     * Retrieves a field from an array by index, returning null if the index is out of bounds or empty.
     *
     * @param fields The array of fields.
     * @param index  The index to retrieve.
     * @return The field at the specified index, or null if out of bounds or empty.
     */
    public static String getFieldOrNull(String[] fields, int index) {
        return index < fields.length && !fields[index].isEmpty() ? fields[index] : null;
    }

    /**
     * Parses a LocalDateTime from an array by index, returning null if parsing fails or the field is empty.
     *
     * @param fields The array of fields.
     * @param index  The index to parse.
     * @return The LocalDateTime object or null if parsing fails or field is empty.
     */
    public static LocalDateTime parseDateTimeOrNull(String[] fields, int index) {
        try {
            return index < fields.length && !fields[index].isEmpty() ? LocalDateTime.parse(fields[index]) : null;
        } catch (Exception e) {
            return null; // return null if parsing fails or field is empty
        }
    }
    /**
     * Parses a Double from an array by index.
     * Returns null if parsing fails or the field is empty.
     *
     * @param fields The array of fields.
     * @param index  The index to parse.
     * @return The Double value or null if parsing fails or the field is empty.
     */
    public static Double parseDoubleOrNull(String[] fields, int index) {
        try {
            return index < fields.length && !fields[index].isEmpty() ? Double.parseDouble(fields[index]) : null;
        } catch (Exception e) {
            return null; // return null if parsing fails or field is empty
        }
    }

    /**
     * Checks if the provided email matches a valid email format.
     *
     * @param email The email address to validate.
     * @return True if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    /**
     * Checks if the provided gender input is valid (either "M" or "F").
     *
     * @param gender The gender input to validate.
     * @return True if the gender is "M" or "F", false otherwise.
     */
    public static boolean isValidGender(String gender) {
        String genderRegex = "^[MF]$";
        return gender.toUpperCase().matches(genderRegex);
    }
}
