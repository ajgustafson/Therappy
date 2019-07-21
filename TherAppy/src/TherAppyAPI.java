import java.util.List;

/**
 * Application interface that describes methods available to interact with the TherAppy
 * database.
 */
public interface TherAppyAPI {

  /**
   * Insert a user into the database.
   * @param user user to insert
   * @return the userID for this user; -1 if user already exists
   */
  //TODO does it really need to return the userID?
  int insertUser(User user);

  /**
   * Insert a user's response to one of the 5 question pairs into the database.
   * @param user user that responded to the question
   * @param questionID ID of the question to which this response correlates
   */
  void insertResponse(User user, int questionID);

  /**
   * Get a list of therapists that match a user.
   * @param user user to match with therapists
   * @return list of therapists that match with given user
   */
  List<Therapist> getMatches(User user);

  /**
   * Insert a user's rating for a given therapist into the database.
   * @param user user who rated given therapist
   * @param therapist therapist who the given user rated
   * @param rating rating user gave therapist
   */
  void insertRating(User user, Therapist therapist, int rating);

}
