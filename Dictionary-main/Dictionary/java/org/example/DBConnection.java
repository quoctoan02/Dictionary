package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  public static Connection getConnection() {
    try {
      return DriverManager.getConnection(
          "jdbc:mariadb://localhost:3306/snow", "root", "123");
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return null;
  }

}
