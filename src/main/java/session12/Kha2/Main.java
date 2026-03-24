package session12.Kha2;

import session11.db.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
    static void main(String[] args) {
        // Lặp lại 1000 lần → tốn CPU
        // Cùng 1 cấu trúc mà vẫn phải lập kế hoạch lại → lãng phí
        // 1000 lần → tốn RAM + overhead
//        String sql = "INSERT INTO Results(data) VALUES(?)";
//
//        try (Connection conn = DBUtility.getConnection();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            for (TestResult tr : list) {
//                ps.setString(1, tr.getData()); // nạp tham số
//                ps.executeUpdate();            // thực thi
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
