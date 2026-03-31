package RestaurantManagement.model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int customerId;
    private int tableId;
    private String status;
    private Timestamp createdAt;

    public Order() {}

    public Order(int id, int customerId, int tableId, String status, Timestamp createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.tableId = tableId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
