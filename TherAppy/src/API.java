import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
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
  @Override
  public void insertTherapistRating(User user, int therapistID, int rating) {
    String user_rates_therapist_sql = "call insert_therapist_rating('" + user.getEmail() +
            "'," + therapistID + "," + rating + ")";
    dbutil.insertOneRecord(user_rates_therapist_sql);
  }


  /**
   * Check the password for a given user.
   *
   * @param username  User to login with given password
   * @param password to authenticate
   * @return true if username and password match, false otherwise
   */
  @Override
  public boolean checkPassword(String username, String password) {
    //TODO implement this
    return false;
  }


  /**
   * Delete user from the database.
   *
   * @param username of user to delete from database
   */
  @Override
  public void deleteUser(String username) {
     String delete_sql = "call delete_user('" + username + ")";
    dbutil.insertOneRecord(delete_sql);
  }


  /**
   * Get therapist ID from a therapist's first and last name
   *
   * @param firstName of therapist
   * @param lastName  of therapist
   * @return given therapist's ID number
   */
  @Override
  public int getTherapistID(String firstName, String lastName) {
    // TODO implement this
    return 0;
  }

  @Override
  public void authenticate(String user, String password) {

    dbutil = new DBUtils("jdbc:mysql://localhost:3306/therappy", user, password);
  }


  public List<Therapist> getMatches(User user) throws IOException, SQLException {

    List<Therapist> therapists = new LinkedList<>();
    List<Therapist> filteredTherapists = new ArrayList<>();

    // Get the zip codes from the URL
    InputStream in = new URL("https://www.zipcodeapi.com/rest/" +
            "WnU4UL8yVFW53jd6dlYQZahLb7oWBo060YiW8o6w11pHDAd4L5dcRXEiDF5ZHkqv/" +
            "radius.csv/" + user.getZipCode() + "/5/mile").openStream();

    // prepare to parse the data
    Scanner scanner = new Scanner(in);
    List<Integer> zips = new ArrayList<>();


    // ignore the header line
    String line = scanner.nextLine();

    // add each zip integer to the zips list.
    while (scanner.hasNext()) {
      line = scanner.nextLine();
      zips.add(Integer.parseInt(line.split(",")[0]));
    }


    Connection connection = dbutil.getConnection();

    CallableStatement stmt = connection.prepareCall("{call findMatchingTherapists(?)}");

    stmt.setString(1, user.getEmail());
    stmt.execute();

    ResultSet rs = stmt.getResultSet();


    while (rs.next() != false) {
      therapists.add(new Therapist(rs.getInt("therapist_id"),
              rs.getString("first_name"), rs.getString("last_name"),
              rs.getDate("dob"), rs.getString("gender"),
              rs.getString("email"), rs.getString("phone_number"),
              rs.getString("zipcode"), rs.getFloat("cost_per_session"),
              rs.getString("style_id"), rs.getString("qualification_id")));
    }

    for (Therapist therapist : therapists) {
      if (zips.contains(Integer.parseInt(therapist.getZipCode()))) {
        filteredTherapists.add(therapist);
      }
    }

    if (filteredTherapists.size() == 0) {
      return therapists;
    }
    return filteredTherapists;
  }


  /**
   * Close the connection when application finishes
   */
  @Override
  public void closeConnection() {
    dbutil.closeConnection();
  }
}
