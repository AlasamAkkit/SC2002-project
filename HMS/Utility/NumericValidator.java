package HMS.Utility;

public class NumericValidator implements Validator {
    @Override
    public boolean isValid(String input) {
        return input.matches("\\d+");
    }
}