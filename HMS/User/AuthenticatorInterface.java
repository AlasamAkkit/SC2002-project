package HMS.User;

public interface AuthenticatorInterface {
    User authenticate(String hospitalID, String role, String password);
}