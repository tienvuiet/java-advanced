package RestaurantManagement.model;

import java.math.BigDecimal;

/*
 * Model phụ dùng để hiển thị dữ liệu join giữa order_items và menu_items
 * Không map trực tiếp 1 bảng, chỉ dùng để show ra UI
 */
public class OrderItemView {
    private int orderItemId;
    private int orderId;
    private int menuItemId;
    private String menuItemName;
    private int quantity;
    private BigDecimal unitPrice;
    private String status;

    public OrderItemView() {
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}