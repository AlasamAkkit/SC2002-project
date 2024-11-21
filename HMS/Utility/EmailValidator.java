package HMS.Utility;

/**
 * A validator that checks if a given input string is a valid email address.
 * This class implements the Validator interface and utilizes a regular expression
 * to determine if the input matches the pattern of a standard email address.
 */
public class EmailValidator implements Validator {

    /**
     * Validates whether the provided string matches the email format.
     * 
     * @param input The string to validate as an email address.
     * @return {@code true} if the input matches the email format, {@code false} otherwise.
     */
    @Override
    public boolean isValid(String input) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return input.matches(emailRegex);
    }
}