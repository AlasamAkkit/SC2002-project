package HMS;

public class PersonalInformation {
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

    public PersonalInformation(String name, int age, String birthday, String phoneNumber, String address, String emailAddress, BloodType bloodType){
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.bloodType = bloodType;
    }

    public void getPersonalInformation(){
        System.out.printf("Name: %s", this.name);
        System.out.printf("Age: %d", this.age);
        System.out.printf("Birthday: %s", this.birthday);
        System.out.printf("Phone Number: %s", this.phoneNumber);
        System.out.printf("Address: %s", this.address);
        System.out.printf("Email Address: %s", this.emailAddress);
        System.out.printf("Blood Type: %s", this.bloodType);
    }

    public boolean updateEmailAddress(String oldEmail, String newEmail){
        if (this.emailAddress.equals(oldEmail)) {
            System.out.println("Current email address is wrong");
            return false; // Login successful
        }
        this.emailAddress = newEmail;
        return true; // Login failed
    }

    public void updateContactNumber(String newContact){
        this.phoneNumber = newContact;
    }
}
