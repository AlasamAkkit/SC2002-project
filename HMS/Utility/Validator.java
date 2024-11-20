package HMS.Utility;

/**
 * Defines a contract for validating string inputs based on specific criteria.
 */
public interface Validator {

    /**
     * Validates a given string input against a set of predefined rules.
     *
     * @param input The string to validate.
     * @return true if the input meets the validation criteria, false otherwise.
     */
    boolean isValid(String input);
}

/*class EmailValidator implements Validator {
    @Override
    public boolean isValid(String input) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return input.matches(emailRegex);
    }
}

/**
 * Validates if the provided string consists only of numeric characters.
 */
class NumericValidator implements Validator {

    /**
     * Validates that the string contains only digits.
     *
     * @param input The string to validate.
     * @return true if the string consists solely of numeric characters, false otherwise.
     */
    @Override
    public boolean isValid(String input) {
        return input.matches("\\d+");
    }
}*/

