import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import JDBC_utils.DBUtils;

// Template for UI experience
// Choose user (new or returning)

// New user
  // Fill out questionnaire
  // Display menu

// Returning user
  // Display menu

// Menu
  // Display matches
  // Rate therapist
  // Delete account
  // Logout

/**
 * Class to represent the user interface for TherAppy.  This class acts as a view to take
 * information from the user and display information to the user.
 */
public class UI {
  DBUtils db = new DBUtils("jdbc:mysql://localhost:3306/Therappy?serverTimezone=EST5EDT",
          "root", "openforehand45");
  API api = new API(db);
  String username = null;
  Scanner scan = new Scanner(System.in);

  /**
   * Sets the user of this session as a new or returning user.
   */
  public void setUser() {
    String response;
    while (this.username == null) {
      response = displayWelcomeMenu();

      if (response.equals("E")) {
        System.out.println("Goodbye!");
        System.exit(0);
      } else if (response.equals("N")) {
        User user = newUser();
        this.username = user.getUsername();
      } else {
        try {
          this.username = returningUser();
          System.out.println("Welcome back " + this.username + "!\n");
        } catch (IllegalStateException e) {
          System.out.println(e.getMessage());
        }
      }
    }
  }

  /**
   * Display menu options before a user creates an account or logs in.
   *
   * @return user choice to register as a new user ("N"), log in as returning user ("R"), or exit
   * ("E")
   */
  private String displayWelcomeMenu() {
    System.out.println("Welcome to TherAppy!");

    String response = "";

    while (!response.equals("N") && !response.equals("R") && !response.equals("E")) {
      System.out.println("New user?  Type: N");
      System.out.println("Returning user? Type: R");
      System.out.println("Exit? Type: E");
      response = scan.nextLine();
      response = response.toUpperCase();
    }

    return response;
  }

  //TODO Validate user input????

  /**
   * Register a new user.  Collect all information about user including identification info, and
   * therapist preference info.  Send all info to database interface.
   *
   * @return User object instantiated with all collected info
   */
  private User newUser() {
    System.out.println("We're glad you are here!  Let us help you set up a profile to find a" +
            " therapist who matches your needs and preferences.");
    System.out.println("First name: ");
    String fName = scan.nextLine();

    System.out.println("Last name: ");
    String lName = scan.nextLine();

    System.out.println("New username: ");
    String username = scan.nextLine();

    System.out.println("New password: ");
    String password = scan.nextLine();

    System.out.println("Birthdate (YYYY-MM-DD): ");
    String dob = scan.nextLine();

    System.out.println("Gender (F/M): ");
    String gender = scan.nextLine();

    System.out.println("Email: ");
    String email = scan.nextLine();

    System.out.println("Zip code: ");
    String zipCode = scan.nextLine();

    System.out.println("Name of insurance company (or 'None' if no applicable insurance): ");
    String insurance = scan.nextLine();

    List<String> maladies = getMaladies();

    System.out.println("Maximum distance you would like to travel to meet with your therapist " +
            "(estimate to the nearest mile): ");
    int maxDistance = Integer.parseInt(scan.nextLine());

    System.out.println("Preferred therapist gender (F/M): ");
    String prefGender = scan.nextLine();

    System.out.println("Maximum cost you are able to pay per session (estimate to the nearest " +
            "dollar): ");
    int maxCost = Integer.parseInt(scan.nextLine());

    System.out.println("Highest preferred therapist qualification (MA/PhD/PsyD/None): ");
    String prefQualification = scan.nextLine();

    System.out.println("Require therapist to accept insurance (Y/N): ");
    String textInsReq = scan.nextLine();
    int needsInsurance;
    if (textInsReq.equals("Y")) {
      needsInsurance = 1;
    } else {
      needsInsurance = 0;
    }

    int prefStyle = 0;

    User newUser = new User(lName, fName, username, password, email, gender, dob, zipCode,
            insurance, maladies, maxDistance, prefGender, maxCost, prefQualification,
            needsInsurance, prefStyle);
    api.insertUser(newUser);

    getStylePreference(newUser);
    api.updateUserStyle(newUser);

    System.out.println("Great!  We have created your TherAppy account!\n");

    return newUser;
  }

  /**
   * Ask user about any maladies they want to work on with their therapist and return a list of
   * these maladies.
   *
   * @return list of maladies from user
   */
  private List<String> getMaladies() {
    List<String> maladies = new ArrayList<>();
    System.out.println("Therapists help clients with a diverse set of experiences, including:\n" +
            "1.Addiction  2.Anorexia  3.Anxiety 4.Bipolar 5.Career 6.Dementia 7.Depression\n" +
            "8.Disordered eating 9.Grief  10.OCD  11.PTSD 12.Relationship  13.Schizophrenia\n" +
            "14.Self-exploration  15.Self-harm  16.Sexuality  17.Stress 18.Other\n\n" +
            "You may discuss as many of these experiences as you would like with your\n" +
            "therapist.  Enter your responses one at a time.");
    String response = "";
    while (!response.equals("0")) {
      System.out.println("Enter the number for one experience you would like to discuss\n" +
              "with your therapist (if you are finished adding experiences, enter '0'): ");
      response = scan.nextLine();
      switch (response) {
        case "0":
          break;
        case "1":
          maladies.add("Addiction");
          break;
        case "2":
          maladies.add("Anorexia");
          break;
        case "3":
          maladies.add("Anxiety");
          break;
        case "4":
          maladies.add("Bipolar");
          break;
        case "5":
          maladies.add("Career");
          break;
        case "6":
          maladies.add("Dementia");
          break;
        case "7":
          maladies.add("Depression");
          break;
        case "8":
          maladies.add("Disordered eating");
          break;
        case "9":
          maladies.add("Grief");
          break;
        case "10":
          maladies.add("OCD");
          break;
        case "11":
          maladies.add("PTSD");
          break;
        case "12":
          maladies.add("Relationship");
          break;
        case "13":
          maladies.add("Schizophrenia");
          break;
        case "14":
          maladies.add("Self-exploration");
          break;
        case "15":
          maladies.add("Self-harm");
          break;
        case "16":
          maladies.add("Sexuality");
          break;
        case "17":
          maladies.add("Stress");
          break;
        case "18":
          System.out.println("Enter a word to describe the other experience: ");
          String other = scan.nextLine();
          maladies.add(other);
          break;
        default:
          break;
      }
    }
    return maladies;
  }

  /**
   * Ask user about their therapy style preferences and send these preferences to the database.
   *
   * @param user answering style preference questions
   */
  private void getStylePreference(User user) {
    System.out.println("Each therapist has a different approach to caring for your mental " +
            "health.\nThe following questions help us find therapists that best meet your " +
            "needs.\n");

    String choice;
    System.out.println("1.I have a goal -- 2.No preference -- 3.  I am open to seeing what " +
            "happens");
    choice = validateStyleChoice();
    if (choice.equals("1")) {
      api.insertStylePrefResponse(user, 40000);
    } else if (choice.equals("3")) {
      api.insertStylePrefResponse(user, 40001);
    }

    System.out.println("1.I am action oriented -- 2.No preference -- 3.I like to talk about my " +
            "problems");
    choice = validateStyleChoice();
    if (choice.equals("1")) {
      api.insertStylePrefResponse(user, 40002);
    } else if (choice.equals("3")) {
      api.insertStylePrefResponse(user, 40003);
    }

    System.out.println("1.I like structure -- 2.No preference -- 3.I like fluidity");
    choice = validateStyleChoice();
    if (choice.equals("1")) {
      api.insertStylePrefResponse(user, 40004);
    } else if (choice.equals("3")) {
      api.insertStylePrefResponse(user, 40005);
    }

    System.out.println("1.I am willing to do work outside of sessions\n" +
            " -- 2.No preference -- \n" +
            "3.I want all my work to be done with my therapist");
    choice = validateStyleChoice();
    if (choice.equals("1")) {
      api.insertStylePrefResponse(user, 40006);
    } else if (choice.equals("3")) {
      api.insertStylePrefResponse(user, 40007);
    }

    System.out.println("1.I like to be asked questions\n" +
            " -- 2.No preference -- \n" +
            "3.I prefer to talk about what is on my mind");
    choice = validateStyleChoice();
    if (choice.equals("1")) {
      api.insertStylePrefResponse(user, 40008);
    } else if (choice.equals("3")) {
      api.insertStylePrefResponse(user, 40009);
    }
  }

  /**
   * Helper method to validate user choices to style preference questions.
   *
   * @return validated user choice
   */
  private String validateStyleChoice() {
    String choice = "";
    while (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
      System.out.println("Enter the number for the choice that best represents your preference: ");
      choice = scan.nextLine();
    }
    return choice;
  }

  /**
   * Get and authenticate returning user's username and password.  Return username if
   * authenticated.
   *
   * @return username of authenticated returning user
   * @throws IllegalStateException if username and password cannot be authenticated
   */
  public String returningUser() throws IllegalStateException {
    System.out.println("Enter your username: ");
    String username = scan.nextLine();
    System.out.println("Enter your password: ");
    String password = scan.nextLine();

    try {
      if (api.checkPassword(username, password)) {
        return username;
      } else {
        throw new IllegalStateException("Username and password not found.\nYou may try to log in " +
                "again from the main menu.");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return username;
  }

  /**
   * Displays and collects user choices from the home menu that a user sees once they have
   * registered or logged in.
   */
  public void displayHomeMenu() {
    String response;
    while (true) {
      response = validateMenuAction();
      switch (response) {
        case "1":
          displayMatches();
          break;
        case "2":
          rateTherapist();
          break;
        case "3":
          deleteAccount();
          break;
        case "4":
          logout();
          break;
      }
    }
  }

  /**
   * Helper method to validate user choices from the home menu.
   *
   * @return validated choice from home menu
   */
  private String validateMenuAction() {
    String response = "";
    while (!response.equals("1") && !response.equals("2")
            && !response.equals("3") && !response.equals("4")) {
      System.out.println("What would you like to do?\n" +
              "1.See my therapist matches\n" +
              "2.Rate a therapist\n" +
              "3.Delete my account\n" +
              "4.Logout\n" +
              "Enter the number that matches your preference: ");
      response = scan.nextLine();
    }
    return response;
  }

  /**
   * Display therapist matches for current user.
   */
  private void displayMatches() {
    List<Therapist> matches = null;
    System.out.println("Here are your top matches: ");
    try {
      matches = api.getMatches(this.username);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    for (Therapist therapist : matches) {
      System.out.println(therapist.toString());
    }
  }

  /**
   * Enable current user to rate a therapist.
   */
  private void rateTherapist() {
    System.out.println("Enter the last name of the therapist you would like to rate: ");
    String lName = scan.nextLine();
    System.out.println("Enter the first name of the therapist you would like to rate: ");
    String fName = scan.nextLine();

    int therapistID = 0;
    try {
      therapistID = api.getTherapistID(fName, lName);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    System.out.println("Would not recommend 1   2   3   4   5 Strongly recommend");

    System.out.println("Follow the scale above to enter a rating for " + fName + " " + lName + ":");
    String textRating = scan.nextLine();
    int rating = Integer.parseInt(textRating);

    api.insertTherapistRating(this.username, therapistID, rating);
    System.out.println("Thank you for adding your rating!\n");
  }

  /**
   * Validates a user's choice to delete their account and communicates with the database to delete
   * user data.
   */
  private void deleteAccount() {
    System.out.println("Are you sure your want to delete your account? (Y/N): ");
    String response = scan.nextLine();
    if (response.equals("Y")) {
      api.deleteUser(this.username);
      System.out.println("Your account has been deleted.");
      System.exit(0);
    }
  }

  /**
   * Log a user out of TherAppy.
   */
  private void logout() {
    this.username = null;
    System.out.println("You have been logged out.  See you next time!");
    System.exit(0);
  }
}
