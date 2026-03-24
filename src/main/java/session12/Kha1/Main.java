package session12.Kha1;

import session11.db.DBUtility;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Main {
    static void main(String[] args) {
        // JDBC bắt buộc phải gọi registerOutParameter() trước khi thực thi
        //JDBC can biet kieu du lieu cua tham so out, de cap phat bo nho phu hop
        // neu mà nó kiểu decimal thì TYPE.Decimal

        try(
                Connection con = DBUtility.getConnection();
            CallableStatement csmt = con.prepareCall("{call get_surery_pee(?, ?)}");
        ) {

            csmt.setInt(1, 505);
            csmt.registerOutParameter(2, Types.DOUBLE);
            csmt.execute();
            double cost = csmt.getDouble(2);
            System.out.println("DKhasdsd: "+ cost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
