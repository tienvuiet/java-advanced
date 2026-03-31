package RestaurantManagement.service;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.dao.DiningTableDAO;
import RestaurantManagement.dao.MenuItemDAO;
import RestaurantManagement.dao.OrderDAO;
import RestaurantManagement.dao.OrderItemDAO;
import RestaurantManagement.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final DiningTableDAO diningTableDAO = new DiningTableDAO();
    private final MenuItemDAO menuItemDAO = new MenuItemDAO();
    // su ly goi mon khach hang
    //occupy chiếm :))

    // lay danh sach ban an do Customer chon
    public List<DiningTable> getAvailableTables(){
        return diningTableDAO.findAvailableTables();
    }
    // lay danh sach mon da goi cua customer
    public List<OrderItemView> getMyOrderedItems(int customerId){
        return orderItemDAO.findByCustomerId(customerId);
    }

    //dat ban goi mon
    // kiem tra ban
    // tao order
    // truyen tung mon vao order_item voi status = pending
    // chuyen trang thai ban sang occupied
    public String placeOrder(int customerId, int tableId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return "Danh sach mon goi khong duoc de trong";
        }

        DiningTable table = diningTableDAO.findById(tableId);
        if (table == null) {
            return "Khong tim thay ban";
        }

        Connection con = null;
        try {
            con = DBConnection.getConnectionRM();
            con.setAutoCommit(false);

            int orderId;

            // CASE 1: Ban dang trong -> tao order moi
            if ("AVAILABLE".equalsIgnoreCase(table.getStatus())) {
                Order newOrder = new Order();
                newOrder.setCustomerId(customerId);
                newOrder.setTableId(tableId);
                newOrder.setStatus("OPEN");

                orderId = orderDAO.createOrder(con, newOrder);
                if (orderId <= 0) {
                    con.rollback();
                    return "Tao order that bai";
                }

                boolean updatedTable = diningTableDAO.updateStatus(con, tableId, "OCCUPIED");
                if (!updatedTable) {
                    con.rollback();
                    return "Cap nhat trang thai ban that bai";
                }
            }
            // CASE 2: Ban da co nguoi -> kiem tra co phai order dang mo cua customer nay khong
            else if ("OCCUPIED".equalsIgnoreCase(table.getStatus())) {
                Order existingOpenOrder = orderDAO.findOpenOrderByCustomerAndTable(customerId, tableId);

                if (existingOpenOrder == null) {
                    con.rollback();
                    return "Ban nay dang duoc su dung, ban khong the goi them mon vao ban nay";
                }

                orderId = existingOpenOrder.getId();
            } else {
                con.rollback();
                return "Trang thai ban khong hop le";
            }

            // Them mon vao order_items
            for (OrderItem item : items) {
                MenuItem menuItem = menuItemDAO.findById(item.getMenuItemId());

                if (menuItem == null) {
                    con.rollback();
                    return "Mon co id = " + item.getMenuItemId() + " khong ton tai";
                }

                if (!menuItem.isAvailable()) {
                    con.rollback();
                    return "Mon " + menuItem.getName() + " hien dang ngung ban";
                }

                if (item.getQuantity() <= 0) {
                    con.rollback();
                    return "So luong mon phai > 0";
                }

                item.setOrderId(orderId);
                item.setUnitPrice(menuItem.getPrice());
                item.setStatus("PENDING");

                boolean inserted = orderItemDAO.insert(con, item);
                if (!inserted) {
                    con.rollback();
                    return "Them mon vao order that bai";
                }
            }

            con.commit();
            return "SUCCESS";

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Loi xu ly goi mon", e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // huy mon
    // chi huy mon do thuoc customer hien tai
    // chi huy duoc khi status = pending
    public  String cancelOrderItem(int customerId, int orderItemId){
        OrderItem item = orderItemDAO.findByIdAndCustomerId(orderItemId, customerId);
        if (item == null){
            return "Khong tim thay mon da goi hoac mon nay khong thuoc tai khoan cua ban";
        }
        if (!"PENDING".equalsIgnoreCase(item.getStatus())){
            return "Chi duoc huy mon o trang thai PENDING";
        }
        boolean updated = orderItemDAO.updateStatus(orderItemId, "CANCELLED");
        return updated ? "SUCCESS": "Huy mon that bai";
    }
}
