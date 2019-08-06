import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UI {
  User user;
  Scanner scan = new Scanner(System.in);

  public void displayGreeting() {
    System.out.println("Welcome to TherAppy!");

    String response = "";

    while (!response.equals("N") && !response.equals("R")) {
      System.out.println("New user?  Type: N");
      System.out.println("Returning user? Type: R");
      response = scan.nextLine();
      response = response.toUpperCase();
    }
    System.out.println("You responded: " + response);
  }

  public User newUser() {
    System.out.println("We're glad you are here!  Let us help you set up a profile to find a" +
            " therapist who matches your needs and preferences.");
    System.out.println("First name: ");
    String fName = scan.nextLine();

    System.out.println("Last name: ");
    String lName = scan.nextLine();

    System.out.println("New username: ");
    String username = scan.nextLine();

    System.out.println("Birthdate (MM-DD-YYY): ");
    String birthdate = scan.nextLine();

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    try {
      Date dob = sdf.parse(birthdate);
    } catch (ParseException e) {
      System.out.println("Birthdate error.");
    }

    System.out.println("Gender (F/M): ");
    String gender = scan.nextLine();

    System.out.println("Email: ");
    String email = scan.nextLine();

    System.out.println("Zip code: ");
    String zipCode = 



    User newUser = new User();
    return newUser;
  }
  // Menu
    // Get matches
    // Rate therapist
    // Retake questionnaire
    // Delete account
    // Logout

  // New user
    // Fill out questionnaire
    // Display menu

  // Returning user
    // Display menu

}
