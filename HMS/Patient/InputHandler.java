package HMS.Patient;

import java.util.Scanner;

public class InputHandler implements InputInterface {
    private final Scanner scanner;

    public InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    @Override
    public String getValidatedInput(String prompt, Validator validator) {
        String input;
        do {
            input = getInput(prompt);
            if (!validator.isValid(input)) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (!validator.isValid(input));
        return input;
    }
}