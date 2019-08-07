import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import JDBC_utils.DBUtils;
/*
public class Driver {

  private static DBUtils dbu;

  public static void main(String[] args) {


    dbu = new DBUtils("jdbc:mysql://localhost:3306/therappy",
            "insert your username here", "insert your password here");

    TherAppyAPI api = new API(dbu);

    List<String> malady1;
    malady1 = new ArrayList<>();
    malady1.add("Addiction");
    malady1.add("Career");
    malady1.add("Bipolar");

    User u1 = new User("bob", "bob", "jefe101", "pword101", "bob@testing.com",
            "M", "2017-04-03",
            "02131", "aetna", malady1, 8, "F",
            6, "MA", 0, 1);

    User u2 = new User("jake", "jake", "jefe101", "pword101", "jake@testing.com",
            "M", "2017-04-03",
            "02131", "aetna", new ArrayList<>(), 8, "F",
            6, "PhD", 0, 1);

    User u3 = new User("april1", "april1", "jefe101", "pword101", "april1@testing.com",
            "M", "2017-04-03",
            "02131", "cigna", new ArrayList<>(), 8, "F",
            6, "MS", 0, 1);


    api.insertUser(u1);
    api.insertStylePrefResponse(u1,40001);
    api.insertStylePrefResponse(u1,40003);
    api.insertStylePrefResponse(u1,40005);
    // two more insertStylePrefResponse here

    api.updateUserStyle(u1);
    api.insertTherapistRating(u1,1001, 7);
  }
}*/
