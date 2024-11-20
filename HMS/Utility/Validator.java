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


