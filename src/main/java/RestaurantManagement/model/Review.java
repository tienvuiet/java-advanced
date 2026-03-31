package RestaurantManagement.model;

import java.sql.Timestamp;

public class Review {
    private int id;
    private int customerId;
    private int orderId;
    private Integer menuItemId;
    private int rating;
    private String comment;
    private Timestamp createdAt;

    public Review() {}

    public Review(int id, int customerId, int orderId, Integer menuItemId, int rating, String comment, Timestamp createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public Integer getMenuItemId() { return menuItemId; }
    public void setMenuItemId(Integer menuItemId) { this.menuItemId = menuItemId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
