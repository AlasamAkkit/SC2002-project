package HMS;

/**
 * Represents personal information for individuals within the system.
 * This class includes basic personal details and methods for accessing and updating these details.
 */
public class PersonalInformation {
    /**
     * Enum for BloodType to ensure valid blood type entries.
     */
    public enum BloodType{
        A_POSITIVE, A_NEGATIVE,
        B_POSITIVE, B_NEGATIVE,
        AB_POSITIVE, AB_NEGATIVE,
        O_POSITIVE, O_NEGATIVE
    }
    private String name;
    private int age;
    private String birthday;
    private String phoneNumber;
    private String address;
    private String emailAddress;
    private BloodType bloodType;

    /**
     * Constructs a new PersonalInformation instance with the provided details.
     *
     * @param name         The name of the individual.
     * @param age          The age of the individual.
     * @param birthday     The birthday of the individual in a string format.
     * @param phoneNumber  The phone number of the individual.
     * @param address      The home address of the individual.
     * @param emailAddress The email address of the individual.
     * @param bloodType    The blood type of the individual.
     */
    public PersonalInformation(String name, int age, String birthday, String phoneNumber, String address, String emailAddress, BloodType bloodType){
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
    }

    /**
     * Prints out the personal information of the individual.
     */
    public void getPersonalInformation(){
        System.out.printf("Name: %s", this.name);
        System.out.printf("Age: %d", this.age);
        System.out.printf("Birthday: %s", this.birthday);
        System.out.printf("Phone Number: %s", this.phoneNumber);
        System.out.printf("Address: %s", this.address);
        System.out.printf("Email Address: %s", this.emailAddress);
        System.out.printf("Blood Type: %s", this.bloodType);
    }

    /**
     * Updates the email address if the old email provided matches the current one.
     *
     * @param oldEmail The current email that needs verification.
     * @param newEmail The new email to update to.
     * @return true if the update is successful, false otherwise.
     */
    public boolean updateEmailAddress(String oldEmail, String newEmail){
        if (this.emailAddress.equals(oldEmail)) {
            System.out.println("Current email address is wrong");
            return false; // Login successful
        }
        this.emailAddress = newEmail;
        return true; // Login failed
    }

    /**
     * Updates the phone number of the individual.
     *
     * @param newContact The new phone number to update to.
     */
    public void updateContactNumber(String newContact){
        this.phoneNumber = newContact;
    }
}
