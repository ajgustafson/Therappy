import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import JDBC_utils.DBUtils;

public class API implements TherAppyAPI {

  private DBUtils dbutil;

  public API(DBUtils dbutil) {
    this.dbutil = dbutil;
  }


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
  @Override
  public int insertUser(User user) {

    String user_basics_sql = "call insert_user_basics('" + user.getFirstName() + "','" + user.getLastName() + "','" + user.getUsername() + "','" +
            user.getPassword() + "','" + user.getDob() + "','" + user.getGender() + "','" + user.getEmail() + "','" + user.getZipCode()
            + "'," + user.getMaxDistance() + ",'" + user.getPrefGender() + "'," + user.getMaxCost() + "," + user.getNeedsInsurance()
            + ")";

    String user_insurance_sql = "call insert_user_insurance('" + user.getEmail() + "','" + user.getInsurance() + "')";

    String user_qual_pref_sql = "call insert_user_qual_pref('" + user.getEmail() + "','" + user.getPrefQualification() + "')";

    dbutil.insertOneRecord(user_basics_sql);

    //Creates new insurance record if user record for insurance does not exist
    dbutil.insertOneRecord(user_insurance_sql);

    //If user qualifcation preference does not exist, this entry left as null in User table
    dbutil.insertOneRecord(user_qual_pref_sql);

    for (int i = 0; i < user.getMaladies().size(); i++) {
      String user_malady_sql = "call insert_user_malady('" + user.getEmail() + "','" + user.getMaladies().get(i) + "')";
      dbutil.insertOneRecord(user_malady_sql);
    }

    //Need to adjust if how this method returns
    return 1;
  }


  /**
   * FIXME - this method updated by Jeff and Mukhi on 8/1 This method inserts the user's responses
   * to one of the 5 questions related to style pref into the database.
   *
   * @param user     User that responded to the question
   * @param choiceID ID of the choice to which this response correlates
   */
  @Override
  public void insertStylePrefResponse(User user, int choiceID) {

    String user_response_sql = "call insert_response('" + user.getEmail() + "','" + choiceID + "')";

    dbutil.insertOneRecord(user_response_sql);

  }

  /**
   * FIXME - this method updated by Jeff and Mukhi on 8/1 This method populates (via a call to a
   * stored procedure) the user's style preference in the DB. This method should be called after the
   * user's responses to the 5 questions related to style pref have been inserted into the DB
   *
   * @param user User to update style pref for TODO -  add exception handling in case this method is
   *             called when all the needed data isn't present
   */
  @Override
  public void updateUserStyle(User user) {
    String user_update_style_sql = "call update_style('" + user.getEmail() + "')";
    dbutil.insertOneRecord(user_update_style_sql);

  }

  /**
   * FIXME - this method updated by Jeff and Mukhi on 8/1 Method to insert a user's rating of a
   * therapist into the database
   *
   * @param user        User who rates the therapist
   * @param therapistID ID of the therapist that was rated
   * @param rating      rating the user gave to the therapist
   */
  public void insertTherapistRating(User user, int therapistID, int rating) {
    String user_rates_therapist_sql = "call insert_therapist_rating('" + user.getEmail() +
            "'," + therapistID + "," + rating + ")";
    dbutil.insertOneRecord(user_rates_therapist_sql);
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

  public void authenticate(String user, String password) {

    dbutil = new DBUtils("jdbc:mysql://localhost:3306/therappy", user, password);
  }

  public void filterTherapistMatches(User user) throws IOException {

    InputStream in = new URL("https://www.zipcodeapi.com/rest/" +
            "hYoS9BQmhj6fALZMB01LzPmHXlwv6AKAua1gMD39RjY6UhdkDqhYg94a7VcVuYVk/" +
            "radius.csv/" + user.getZipCode() + "/5/mile").openStream();

    Scanner scanner = new Scanner(in);
    List<Integer> zips = new ArrayList<>();

    String line = scanner.nextLine();

    while (scanner.hasNext()) {
      line = scanner.nextLine();
      zips.add(Integer.parseInt(line.split(",")[0]));
    }

  }


  /**
   * Close the connection when application finishes
   */
  public void closeConnection() {
    dbutil.closeConnection();
  }
}
