import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to represent a therapist for TherAppy application.
 */
public class Therapist {

  /**
   * Unique ID number for this therapist.
   */
  private int therapistID;
  /**
   * Last name of this therapist.
   */
  private String lastName;
  /**
   * First name of this therapist.
   */
  private String firstName;
  /**
   * Gender of this therapist. F: female, M: male, N: non-binary.
   */
  private char gender;    // F, M, N
  /**
   * Date of birth for this therapist.
   */
  private Date dob;
  /**
   * Zip code for the location of this therapist's practice.
   */
  private String zipCode;
  /**
   * Cost-per-session that this therapist charges.
   */
  private int costPerSession;
  /**
   * Therapy style of this therapist.
   */
  private String style;
  /**
   * Degree qualification this therapist holds.
   */
  private String qualification;
  /**
   * List of insurance providers accepted by this therapist.
   */
  private List<String> insurance = new ArrayList<>(); //???????

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
   * @param insurance     list of insurance providers accepted
   */
  public Therapist(int ID, String lastName, String firstName, char gender, Date dob, String zipCode,
                   int cost, String style, String qualification, List<String> insurance) {
    this.therapistID = ID;
    this.lastName = lastName;
    this.firstName = firstName;
    this.gender = gender;
    this.dob = dob;
    this.zipCode = zipCode;
    this.costPerSession = cost;
    this.style = style;
    this.qualification = qualification;
    this.insurance = insurance;
  }

  @Override
  public String toString() {
    return "Therapist {\n"
            + "ID: " + this.therapistID + "\n"
            + "Name: " + this.firstName + " " + this.lastName + "\n"
            + "Gender: " + this.gender + "\n"
            + "DOB: " + this.dob + "\n"
            + "Zip code: " + this.zipCode + "\n"
            + "Cost: " + this.costPerSession + "\n"
            + "Style: " + this.style + "\n"
            + "Degree: " + this.qualification + "\n"
            + "}";
  }

}
