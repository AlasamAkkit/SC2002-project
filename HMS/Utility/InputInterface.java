package HMS.Utility;

/**
 * Defines methods for obtaining and validating user input.
 * This interface ensures that classes implementing it provide mechanisms to handle
 * standard and validated inputs according to specific requirements.
 */
public interface InputInterface {

    /**
     * Retrieves a line of input from the user after displaying a specified prompt.
     * 
     * @param prompt A string prompt displayed to the user indicating what input is expected.
     * @return The user input as a string.
     */
    String getInput(String prompt);

    /**
     * Retrieves and validates user input based on a specific validation logic provided by the validator.
     * This method continuously prompts the user until valid input is received.
     * 
     * @param prompt A string prompt displayed to the user indicating what input is expected.
     * @param validator A validator object used to check the validity of the user's input.
     * @return The validated input string that meets the criteria specified by the validator.
     */
    String getValidatedInput(String prompt, Validator validator);
}
