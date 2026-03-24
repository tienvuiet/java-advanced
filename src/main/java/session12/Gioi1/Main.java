package session12.Gioi1;

import session11.XuatSac1.Db.DBKetNoi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
//    Pre-compiled = biên dịch trước + cố định cấu trúc → database luôn biết phần nào là code (không cho đụng), phần nào là data (dù data có độc hại cũng chỉ là data).
//            → Đây chính là lý do PreparedStatement (và parameterized query nói chung) là cách an toàn nhất, chuẩn vàng để chống SQL Injection.

    public  void Login(){
        Connection con = null;
        PreparedStatement ps = null;
        con = DBKetNoi.getConnectionDoctor();
        String sql = "select * from Doctor where code = ? and pass = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "tien");
            ps.setString(2, "123456");
            ResultSet row = ps.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
