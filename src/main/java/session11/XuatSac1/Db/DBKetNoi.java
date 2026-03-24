package session11.XuatSac1.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBKetNoi {
    public  static Connection getConnectionDoctor(){
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/patiens";
        String user = "root";
        String password = "123456";
        try {
            con = DriverManager.getConnection(url, user, password);
            if (con!= null){
                System.out.println("Da ket noi database patient");
            }
        } catch (SQLException e) {
            System.out.println("Loi ket noi: "+ e.getMessage());
        }
        return con;
    }

    static void main(String[] args) {
        System.out.println(getConnectionDoctor());
    }
}
