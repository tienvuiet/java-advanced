package RestaurantManagement.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
      public  static Connection getConnectionRM(){
          Connection con = null;
          String url = "jdbc:mysql://localhost:3306/projectAdvanced";
          String user = "root";
          String password = "123456";
          try {
              con = DriverManager.getConnection(url, user, password);
          } catch (SQLException e) {
              throw new RuntimeException("Ket noi database that bai: ",e);
          }
          return con;
      }

}
