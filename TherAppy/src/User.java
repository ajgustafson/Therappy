import java.util.Date;

public class User {

  private int userID;
  private String lastName;
  private String firstName;
  private String email;
  private char gender;  // 'F', 'M', 'N'
  private Date dob;
  private String zipCode;
  private String insurance;
  private int maxDistance;
  private char prefGender;
  private int maxCost;
  private String prefQualification;
  private boolean needsInsurance;

  public User(int ID, String lastName, String firstName, String email, char gender, Date dob,
              String zipCode, String insurance, int maxDistance, char prefGender, int maxCost,
              String prefQualification, boolean needsInsurance) {
    this.userID = ID;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.gender = gender;
    this.dob = dob;
    this.zipCode = zipCode;
    this.insurance = insurance;
    this.maxDistance = maxDistance;
    this.prefGender = prefGender;
    this.maxCost = maxCost;
    this.prefQualification = prefQualification;
    this.needsInsurance = needsInsurance;
  }

  @Override
  public String toString() {
    return "User {\n"
            + "ID: " + this.userID + "\n"
            + "Name: " + this.firstName + " " + this.lastName + "\n"
            + "Email: " + this.email + "\n"
            + "Gender: " + this.gender + "\n"
            + "DOB: " + this.dob + "\n"
            + "Zip code: " + this.zipCode + "\n"
            + "Insurance: " + this.insurance + "\n"
            + "Preferred therapist distance: " + this.maxDistance + "\n"
            + "Preferred therapist gender: " + this.prefGender + "\n"
            + "Preferred therapist cost: " + this.maxCost + "\n"
            + "Preferred therapist qualification: " + this.prefQualification + "\n"
            + "Needs insurance: " + this.needsInsurance + "\n"
            + "}";
  }
}
