package RestaurantManagement.service;

import RestaurantManagement.dao.OrderDAO;
import RestaurantManagement.dao.OrderItemDAO;
import RestaurantManagement.dao.ReviewDAO;
import RestaurantManagement.model.Order;
import RestaurantManagement.model.OrderItem;
import RestaurantManagement.model.OrderItemView;
import RestaurantManagement.model.Review;

import java.util.List;

public class ReviewService {
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    /*
     * Tao review cho 1 mon cu the trong order
     *
     * Rule:
     * - order phai thuoc customer
     * - order phai COMPLETED
     * - mon phai nam trong order
     * - mon nen la SERVED
     */
    public String addMenuItemReview(int customerId, int orderId, int menuItemId, int rating, String comment) {
        Order order = orderDAO.findByIdAndCustomerId(orderId, customerId);

        if (order == null) {
            return "Khong tim thay order hoac order nay khong thuoc tai khoan cua ban";
        }

        if (!"COMPLETED".equalsIgnoreCase(order.getStatus())) {
            return "Chi duoc danh gia mon trong order da COMPLETED";
        }

        OrderItem item = orderItemDAO.findByOrderIdAndMenuItemId(orderId, menuItemId);
        if (item == null) {
            return "Mon nay khong thuoc order da chon";
        }

        if (!"SERVED".equalsIgnoreCase(item.getStatus())) {
            return "Chi duoc danh gia mon da SERVED";
        }

        String validation = validateReview(rating, comment);
        if (!"VALID".equals(validation)) {
            return validation;
        }
        Review review = new Review();
        review.setCustomerId(customerId);
        review.setOrderId(orderId);
        review.setMenuItemId(menuItemId);
        review.setRating(rating);
        review.setComment(comment == null ? "" : comment.trim());
        boolean inserted = reviewDAO.insert(review);
        return inserted ? "SUCCESS" : "Them review mon that bai";
    }


    /*
     * Validate du lieu review
     */
    private String validateReview(int rating, String comment) {
        if (rating < 1 || rating > 5) {
            return "Rating phai nam trong khoang 1 den 5";
        }

        if (comment != null && comment.length() > 500) {
            return "Comment khong duoc vuot qua 500 ky tu";
        }
        return "VALID";
    }



    /*
     * Lay danh sach order da COMPLETED cua customer
     * Dung de hien thi truoc khi review
     */
    public List<Order> getCompletedOrdersOfCustomer(int customerId) {
        return orderDAO.findCompletedOrdersByCustomerId(customerId);
    }

    /*
     * Lay danh sach mon da SERVED cua 1 order
     * Dung de hien thi truoc khi review mon
     */
    public List<OrderItemView> getServedItemsOfOrder(int orderId) {
        return orderItemDAO.findServedItemsByOrderId(orderId);
    }
    /*
     * Lay lich su review cua customer
     */
    public List<Review> getMyReviews(int customerId) {
        return reviewDAO.findByCustomerId(customerId);
    }

}