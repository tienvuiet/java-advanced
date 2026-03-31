package RestaurantManagement.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Invoice {
    private int id;
    private int orderId;
    private BigDecimal totalAmount;
    private Timestamp checkedOutAt;

    public Invoice() {}

    public Invoice(int id, int orderId, BigDecimal totalAmount, Timestamp checkedOutAt) {
        this.id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.checkedOutAt = checkedOutAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Timestamp getCheckedOutAt() { return checkedOutAt; }
    public void setCheckedOutAt(Timestamp checkedOutAt) { this.checkedOutAt = checkedOutAt; }
}
