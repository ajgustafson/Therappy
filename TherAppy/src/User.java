import java.util.Date;
import java.util.List;

public class User {

  // private int userID;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String dob;
  private String gender;  // 'Female', 'Male'
  private String email;
  private String zipCode;
  private String insurance;
  private List<String> maladies;
  private int maxDistance;
  private String prefGender;
  private int maxCost;
  private String prefQualification;
  private int needsInsurance;
  private int prefStyle;

  public User(String lastName, String firstName, String username, String password, String email,
              String gender, String dob, String zipCode, String insurance, List<String> maladies,
              int maxDistance, String prefGender, int maxCost, String prefQualification,
              int needsInsurance, int prefStyle) {
    // this.userID = ID;
    this.lastName = lastName;
    this.firstName = firstName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.gender = gender;
    this.dob = dob;
    this.zipCode = zipCode;
    this.insurance = insurance;
    this.maladies = maladies;
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

  public String getUsername() { return username; }

  public String getPassword() { return password; }

  public String getEmail() {
    return email;
  }

  public String getGender() {
    return gender;
  }

  public String getDob() {
    return dob;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getInsurance() {
    return insurance;
  }

  public List<String> getMaladies() {
    return maladies;
  }

  public int getMaxDistance() {
    return maxDistance;
  }

  public String getPrefGender() {
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

  public int getPrefStyle() {
    return prefStyle;
  }

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
            + "Maladies: " + this.maladies.toString() + "\n"
            + "Preferred therapist distance: " + this.maxDistance + "\n"
            + "Preferred therapist gender: " + this.prefGender + "\n"
            + "Preferred therapist cost: " + this.maxCost + "\n"
            + "Preferred therapist qualification: " + this.prefQualification + "\n"
            + "Needs insurance: " + this.needsInsurance + "\n"
            + "}";
  }
}
