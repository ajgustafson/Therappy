import java.util.Date;

/**
 * Class to represent a therapist for TherAppy application.
 */
public class Therapist {

  /**
   * Unique ID number for this therapist.
   */
  private int therapistID;
  /**
   * First name of this therapist.
   */
  private String firstName;
  /**
   * Last name of this therapist.
   */
  private String lastName;
  /**
   * Date of birth for this therapist.
   */
  private Date dob;
  /**
   * Gender of this therapist. F: female, M: male, N: non-binary.
   */
  private String gender;    // F, M, N
  /**
   * The email of this therapist
   */
  private String email;
  /**
   * The phone number of this therapist
   */
  private String phoneNumber;
  /**
   * Zip code for the location of this therapist's practice.
   */
  private String zipCode;
  /**
   * Cost-per-session that this therapist charges.
   */
  private float costPerSession;
  /**
   * Therapy style of this therapist.
   */
  private String style;
  /**
   * Degree qualification this therapist holds.
   */
  private String qualification;

  /**
   * Construct therapist object with given attributes.
   *
   * @param ID            ID number
   * @param lastName      last name
   * @param firstName     first name
   * @param gender        gender ('F', 'M', 'N')
   * @param dob           date of birth
   * @param zipCode       zip code for therapist's practice location
   * @param cost          cost per session charged
   * @param style         therapy style
   * @param qualification highest degree held
   */
  public Therapist(int ID, String firstName, String lastName, Date dob, String gender,
                   String email, String phoneNumber, String zipCode,
                   float cost, String style, String qualification) {
    this.therapistID = ID;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dob = dob;
    this.gender = gender;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.zipCode = zipCode;
    this.costPerSession = cost;
    this.style = style;
    this.qualification = qualification;
  }

  /**
   * Get the therapist ID
   *
   * @return the therapist's ID primary key
   */
  public int getID() {
    return therapistID;
  }

  /**
   * Get the therapist's zipcode
   *
   * @return the therapist's zipcode
   */
  public String getZipCode() {
    return zipCode;
  }

  @Override
  public String toString() {
    return "Therapist {\n"
            + "Name: " + this.firstName + " " + this.lastName + "\n"
            + "Gender: " + this.gender + "\n"
            + "DOB: " + this.dob + "\n"
            + "E-mail: " + this.email + "\n"
            + "Phone Number: " + this.phoneNumber + "\n"
            + "Zip code: " + this.zipCode + "\n"
            + "Cost: " + this.costPerSession + "\n"
            + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Therapist)) {
      return false;
    }
    Therapist other = (Therapist) o;
    return this.therapistID == other.therapistID;
  }
}
