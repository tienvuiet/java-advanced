package RestaurantManagement.model;

import java.sql.Timestamp;

public class OrderApproval {
    private int id;
    private int orderItemId;
    private int managerId;
    private String approvalStatus;
    private String reason;
    private Timestamp approvedAt;

    public OrderApproval() {}

    public OrderApproval(int id, int orderItemId, int managerId, String approvalStatus, String reason, Timestamp approvedAt) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.managerId = managerId;
        this.approvalStatus = approvalStatus;
        this.reason = reason;
        this.approvedAt = approvedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    public int getManagerId() { return managerId; }
    public void setManagerId(int managerId) { this.managerId = managerId; }

    public String getApprovalStatus() { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus) { this.approvalStatus = approvalStatus; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Timestamp getApprovedAt() { return approvedAt; }
    public void setApprovedAt(Timestamp approvedAt) { this.approvedAt = approvedAt; }
}
