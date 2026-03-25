package session14.Test.Db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
    public  static Connection getConnectionTest(){
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/testDh";
        String user = "root";
        String password = "123456";
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    static void main(String[] args) {
        System.out.println(getConnectionTest());
    }
}
