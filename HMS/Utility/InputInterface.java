package HMS.Utility;

public interface InputInterface {
    String getInput(String prompt);
    String getValidatedInput(String prompt, Validator validator);
}
