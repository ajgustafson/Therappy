import org.junit.Before;
import org.junit.Test;

import java.sql.Date;

import JDBC_utils.DBUtils;

public class apiTest {

  private JDBC_utils.DBUtils db;
  private TherAppyAPI api;

  @Before
  public void setUp() {
    db = new DBUtils("jdbc:mysql://localhost:3306/Therappy?serverTimezone=EST5EDT",
            "therappy", "therappyproject!");
    api = new API(db);
  }

  @Test
  public void insertUser() {
    User user = new User("Roberts", "James", "jrobot@gmail.com",
            'M', new Date(5241992), "02118", "1",
            10, 'F', 200, "1",
            0, 1);

    System.out.println(user.getPrefStyle());

    int key = api.insertUser(user);
    System.out.println(key);
  }
}