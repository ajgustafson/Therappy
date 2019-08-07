import java.sql.SQLException;

import JDBC_utils.DBUtils;

public class UITest {

  public static void main(String args[]) {
    UI ui = new UI();

    ui.setUser();

    ui.displayHomeMenu();

//    DBUtils db = new DBUtils("jdbc:mysql://localhost:3306/Therappy?serverTimezone=EST5EDT",
//            "therappy", "therappyproject!");
//    API api = new API(db);
//    try {
//      System.out.println(ui.returningUser());
//    } catch (Exception e) {
//      System.out.println(e.getMessage());
//    }


  }
}
