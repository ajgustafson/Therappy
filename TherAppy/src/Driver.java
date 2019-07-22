import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import JDBC_utils.DBUtils;

public class Driver {

  static DBUtils dbu;

//  private String[] getUserAndPassword() {
//    System.out.println("Please login to the database:");
//    System.out.print("url: ");
//    Scanner scanner = new Scanner(System.in);
//    String userName = scanner.nextLine();
//
//    System.out.print("username: ");
//    Scanner scanner = new Scanner(System.in);
//    String userName = scanner.nextLine();
//
//    System.out.print("password: ");
//    scanner = new Scanner(System.in);
//    String password = scanner.nextLine();
//    String[] data = {url, userName, password};
//    return data;
//  }

  private static List<String> getSquirrels() {
    String sql = "select * from squirrel";

    List<String> squirrels = new ArrayList<String>();


    try {
      // get connection and initialize statement
      Connection con = dbu.getConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next() != false)
        squirrels.add(
                rs.getString("squirrel_id") + " " +
                        rs.getString("name") + " " +
                        rs.getString("gender") + " "
        );
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    return squirrels;
  }

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (Exception e) {
      //
    }
    dbu = new DBUtils("jdbc:mysql://localhost:3306/squirrels?serverTimezone=EST5EDT",
            "therappy", "therappyproject!");

    List<String> squirrels = getSquirrels();

    for (String str : squirrels) {
      System.out.println(str);
    }

    dbu.closeConnection();
  }
}
