package session14.MiniPrj.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMiniprj {
    public static Connection getConnectionMiniPrj(){
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/miniprj";
        String user = "root";
        String password = "123456";
        try {
            con = DriverManager.getConnection(url, user, password);
//            if (con != null) System.out.println("Da ket noi data");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    static void main(String[] args) {
        System.out.println(getConnectionMiniPrj());
    }
}
