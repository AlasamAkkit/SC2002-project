package HMS.Utility;

/**
 * A validator that checks if a given input string is purely numeric.
 * This class implements the Validator interface and uses a regular expression to 
 * determine if the input consists only of digits.
 */
public class NumericValidator implements Validator {

    /**
     * Validates whether the provided string is numeric.
     * 
     * @param input The string to validate as numeric.
     * @return {@code true} if the input is numeric (consists only of digits), {@code false} otherwise.
     */
    @Override
    public boolean isValid(String input) {
        return input.matches("\\d+");
    }
}