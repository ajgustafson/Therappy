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

}
