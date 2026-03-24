package session11.Kha1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Kha1 {
   public  static Connection getConnection(){
       Connection con = null;
       try {
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db1", "root", "123456");
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return con;
   }
}
// phan 1
//rò rỉ tài nguyên : DriverManager.getConnection: tạo ra connection mới đến DB
// nếu không close(): connection vẫn tồn tại trong db, tích lủy theo thời gian

// connection chết nhung vẫn bị dùng lại
// tao connection lien tuc = ton tai nguyen
// khi db qua tai : api backend treo, FE khong phan hoi, toan bo he thong ngung hoat dong

