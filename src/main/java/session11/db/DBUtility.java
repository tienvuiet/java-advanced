package session11.db;

import java.sql.*;

public class DBUtility {
    public static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db1", "root", "123456");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll(con);
        }
        return con;
    }

    static void main(String[] args) {
        System.out.println(getConnection());

    }
    public static void closeAll(Connection con) {
        if (con!= null){
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
