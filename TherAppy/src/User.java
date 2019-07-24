import java.util.Date;

public class User {

  // private int userID;
  private String lastName;
  private String firstName;
  private String email;
  private char gender;  // 'F', 'M', 'N'
  private Date dob;
  private String zipCode;
  private String insurance;
  private String malady;
  private int maxDistance;
  private char prefGender;
  private int maxCost;
  private String prefQualification;
  private int needsInsurance;
  private int prefStyle;

  public User(String lastName, String firstName, String email, char gender, Date dob,
              String zipCode, String insurance, String malady, int maxDistance, char prefGender,
              int maxCost, String prefQualification, int needsInsurance, int prefStyle) {
    // this.userID = ID;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.gender = gender;
    this.dob = dob;
    this.zipCode = zipCode;
    this.insurance = insurance;
    this.malady = malady;
    this.maxDistance = maxDistance;
    this.prefGender = prefGender;
    this.maxCost = maxCost;
    this.prefQualification = prefQualification;
    this.needsInsurance = needsInsurance;
    this.prefStyle = prefStyle;
  }
  /*
  public int getUserID() {
    return userID;
  }
  */

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getEmail() {
    return email;
  }

  public char getGender() {
    return gender;
  }

  public Date getDob() {
    return dob;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getInsurance() {
    return insurance;
  }

  public String getMalady() {
    return malady;
  }

  public int getMaxDistance() {
    return maxDistance;
  }

  public char getPrefGender() {
    return prefGender;
  }

  public int getMaxCost() {
    return maxCost;
  }

  public String getPrefQualification() {
    return prefQualification;
  }

  public int getNeedsInsurance() {
    return needsInsurance;
  }

  public int getPrefStyle() { return prefStyle; }

  @Override
  public String toString() {
    return "User {\n"
            // + "ID: " + this.userID + "\n"
            + "Name: " + this.firstName + " " + this.lastName + "\n"
            + "Email: " + this.email + "\n"
            + "Gender: " + this.gender + "\n"
            + "DOB: " + this.dob + "\n"
            + "Zip code: " + this.zipCode + "\n"
            + "Insurance: " + this.insurance + "\n"
            + "Malady: " + this.malady + "\n"
            + "Preferred therapist distance: " + this.maxDistance + "\n"
            + "Preferred therapist gender: " + this.prefGender + "\n"
            + "Preferred therapist cost: " + this.maxCost + "\n"
            + "Preferred therapist qualification: " + this.prefQualification + "\n"
            + "Needs insurance: " + this.needsInsurance + "\n"
            + "}";
  }
}
