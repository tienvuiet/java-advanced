package RestaurantManagement.dao;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.model.ChefOrderItemView;
import RestaurantManagement.model.OrderItem;
import RestaurantManagement.model.OrderItemView;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    // them mon vao order bang connection de transaction
    public boolean insert(Connection con, OrderItem orderItem) {
        String sql = "insert into order_items(order_id, menu_item_id, quantity, unit_price, status) values(?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getMenuItemId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setBigDecimal(4, orderItem.getUnitPrice());
            ps.setString(5, orderItem.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi them mon vao order_items", e);
        }
    }

    // Lay danh sach mon cua 1 order
    public List<OrderItemView> findByOrderId(int orderId) {
        List<OrderItemView> list = new ArrayList<>();
        String sql = """
                SELECT oi.id AS order_item_id,
                       oi.order_id,
                       oi.menu_item_id,
                       m.name AS menu_item_name,
                       oi.quantity,
                       oi.unit_price,
                       oi.status
                FROM order_items oi
                JOIN menu_items m ON oi.menu_item_id = m.id
                WHERE oi.order_id = ?
                ORDER BY oi.id
                """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItemView item = new OrderItemView();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setMenuItemName(rs.getString("menu_item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    list.add(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach mon theo order", e);
        }
        return list;
    }

    // Lay tat ca mon da goi cua 1 customer
    public List<OrderItemView> findByCustomerId(int customerId) {
        List<OrderItemView> list = new ArrayList<>();

        String sql = """
                SELECT oi.id AS order_item_id,
                       oi.order_id,
                       oi.menu_item_id,
                       m.name AS menu_item_name,
                       oi.quantity,
                       oi.unit_price,
                       oi.status
                FROM order_items oi
                JOIN orders o ON oi.order_id = o.id
                JOIN menu_items m ON oi.menu_item_id = m.id
                WHERE o.customer_id = ?
                ORDER BY o.created_at DESC, oi.id DESC
                """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItemView item = new OrderItemView();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setMenuItemName(rs.getString("menu_item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    list.add(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach mon theo customer", e);
        }

        return list;
    }

    // tim 1 mon da goi theo order_item_id
    public OrderItem findById(int orderItemId) {
        String sql = "SELECT * FROM order_items WHERE id = ?";

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    return item;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim order_item theo id", e);
        }
        return null;
    }

    // kiem tra order item co thuoc customer nay khong
    public OrderItem findByIdAndCustomerId(int orderItemId, int customerId) {
        String sql = """
                SELECT oi.*
                FROM order_items oi
                JOIN orders o ON oi.order_id = o.id
                WHERE oi.id = ? AND o.customer_id = ?
                """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderItemId);
            ps.setInt(2, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    return item;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim order item theo customer", e);
        }

        return null;
    }

    // cap nhat trang thai mon
    public boolean updateStatus(int orderItemId, String newStatus) {
        String sql = "update order_items set status = ? where id = ?";

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, newStatus);
            ps.setInt(2, orderItemId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi cap nhat status order_item", e);
        }
    }




    //== DAU BEP


    /*
     * Danh sach mon cho chef theo trang thai
     * Join orders + users + dining_tables + menu_items
     * Sap xep theo thoi gian goi mon tang dan
     */
    public List<ChefOrderItemView> findChefItemsByStatus(String status) {
        List<ChefOrderItemView> list = new ArrayList<>();

        String sql = """
                SELECT oi.id AS order_item_id,
                       oi.order_id,
                       dt.table_number,
                       u.username AS customer_username,
                       m.name AS menu_item_name,
                       oi.quantity,
                       oi.unit_price,
                       oi.status,
                       o.created_at
                FROM order_items oi
                JOIN orders o ON oi.order_id = o.id
                JOIN users u ON o.customer_id = u.id
                JOIN dining_tables dt ON o.table_id = dt.id
                JOIN menu_items m ON oi.menu_item_id = m.id
                WHERE oi.status = ?
                ORDER BY o.created_at ASC, oi.id ASC
                """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChefOrderItemView item = new ChefOrderItemView();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setTableNumber(rs.getString("table_number"));
                    item.setCustomerUsername(rs.getString("customer_username"));
                    item.setMenuItemName(rs.getString("menu_item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    item.setOrderedAt(rs.getTimestamp("created_at"));
                    list.add(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach mon theo trang thai cho chef", e);
        }

        return list;
    }

    /*
     * Lay tat ca mon trong bep can theo doi
     * Co the hien thi PENDING, COOKING, READY
     * Khong can hien thi CANCELLED neu ban khong muon
     */
    public List<ChefOrderItemView> findKitchenActiveItems() {
        List<ChefOrderItemView> list = new ArrayList<>();

        String sql = """
                SELECT oi.id AS order_item_id,
                       oi.order_id,
                       dt.table_number,
                       u.username AS customer_username,
                       m.name AS menu_item_name,
                       oi.quantity,
                       oi.unit_price,
                       oi.status,
                       o.created_at
                FROM order_items oi
                JOIN orders o ON oi.order_id = o.id
                JOIN users u ON o.customer_id = u.id
                JOIN dining_tables dt ON o.table_id = dt.id
                JOIN menu_items m ON oi.menu_item_id = m.id
                WHERE oi.status IN ('PENDING', 'COOKING', 'READY')
                ORDER BY o.created_at ASC, oi.id ASC
                """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                ChefOrderItemView item = new ChefOrderItemView();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setTableNumber(rs.getString("table_number"));
                item.setCustomerUsername(rs.getString("customer_username"));
                item.setMenuItemName(rs.getString("menu_item_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getBigDecimal("unit_price"));
                item.setStatus(rs.getString("status"));
                item.setOrderedAt(rs.getTimestamp("created_at"));
                list.add(item);
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach mon trong bep", e);
        }

        return list;
    }

    /*
     * Chef tim 1 mon theo id de cap nhat trang thai
     */
    public OrderItem findByIdForChef(int orderItemId) {
        String sql = "SELECT * FROM order_items WHERE id = ?";

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    return item;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim order item cho chef", e);
        }

        return null;
    }





    // Thanh toan
    /*
     * Tinh tong tien cua order
     *
     * Quy tac moi:
     * - KHONG tinh cac mon co status: CANCELLED, REJECTED
     * - Tat ca cac mon con lai deu duoc tinh tien
     */
    public BigDecimal calculateTotalValidItemsByOrderId(int orderId) {
        String sql = """
            SELECT SUM(quantity * unit_price) AS total_amount
            FROM order_items
            WHERE order_id = ? 
              AND status NOT IN ('CANCELLED', 'REJECTED')
            """;
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total_amount");
                    return total != null ? total : BigDecimal.ZERO;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tinh tong tien (bo qua CANCELLED, REJECTED)", e);
        }

        return BigDecimal.ZERO;
    }
    /*
     * Lay danh sach mon hop le de hien thi hoa don
     *
     * Quy tac:
     * - KHONG hien thi mon CANCELLED, REJECTED
     */
    public List<OrderItemView> findValidItemsByOrderId(int orderId) {
        List<OrderItemView> list = new ArrayList<>();

        String sql = """
            SELECT oi.id AS order_item_id,
                   oi.order_id,
                   oi.menu_item_id,
                   m.name AS menu_item_name,
                   oi.quantity,
                   oi.unit_price,
                   oi.status
            FROM order_items oi
            JOIN menu_items m ON oi.menu_item_id = m.id
            WHERE oi.order_id = ? 
              AND oi.status NOT IN ('CANCELLED', 'REJECTED')
            ORDER BY oi.id
            """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItemView item = new OrderItemView();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setMenuItemName(rs.getString("menu_item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    list.add(item);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach mon hop le", e);
        }

        return list;
    }



    // review
    /*
     * Kiem tra 1 mon co thuoc order nao do khong
     * Dong thoi lay thong tin item de phuc vu review mon
     */
    public OrderItem findByOrderIdAndMenuItemId(int orderId, int menuItemId) {
        String sql = """
            SELECT *
            FROM order_items
            WHERE order_id = ? AND menu_item_id = ?
            LIMIT 1
            """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));
                    return item;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim order item theo orderId va menuItemId", e);
        }

        return null;
    }
    /*
     * Lay danh sach cac mon da SERVED cua 1 order
     *
     * Muc dich:
     * - Dung cho Customer theo doi mon da hoan thanh
     * - Dung cho Review (chi cho danh gia mon da SERVED)
     *
     * Chi lay:
     * - status = 'SERVED'
     *
     * Sap xep:
     * - theo thu tu goi mon (id tang dan)
     */
    public List<OrderItemView> findServedItemsByOrderId(int orderId) {
        List<OrderItemView> list = new ArrayList<>();

        String sql = """
            SELECT oi.id AS order_item_id,
                   oi.order_id,
                   oi.menu_item_id,
                   m.name AS menu_item_name,
                   oi.quantity,
                   oi.unit_price,
                   oi.status
            FROM order_items oi
            JOIN menu_items m ON oi.menu_item_id = m.id
            WHERE oi.order_id = ? 
              AND oi.status = 'SERVED'
            ORDER BY oi.id
            """;

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItemView item = new OrderItemView();

                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setMenuItemId(rs.getInt("menu_item_id"));
                    item.setMenuItemName(rs.getString("menu_item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getBigDecimal("unit_price"));
                    item.setStatus(rs.getString("status"));

                    list.add(item);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach mon da SERVED theo order", e);
        }

        return list;
    }


}