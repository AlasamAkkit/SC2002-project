package HMS.Patient;

public interface Validator {
    boolean isValid(String input);
}

class EmailValidator implements Validator {
    @Override
    public boolean isValid(String input) {
        return input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}

class NumericValidator implements Validator {
    @Override
    public boolean isValid(String input) {
        return input.matches("\\d+");
    }
}
