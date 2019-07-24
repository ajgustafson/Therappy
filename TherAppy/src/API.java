import java.util.List;

import JDBC_utils.DBUtils;

public class API implements TherAppyAPI {

  private DBUtils dbutil;

  public API(DBUtils dbutil) {
    this.dbutil = dbutil;
  }

  /**
   * Insert a user into the database.
   *
   * @param user user to insert
   * @return the userID for this user; -1 if user already exists
   */
  @Override
  public int insertUser(User user) {
    // Our SQL query
    String sql = "INSERT INTO user (userName, email, pwd, gender, zipcode, dob, insurance_id, " +
            " malady, gender_pref, max_distance, max_cost, qualification_pref, need_insurance, " +
            "style_id) VALUES " +
            "('" + user.getFirstName() + " " + user.getLastName() + "','" +
            user.getEmail() + "','" + "testPassword" + "','" +
            user.getGender() + "','" + user.getZipCode() + "','" +
            user.getDob() + "','" + user.getInsurance() + "','" + user.getMalady() + "','" +
            user.getPrefGender() + "','" + user.getMaxDistance() + "','" +
            user.getMaxCost() + "','" + user.getPrefQualification() + "','" +
            user.getNeedsInsurance() + "','" + user.getPrefStyle() + "')";

    return dbutil.insertOneRecord(sql);
  }

  /**
   * Insert a user's response to one of the 5 question pairs into the database.
   *
   * @param user       user that responded to the question
   * @param questionID ID of the question to which this response correlates
   */
  @Override
  public void insertResponse(User user, int questionID) {

  }

  /**
   * Get a list of therapists that match a user.
   *
   * @param user user to match with therapists
   * @return list of therapists that match with given user
   */
  @Override
  public List<Therapist> getMatches(User user) {
    return null;
  }

  /**
   * Insert a user's rating for a given therapist into the database.
   *
   * @param user      user who rated given therapist
   * @param therapist therapist who the given user rated
   * @param rating    rating user gave therapist
   */
  @Override
  public void insertRating(User user, Therapist therapist, int rating) {

  }
}
