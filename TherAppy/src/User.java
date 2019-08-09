import java.util.List;

/**
 * Class to represent a TherAppy user.
 */
public class User {
  /**
   * User's first name.
   */
  private String firstName;
  /**
   * User's last name.
   */
  private String lastName;
  /**
   * User's username.
   */
  private String username;
  /**
   * User's password.
   */
  private String password;
  /**
   * User's date of birth (YYYY-MM-DD).
   */
  private String dob;
  /**
   * User's gender (F/M).
   */
  private String gender;
  /**
   * User's email.
   */
  private String email;
  /**
   * User's zip code.
   */
  private String zipCode;
  /**
   * Name of user's insurance company.
   */
  private String insurance;
  /**
   * List of mental health maladies that the user self-identified.
   */
  private List<String> maladies;
  /**
   * The maximum distance the user is willing to travel to meet with a therapist.
   */
  private int maxDistance;
  /**
   * User's preferred therapist gender (F/M).
   */
  private String prefGender;
  /**
   * The maximum cost the user is willing to pay per therapy session.
   */
  private int maxCost;
  /**
   * User's preferred therapist qualifications (MA/PhD/PsyD).
   */
  private String prefQualification;
  /**
   * Identifies if the user requires a therapist to accept insurance (Y/N).
   */
  private int needsInsurance;
  /**
   * User's preferred therapy style as identified through a questionnaire.
   */
  private int prefStyle;

  /**
   * TherAppy user.
   *
   * @param lastName          of user
   * @param firstName         of user
   * @param username          of user
   * @param password          of user
   * @param email             of user
   * @param gender            of user (F/M)
   * @param dob               birthdate of user (YYYY-MM-DD)
   * @param zipCode           of user
   * @param insurance         company of user
   * @param maladies          identified by user
   * @param maxDistance       user is willing to travel to meet with therapist
   * @param prefGender        of user's therapist
   * @param maxCost           user is willing to pay for each therapy session
   * @param prefQualification of user's therapist (MA/PhD/PsyD)
   * @param needsInsurance    1 if user requires therapist to accept insurance, 0 otherwise
   * @param prefStyle         of therapy for user
   */
  public User(String lastName, String firstName, String username, String password, String email,
              String gender, String dob, String zipCode, String insurance, List<String> maladies,
              int maxDistance, String prefGender, int maxCost, String prefQualification,
              int needsInsurance, int prefStyle) {
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

  /**
   * get first name
   *
   * @return first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * get last name
   *
   * @return last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * get username
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * get the user's password
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * get the date of birth
   *
   * @return date of birth
   */
  public String getDob() {
    return dob;
  }

  /**
   * get the user's gender
   *
   * @return the user's gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * get the user's email
   *
   * @return the user's email
   */
  public String getEmail() {
    return email;
  }

  /**
   * get the user's zipcode
   *
   * @return the user's zipcode
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * Get the user's insurance
   *
   * @return the user's insurance
   */
  public String getInsurance() {
    return insurance;
  }

  /**
   * Get the user's list of maladies
   *
   * @return a list of the user's maladies
   */
  public List<String> getMaladies() {
    return maladies;
  }

  /**
   * Get the user's max distance
   *
   * @return the user's max distance
   */
  public int getMaxDistance() {
    return maxDistance;
  }

  /**
   * Get the user's therapist gender preference
   *
   * @return the user's therapist gender preference
   */
  public String getPrefGender() {
    return prefGender;
  }

  /**
   * get the user's max cost
   *
   * @return the user's max cost
   */
  public int getMaxCost() {
    return maxCost;
  }

  /**
   * get the user's preferred qualification
   *
   * @return the user's preferred qualification
   */
  public String getPrefQualification() {
    return prefQualification;
  }

  /**
   * get whether the user needs insurance
   *
   * @return 0 if the user doesn't and 1 otherwise
   */
  public int getNeedsInsurance() {
    return needsInsurance;
  }

  @Override
  public String toString() {
    return "User {\n"
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
            + "Preferred style: " + this.prefStyle + "\n"
            + "}";
  }
}
