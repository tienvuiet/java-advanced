package session14.Test.Dao;

import session14.Test.Db.DBTest;

import java.sql.*;

public class Transfer {
    public void TransferMoney(String fromId, String toId, double amount){
        Connection con = null;
        PreparedStatement ps = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            con = DBTest.getConnectionTest();
            con.setAutoCommit(false);
            String checkGui = "select Balance from Accounts where AccountId = ? for update";
            ps = con.prepareStatement(checkGui);
            ps.setString(1, fromId);
            rs = ps.executeQuery();
            if (!rs.next()){
                System.out.println("Tai khoan khong ton tai");
                con.rollback();
                return;
            }

            double balance = rs.getDouble("Balance");
            if (balance < amount){
                System.out.println("Khong du tien");
                con.rollback();
                return ;
            }

            String checkNhan = "select AccountId from Accounts where AccountId = ?";
            ps = con.prepareStatement(checkNhan);
            ps.setString(1, toId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                System.out.println("Khong co tai khoan nhan");
                con.rollback();
                return;
            }

            cs = con.prepareCall("{call sp_UpdateBalance(?, ?)}");

            cs.setString(1, fromId);
            cs.setDouble(2, -amount);
            cs.execute();

            cs.clearParameters();
            cs.setString(1, toId);
            cs.setDouble(2, amount);
            cs.execute();
            con.commit();
            System.out.println("Chuyen tien thanh cong");
            String resultSql = "select * from Accounts where AccountId in (?, ?)";
            ps = con.prepareStatement(resultSql);
            ps.setString(1, fromId);
            ps.setString(2, toId);
            rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(
                        rs.getString("AccountId") + " | " +
                                rs.getString("FullName") + " | "+
                                rs.getString("Balance")
                );
            }

        } catch (Exception e) {
            System.out.println("Loi: "+ e.getMessage());
            if (con != null){
                try {
                    con.rollback();
                    System.out.println("rollback thanh cong");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (cs != null) cs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
