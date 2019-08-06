import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Application interface that describes methods available to interact with the TherAppy database.
 */
public interface TherAppyAPI {

  /**
   * FIXME - this method updated by Jeff and Mukhi on 8/1 When a user fills out all the information
   * asked in the user-interface, this method inserts the majority of the entered information into
   * the appropriate tables in the database.  The user's answers to the last 5 questions of our
   * survey (related to style pref) are inserted via a separate method.
   *
   * @param user User to insert
   * @return the userID for this user; -1 if user already exists - TODO - TBD this may change
   */
  //TODO currently not returning anything. does it really need to return the userID?
  int insertUser(User user);

  /**
   * FIXME - this method updated by Jeff and Mukhi on 8/1 This method inserts a user's responses to
   * one of the 5 questions related to style pref into the database.
   *
   * @param user     User that responded to the question
   * @param choiceID ID of the choice to which this response correlates
   */
  void insertStylePrefResponse(User user, int choiceID);


  /**
   * FIXME - this method updated by Jeff and Mukhi on 8/1 This method populates (via a call to a
   * stored procedure) the user's style preference in the DB. This method should be called after the
   * user's responses to the 5 questions related to style pref have been inserted into the DB
   *
   * @param user User to update style pref for TODO -  add exception handling in case this method is
   *             called when all the needed data isn't present
   */
  void updateUserStyle(User user);

  /**
   * FIXME - this method updated by Jeff and Mukhi on 8/1 Method to insert a user's rating of a
   * therapist into the database
   *
   * @param user        User who rates the therapist
   * @param therapistID ID of the therapist that was rated
   * @param rating      rating the user gave to the therapist
   */
  void insertTherapistRating(User user, int therapistID, int rating);

  /**
   * Get a list of therapists that match a user.
   *
   * @param user user to match with therapists
   * @return list of therapists that match with given user
   */
  List<Therapist> getMatches(User user);

  /**
   * Get a filtered list of therapists by given user's zip-code
   *
   * @param user user to filter based on their zip-code
   * @return a list of therapists within the user's radius of 5 miles
   */
  List<Therapist> filterTherapistMatches(User user) throws IOException, SQLException;

  /**
   * Insert a user's rating for a given therapist into the database.
   *
   * @param user      user who rated given therapist
   * @param therapist therapist who the given user rated
   * @param rating    rating user gave therapist
   */
  void insertRating(User user, Therapist therapist, int rating);

  /**
   * Check the password for a given user.
   *
   * @param user     User to login with given password
   * @param password to authenticate
   */
  void checkPassword(User user, String password);

  /**
   * Delete user from the database.
   *
   * @param user to delete from database
   */
  void deleteUser(User user);
  
  /**
   * Set connection settings
   */
  void authenticate(String user, String password);

  /**
   * Close the connection when application finishes
   */
  void closeConnection();

}
