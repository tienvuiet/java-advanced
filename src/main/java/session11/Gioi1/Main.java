package session11.Gioi1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;

public class Main {
    // cai executeUpdated
    // tra ve > 0: so dong bi anh huong
    // tra ve = 0 : theo tac khong loi nhung khong dong nao bi thay doi
    public void update(){
        Connection con = null;
        PreparedStatement ps = null;

        try {
//            con = DriverManager.getConnection();

            String sql = "UPDATE Beds SET bed_status = ? WHERE bed_id = ?";
            ps = con.prepareStatement(sql);

            ps.setString(1, "Occupied");
            ps.setInt(2, 23);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Mã giường không tồn tại!");
            } else {
                System.out.println("Cập nhật giường thành công!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
