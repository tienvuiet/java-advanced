package session12.Gioi2;

import session11.db.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    // Vì các phương thức như setDouble(), setInt() của PreparedStatement sẽ tự động chuyển giá trị Java sang đúng định dạng mà database yêu cầu, không phụ thuộc vào thiết lập vùng (locale) của hệ điều hành.
    public void updateBenhNhan(){
        String sql = "update Vitals set temperature = ? where p_id = ?";

        try (Connection con = DBUtility.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)
        ){

            Double temp = 37.5;
            int patientId = 1;
            ps.setDouble(1, temp);
            ps.setInt(2, patientId);
            int row = ps.executeUpdate();
            System.out.println("So dong cap nhat: "+ row);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
