package HMS.Utility;

public class EmailValidator implements Validator {
    @Override
    public boolean isValid(String input) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return input.matches(emailRegex);
    }
}