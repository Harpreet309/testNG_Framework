package models;

public class FormData {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String mobileNumber;
    private final String subjects;
    private final String currentAddress;
    private final String state = "NCR";
    private final String city = "Delhi";
    private final String gender = "Male";
    private final String hobbies = "Sports";
    private final String dateOfBirth = "17 November,1994";
    private final String successMessage = "Thanks for submitting the form";

    // Constructor
    public FormData(String firstName, String lastName, String email, String mobileNumber,
                    String subjects, String currentAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.subjects = subjects;
        this.currentAddress = currentAddress;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getMobileNumber() { return mobileNumber; }
    public String getSubjects() { return subjects; }
    public String getCurrentAddress() { return currentAddress; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getGender() { return gender; }
    public String getHobbies() { return hobbies; }
    public String getSuccessMessage() { return successMessage; }
    public String getDateOfBirth() { return dateOfBirth; }

    @Override
    public String toString() {
        return "FormData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", subjects='" + subjects + '\'' +
                ", currentAddress='" + currentAddress + '\'' +
                '}';
    }
}