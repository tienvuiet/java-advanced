package RestaurantManagement.dao;

import RestaurantManagement.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InvoiceDAO {
    public boolean insert(Connection con, Invoice invoice) {
        String sql = "INSERT INTO invoices(order_id, total_amount, checked_out_at) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoice.getOrderId());
            ps.setBigDecimal(2, invoice.getTotalAmount());
            ps.setTimestamp(3, invoice.getCheckedOutAt());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi tao invoice", e);
        }
    }

    // tim hoa don theo orderId
    public Invoice findByOrderId(int orderId) {
        String sql = "SELECT * FROM invoices WHERE order_id = ?";

        try (
                Connection con = RestaurantManagement.config.DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("id"));
                    invoice.setOrderId(rs.getInt("order_id"));
                    invoice.setTotalAmount(rs.getBigDecimal("total_amount"));
                    invoice.setCheckedOutAt(rs.getTimestamp("checked_out_at"));
                    return invoice;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim invoice theo order_id", e);
        }

        return null;
    }
}
