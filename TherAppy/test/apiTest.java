import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
  public void testFilterTherapists() {
    try {
      List<Therapist> therapists = api.getMatches("gwickman8", false);
//      for (Therapist therapist : therapists) {
//        System.out.println(therapist);
//      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
