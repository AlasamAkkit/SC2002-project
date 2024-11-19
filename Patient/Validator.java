package HMS.Patient;

public interface Validator {
    boolean isValid(String input);
}

class EmailValidator implements Validator {
    @Override
    public boolean isValid(String input) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return input.matches(emailRegex);
    }
}

class NumericValidator implements Validator {
    @Override
    public boolean isValid(String input) {
        return input.matches("\\d+");
    }
}

