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

/**
 * This class is an API for the Therappy project. It allows you to insert information into the
 * database as well as filter therapist matches and delete a user among other operations.
 */
public class API implements TherAppyAPI {

  /**
   * JDBC utility
   */
  private DBUtils dbutil;

  /**
   * Construct an API object with given database utilities.
   *
   * @param dbutil JDBC utility to connect Java application with MySQL database
   */
  public API(DBUtils dbutil) {
    this.dbutil = dbutil;
  }


  /**
   * When a user fills out all the information asked in the user-interface, this method inserts the
   * majority of the entered information into the appropriate tables in the database.  The user's
   * answers to the last 5 questions of our survey (related to style pref) are inserted via a
   * separate method.
   *
   * @param user User to insert
   */
  @Override
  public void insertUser(User user) {

    // Building the stored procedure call query
    // to insert the user into the database
    String user_basics_sql = "call insert_user_basics('" + user.getFirstName() + "','"
            + user.getLastName() + "','" + user.getUsername() + "','" + user.getPassword() + "','"
            + user.getDob() + "','" + user.getGender() + "','" + user.getEmail() + "','"
            + user.getZipCode() + "'," + user.getMaxDistance() + ",'" + user.getPrefGender() + "',"
            + user.getMaxCost() + "," + user.getNeedsInsurance() + ")";

    // query to insert insurance information about the user
    String user_insurance_sql = "call insert_user_insurance('" + user.getEmail() + "','"
            + user.getInsurance() + "')";

    // query to insert the user's qualification preference (PhD)
    String user_qual_pref_sql = "call insert_user_qual_pref('" + user.getEmail() + "','"
            + user.getPrefQualification() + "')";

    // Inserts the user into the database
    dbutil.insertOneRecord(user_basics_sql);

    //Creates new insurance record if user record for insurance does not exist
    dbutil.insertOneRecord(user_insurance_sql);

    //If user qualifcation preference does not exist, this entry left as null in User table
    dbutil.insertOneRecord(user_qual_pref_sql);

    //Adds the users maladies to the Database
    for (int i = 0; i < user.getMaladies().size(); i++) {
      String user_malady_sql = "call insert_user_malady('" + user.getEmail() + "','"
              + user.getMaladies().get(i) + "')";
      dbutil.insertOneRecord(user_malady_sql);
    }
  }

  /**
   * This method inserts the user's responses to one of the 5 questions related to style pref into
   * the database.
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
   * This method populates (via a call to a stored procedure) the user's style preference in the DB.
   * This method should be called after the user's responses to the 5 questions related to style
   * pref have been inserted into the DB
   *
   * @param user User to update style pref for
   */
  @Override
  public void updateUserStyle(User user) {
    String user_update_style_sql = "call update_style('" + user.getEmail() + "')";
    dbutil.insertOneRecord(user_update_style_sql);

  }


  /**
   * Method to insert a user's rating of a therapist into the database
   *
   * @param username    User who rates the therapist
   * @param therapistID ID of the therapist that was rated
   * @param rating      rating the user gave to the therapist
   */
  @Override
  public void insertTherapistRating(String username, int therapistID, int rating) {
    String email = null;
    try {
      email = getUserEmail(username);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    String user_rates_therapist_sql = "call insert_therapist_rating('" + email +
            "'," + therapistID + "," + rating + ")";
    dbutil.insertOneRecord(user_rates_therapist_sql);
  }


  /**
   * Check the password for a given user.
   *
   * @param username User to login with given password
   * @param password to authenticate
   * @return true if username and password match, false otherwise
   */
  @Override
  public boolean checkPassword(String username, String password) throws SQLException {

    // Getting connection to the database and preparing to call a stored procedure
    // to validate that the user has typed the right credentials
    Connection connection = dbutil.getConnection();
    CallableStatement stmt = connection.prepareCall("{call validate_user(?, ?)}");

    // set the parameters
    stmt.setString(1, username);
    stmt.setString(2, password);
    stmt.execute();

    // get the results of the query
    ResultSet rs = stmt.getResultSet();
    String result = "";
    while (rs.next() != false) {
      result = rs.getString("result");
    }

    // check the result
    if (result.equals("1")) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Delete user from the database.
   *
   * @param username of user to delete from database
   */
  @Override
  public void deleteUser(String username) {
    String delete_sql = "call delete_user('" + username + "')";
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
  public int getTherapistID(String firstName, String lastName) throws SQLException {
    // Connect to the database
    Connection connection = dbutil.getConnection();
    CallableStatement stmt = connection.prepareCall("{call get_therapist_id(?, ?)}");

    // Set the parameters
    stmt.setString(1, firstName);
    stmt.setString(2, lastName);
    stmt.execute();

    // Get the results of the stored procedure
    ResultSet rs = stmt.getResultSet();
    String result = "";
    while (rs.next() != false) {
      result = rs.getString("therapist_id_var");
    }

    // return the ID
    return Integer.parseInt(result);
  }

  /**
   * Get a list of therapist matches for given user.
   *
   * @param username user to match with therapists
   * @return List of therapists that match with given user
   * @throws IOException  //TODO when does it throw this?
   * @throws SQLException if an error occurs when interacting with the database
   */
  public List<Therapist> getMatches(String username, boolean insert)
          throws IOException, SQLException {

    // A list of therapists without zipcode filtering
    List<Therapist> therapists = new LinkedList<>();

    // A list of therapists WITH zipcode filtering
    List<Therapist> therapistsFilteredByZipcode = new ArrayList<>();

    // Get the zipcode, email, and distance from the database
    String zipCode = getUserZipCode(username);
    String email = getUserEmail(username);
    String distance = getUserDistance(username);

    // Get the zip codes from the URL
    InputStream in = new URL("https://www.zipcodeapi.com/rest/" +
            "kOG5lF8QkczEUsfJLvwreaPQLLMAo5Wsp0orHvbfacQOgqRoafnwMP5mZvgQBU70/" +
            "radius.csv/" + zipCode + "/" + distance + "/mile").openStream();

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

    // Get the connection to the database and prepare to call the stored procedure
    // to find matching therapists
    Connection connection = dbutil.getConnection();
    CallableStatement stmt = connection.prepareCall("{call findMatchingTherapists(?)}");

    // set the parameter and execute
    stmt.setString(1, email);
    stmt.execute();

    ResultSet rs = stmt.getResultSet();

    List<Integer> matchScores = new ArrayList<>();

    while (rs.next() != false) {
      therapists.add(new Therapist(rs.getInt("therapist_id"),
              rs.getString("first_name"), rs.getString("last_name"),
              rs.getDate("dob"), rs.getString("gender"),
              rs.getString("email"), rs.getString("phone_number"),
              rs.getString("zipcode"), rs.getFloat("cost_per_session"),
              rs.getString("style_id"), rs.getString("qualification_id")));

      matchScores.add(rs.getInt("match_score"));
    }

    for (Therapist therapist : therapists) {
      if (zips.contains(Integer.parseInt(therapist.getZipCode()))) {
        therapistsFilteredByZipcode.add(therapist);
      }
    }

    if (insert) {
      insertTherapistMatches(username, matchScores, therapists, therapistsFilteredByZipcode);
    }

    //TODO close connection?

    if (therapistsFilteredByZipcode.size() > 0) {
      return therapistsFilteredByZipcode;
    }
    return therapists;
  }

  private void insertTherapistMatches(String username, List<Integer> matchScores,
                                      List<Therapist> therapists,
                                      List<Therapist> filteredTherapists) throws SQLException {
    List<Therapist> matches;

    Connection connection = dbutil.getConnection();
    CallableStatement stmt = connection.prepareCall("{call get_user_id(?)}");
    stmt.setString(1, username);
    stmt.execute();

    ResultSet rs = stmt.getResultSet();


    rs.next();
    int userID = rs.getInt("user_id");

    if (filteredTherapists.size() > 0) {
      matches = filteredTherapists;
    } else {
      matches = therapists;
    }


    for (int i = 0; i < 5; i++) {
      int index;
      Therapist therapist = matches.get(i);
      index = therapists.indexOf(therapist);

      String insert_sql = "call insert_matches(" + userID + ", " + therapist.getID() +
              ", " + matchScores.get(index) + ")";
      dbutil.insertOneRecord(insert_sql);
    }
  }

  /**
   * Helper method to get a user's zip code from the database based on their username.
   *
   * @param username of user to fetch zip code
   * @return zip code of user with given username
   * @throws SQLException if an error occurs when interacting with the database
   */
  private String getUserZipCode(String username) throws SQLException {
    Connection connection = dbutil.getConnection();

    CallableStatement stmt = connection.prepareCall("{call get_user_zipcode(?)}");

    stmt.setString(1, username);
    stmt.execute();

    ResultSet rs = stmt.getResultSet();
    String zipCode = "";
    while (rs.next() != false) {
      zipCode = rs.getString("zipcode");
    }
    return zipCode;
  }

  /**
   * Helper method to get a user's email address from the database.
   *
   * @param username of user to get email address
   * @return email address for user with given username
   * @throws SQLException if an error occurs when interacting with the database.
   */
  private String getUserEmail(String username) throws SQLException {
    Connection connection = dbutil.getConnection();

    CallableStatement stmt = connection.prepareCall("{call get_user_email(?)}");

    stmt.setString(1, username);
    stmt.execute();

    ResultSet rs = stmt.getResultSet();
    String email = "";
    while (rs.next() != false) {
      email = rs.getString("email");
    }
    return email;
  }

  private String getUserDistance(String username) throws SQLException {
    Connection connection = dbutil.getConnection();

    CallableStatement stmt = connection.prepareCall("{call get_user_max_distance(?)}");

    stmt.setString(1, username);
    stmt.execute();

    ResultSet rs = stmt.getResultSet();
    String distance = "";
    while (rs.next() != false) {
      distance = rs.getString("max_distance");
    }
    return distance;
  }


  /**
   * Close the connection when application finishes
   */
  @Override
  public void closeConnection() {
    dbutil.closeConnection();
  }
}
