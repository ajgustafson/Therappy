import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import JDBC_utils.DBUtils;


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


public class UI {
  DBUtils db = new DBUtils("jdbc:mysql://localhost:3306/Therappy?serverTimezone=EST5EDT",
          "therappy", "therappyproject!");
  API api = new API(db);
  User user;
  Scanner scan = new Scanner(System.in);

  public void setUser() {
    System.out.println("Welcome to TherAppy!");

    String response = "";

    while (!response.equals("N") && !response.equals("R")) {
      System.out.println("New user?  Type: N");
      System.out.println("Returning user? Type: R");
      response = scan.nextLine();
      response = response.toUpperCase();
    }

    if (response.equals("N")) {
      this.user = newUser();
    } else {
      // this.user = getUser();
    }
  }

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

    System.out.println("Birthdate (MM-DD-YYY): ");
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

    System.out.println("Maximum distance you would like to travel to meet with your therapist (estimate to the nearest mile): ");
    int maxDistance = scan.nextInt();

    System.out.println("Preferred therapist gender (F/M): ");
    String prefGender = scan.nextLine();

    System.out.println("Maximum cost you are able to pay per session (estimate to the nearest dollar): ");
    int maxCost = scan.nextInt();

    System.out.println("Highest preferred therapist qualification (Master/PhD/PsyD): ");
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
    return newUser;
  }

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
      }
    }
    return maladies;
  }

  private User returningUser() {
    System.out.println("Enter your username: ");
    String username = scan.nextLine();
    System.out.println("Enter your password: ");
    String password = scan.nextLine();

    if (api.checkPassword(username, password)) {
    }

  }

  public void displayMenu() {
    String response;
    boolean quit = false;
    while (!quit) {
      response = getMenuAction();
      switch (response) {
        case "1":
          displayMatches();
          break;
        case "2":
          rateTherapist();
          break;
        case "3":
          deleteAccount();
          quit = true;
          break;
        case "4":
          logout();
          quit = true;
          break;
      }
    }
  }

  private String getMenuAction() {
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

  private void displayMatches() {
    System.out.println("Here are your top matches: ");
    List<Therapist> matches = api.getMatches(this.user);
    for (Therapist therapist : matches) {
      System.out.println(therapist.toString());
    }
  }

  private void rateTherapist() {
    System.out.println("Enter the last name of the therapist you would like to rate: ");
    String lName = scan.nextLine();
    System.out.println("Enter the first name of the therapist you would like to rate: ");
    String fName = scan.nextLine();

    // Therapist therapist = api.getTherapist(lName, fName);
    System.out.println("Would not recommend 1   2   3   4   5 Strongly recommend");

    System.out.println("Follow the scale above to enter a rating for " + fName + " " + lName + ":");
    int rating = scan.nextInt();

    //api.insertRating(this.user, therapist, rating);
    System.out.println("Thank you for adding your rating!");
  }

  private void deleteAccount() {
    System.out.println("Are you sure your want to delete your account? (Y/N): ");
    String response = scan.nextLine();
    if (response.equals("Y")) {
      //api.deleteUser(this.user);
      System.out.println("Your account has been deleted.");
      System.exit(0);
    }
  }

  private void logout() {
    this.user = null;
    System.out.println("You have been logged out.  See you next time!");
  }
}
