package RestaurantManagement.service;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.dao.DiningTableDAO;
import RestaurantManagement.dao.InvoiceDAO;
import RestaurantManagement.dao.OrderDAO;
import RestaurantManagement.dao.OrderItemDAO;
import RestaurantManagement.model.Invoice;
import RestaurantManagement.model.Order;
import RestaurantManagement.model.OrderItemView;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class PaymentService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final DiningTableDAO diningTableDAO = new DiningTableDAO();
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();


    /*
     * Xem truoc hoa don
     *
     * Quy tac:
     * - hien thi tat ca mon KHONG bi CANCELLED, REJECTED
     */
    public List<OrderItemView> preview(int tableId) {
        Order order = orderDAO.findOpenOrderByTableId(tableId);

        if (order == null) {
            System.out.println("Bat b=loi");
            return List.of();
        }

        return orderItemDAO.findValidItemsByOrderId(order.getId());
    }

    // thanh toan cho 1 ban
    // tim order dang open
    // tinh tong tien cac mon served
    // tao invoice
    // update order -> completed
    // update table -> available
    /*
     * Thanh toan theo ban
     *
     * Quy tac moi:
     * - tinh tien tat ca mon KHONG bi CANCELLED, REJECTED
     */
    public String checkout(int tableId) {
        Order order = orderDAO.findOpenOrderByTableId(tableId);

        if (order == null) {
            return "Khong co order dang mo cho ban nay";
        }

        // tranh thanh toan lap
        Invoice existingInvoice = invoiceDAO.findByOrderId(order.getId());
        if (existingInvoice != null) {
            return "Order nay da duoc thanh toan truoc do";
        }

        // 👉 dùng hàm mới
        BigDecimal total = orderItemDAO.calculateTotalValidItemsByOrderId(order.getId());

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            return "Khong co mon hop le de thanh toan";
        }

        Connection con = null;
        try {
            con = DBConnection.getConnectionRM();
            con.setAutoCommit(false);

            // tao invoice
            Invoice invoice = new Invoice();
            invoice.setOrderId(order.getId());
            invoice.setTotalAmount(total);
            invoice.setCheckedOutAt(new Timestamp(System.currentTimeMillis()));

            if (!invoiceDAO.insert(con, invoice)) {
                con.rollback();
                return "Tao hoa don that bai";
            }

            if (!orderDAO.updateStatus(con, order.getId(), "COMPLETED")) {
                con.rollback();
                return "Cap nhat order that bai";
            }

            if (!diningTableDAO.updateStatus(con, tableId, "AVAILABLE")) {
                con.rollback();
                return "Cap nhat trang thai ban that bai";
            }

            con.commit();
            return "SUCCESS: " + total;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Loi thanh toan", e);
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
}
