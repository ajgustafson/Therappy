/**
 * Controller for TherAppy application.
 */
public class Driver {

  public static void main(String args[]) {
    // make a new UI object
    UI ui = new UI();

    // greets the user and displays the welcome menu
    ui.setUser();
    ui.displayHomeMenu();
  }
}
