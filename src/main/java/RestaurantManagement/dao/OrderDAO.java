package RestaurantManagement.dao;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    //
    // Tao order theo connection co san de dung trong transaction
    public int createOrder(Connection con, Order order) {
        String sql = "insert into orders(customer_id, table_id, status) values (?, ?, ?)";

        try (
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, order.getCustomerId());
            ps.setInt(2, order.getTableId());
            ps.setString(3, order.getStatus());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tao order", e);
        }
        return -1;
    }
    public Order findOpenOrderByCustomerAndTable(int customerId, int tableId) {
        String sql = "SELECT * FROM orders WHERE customer_id = ? AND table_id = ? AND status = 'OPEN' ORDER BY id DESC LIMIT 1";

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, customerId);
            ps.setInt(2, tableId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableId(rs.getInt("table_id"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    return order;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim open order theo customer va table", e);
        }

        return null;
    }




    // chuc nang thanh toan
    // tim order dang open theo tableId
    public Order findOpenOrderByTableId(int tableId) {
        String sql = "SELECT * FROM orders WHERE table_id = ? AND status = 'OPEN' LIMIT 1";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, tableId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableId(rs.getInt("table_id"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    return order;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim order OPEN theo table", e);
        }

        return null;
    }
    //cap nhat trang thai order trong transaction
    // order se duoc doi tu open->completed
    public boolean updateStatus(Connection con, int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi cap nhat status order", e);
        }
    }


    // danh gia kiem tra order có thuoc customer và da completed chua
    /*
     * Tim order theo id va customer
     * Dung de kiem tra quyen review
     */
    public Order findByIdAndCustomerId(int orderId, int customerId) {
        String sql = "SELECT * FROM orders WHERE id = ? AND customer_id = ?";

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ps.setInt(2, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableId(rs.getInt("table_id"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    return order;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim order theo id va customer", e);
        }

        return null;
    }


    /*
     * Lay danh sach order da COMPLETED cua 1 customer
     *
     * Muc dich:
     * - Hien thi truoc khi customer nhap order_id de review
     * - Chi hien thi order da hoan tat moi duoc danh gia
     */
    public List<Order> findCompletedOrdersByCustomerId(int customerId) {
        List<Order> list = new ArrayList<>();
        String sql = """
            SELECT *
            FROM orders
            WHERE customer_id = ? AND status = 'COMPLETED'
            ORDER BY id DESC
            """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableId(rs.getInt("table_id"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(order);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach order COMPLETED theo customer", e);
        }

        return list;
    }
}
