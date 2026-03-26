package session14.MiniPrj.dao;

import session14.MiniPrj.Db.DBMiniprj;

import java.sql.*;

public class FlashSaleService {
    public void placeOrder(int userId, int productId, int quantity){
        Connection con = null;
        try {
            con = DBMiniprj.getConnectionMiniPrj();
            con.setAutoCommit(false);
            // kiem tra ton kho
            String checkSQL = "select stock, price from Products where id = ? for update";
            PreparedStatement check = con.prepareStatement(checkSQL);
            check.setInt(1, productId);
            ResultSet rs = check.executeQuery();
            if (rs.next()){
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");
                if (stock < quantity){
                    System.out.println(" het hang");
                    return;
                }

                // tao order
                String orderSQL = "insert into Orders(user_id, total_amount) values (?, ?)";
                PreparedStatement orderStmt = con.prepareStatement(orderSQL, Statement.RETURN_GENERATED_KEYS);
                //Statement.RETURN_GENERATED_KEYS lấy id vừa tạo order
                orderStmt.setInt(1, userId);
                orderStmt.setDouble(2, price * quantity);
                orderStmt.executeUpdate();

                ResultSet rsOrder = orderStmt.getGeneratedKeys();
                rsOrder.next();
                int orderId = rsOrder.getInt(1);

                // tao order detail
                String detailSQL = "insert into Order_Details(order_id,product_id,quantity,price) values (?, ? , ?, ?)";
                PreparedStatement detail = con.prepareStatement(detailSQL);
                detail.setInt(1, orderId);
                detail.setInt(2, productId);
                detail.setInt(3, quantity);
                detail.setDouble(4,price );
                detail.executeUpdate();

                // tru stock
                String updateSql = "update Products set stock = stock - ? where id = ?";
                PreparedStatement prstock = con.prepareStatement(updateSql);
                prstock.setInt(1, quantity);
                prstock.setInt(2, productId);
                prstock.executeUpdate();

                con.commit();
                System.out.println("Dat hang thanh cong");
            }
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
