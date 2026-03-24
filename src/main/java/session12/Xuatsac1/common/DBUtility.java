package session12.Xuatsac1.common;

import java.sql.*;

public class DBUtility {
    public static Connection getConnectionPatient(){
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/patients";
        String user = "root";
        String password = "123456";
        try {
            con = DriverManager.getConnection(url, user, password);
            if (con != null) System.out.println("Da ket noi patient");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    static void main(String[] args) {
        System.out.println(getConnectionPatient());
    }


}
