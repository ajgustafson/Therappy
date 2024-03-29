import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Application interface that describes methods available to interact with the TherAppy database.
 */
public interface TherAppyAPI {

  /**
   * When a user fills out all the information asked in the user-interface, this method inserts the
   * majority of the entered information into the appropriate tables in the database.  The user's
   * answers to the last 5 questions of our survey (related to style pref) are inserted via a
   * separate method.
   *
   * @param user User to insert
   **/
  void insertUser(User user);

  /**
   * This method inserts a user's responses to one of the 5 questions related to style pref into the
   * database.
   *
   * @param user     User that responded to the question
   * @param choiceID ID of the choice to which this response correlates
   */
  void insertStylePrefResponse(User user, int choiceID);

  /**
   * This method populates (via a call to a stored procedure) the user's style preference in the DB.
   * This method should be called after the user's responses to the 5 questions related to style
   * pref have been inserted into the DB
   *
   * @param user User to update style pref for
   */
  void updateUserStyle(User user);

  /**
   * Method to insert a user's rating of a therapist into the database
   *
   * @param username    User who rates the therapist
   * @param therapistID ID of the therapist that was rated
   * @param rating      rating the user gave to the therapist
   */
  void insertTherapistRating(String username, int therapistID, int rating);

  /**
   * Get a filtered list of therapists by given user's zip-code
   *
   * @param username user to filter based on their zip-code
   * @param insert   a boolean to choose whether to insert into the matches table
   * @return a list of therapists within the user's radius of 5 miles
   */
  List<Therapist> getMatches(String username, boolean insert) throws IOException, SQLException;


  /**
   * Check the password for a given user.
   *
   * @param username User to login with given password
   * @param password to authenticate
   * @return true if username and password match, false otherwise
   */
  boolean checkPassword(String username, String password) throws SQLException;

  /**
   * Delete user from the database.
   *
   * @param username of user to delete from database
   */
  void deleteUser(String username);

  /**
   * Get therapist ID from a therapist's first and last name
   *
   * @param firstName of therapist
   * @param lastName  of therapist
   * @return given therapist's ID number
   */
  int getTherapistID(String firstName, String lastName) throws SQLException;

  /**
   * Close the connection when application finishes
   */
  void closeConnection();

}
