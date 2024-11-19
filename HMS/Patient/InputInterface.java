package HMS.Patient;

public interface InputInterface {
    String getInput(String prompt);
    String getValidatedInput(String prompt, Validator validator);
}
