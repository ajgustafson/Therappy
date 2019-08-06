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
    //    Calendar calendar = Calendar.getInstance();
    //    calendar.set(1956, 12, 25);
    User testUser = new User("Wickman", "Genia", "gwickman8",
            "695BMNauvKp", "gwickman8@abc.net.au", "F", "1956-12-25",
            "02118", "20010", null, 13, "M",
            97, "30002", 1, 10001);


    try {
      List<Therapist> therapists = api.filterTherapistMatches(testUser);
      for (Therapist therapist : therapists) {
        System.out.println(therapist);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
