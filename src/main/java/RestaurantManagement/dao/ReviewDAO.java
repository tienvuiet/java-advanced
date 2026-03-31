package RestaurantManagement.dao;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class ReviewDAO {
    /*
     * Them review moi vao bang reviews
     *
     * menu_item_id co the null:
     * - null -> review cho order / nha hang
     * - co gia tri -> review cho mon cu the
     */
    public boolean insert(Review review) {
        String sql = """
                INSERT INTO reviews(customer_id, order_id, menu_item_id, rating, comment)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, review.getCustomerId());
            ps.setInt(2, review.getOrderId());

            if (review.getMenuItemId() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
                // tranh loi NullPointerException
            } else {
                ps.setInt(3, review.getMenuItemId());
            }
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getComment());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi them review", e);
        }
    }

    /*
     * Lay tat ca review cua 1 customer
     * Dung khi muon xem lich su danh gia
     */
    public List<Review> findByCustomerId(int customerId) {
        List<Review> list = new ArrayList<>();

        String sql = "SELECT * FROM reviews WHERE customer_id = ? ORDER BY created_at DESC";

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setId(rs.getInt("id"));
                    review.setCustomerId(rs.getInt("customer_id"));
                    review.setOrderId(rs.getInt("order_id"));

                    int menuItemId = rs.getInt("menu_item_id");
                    if (rs.wasNull()) {
                        review.setMenuItemId(null);
                    } else {
                        review.setMenuItemId(menuItemId);
                    }

                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    review.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(review);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay review theo customer", e);
        }

        return list;
    }
}