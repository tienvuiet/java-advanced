package RestaurantManagement.dao;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public User findByUsername(String userName){
        String sql = "select * from users where username = ?";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setString(1, userName);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getString("status"));
                    return user;
                }
            }catch (Exception e){
                throw  new RuntimeException(e);
            }
        }catch (Exception e){
            throw  new RuntimeException("Loi tim user theo username", e);
        }
        return null;
    }

    // tim user theo id
    /*
     * Tim user theo id
     * Dung cho:
     * - vo hieu hoa tai khoan
     * - xem user co ton tai hay khong
     */
    public User findById(int id) {
        String sql = "select * from users where id = ?";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getString("status"));
                    return user;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim user theo id", e);
        }
        return null;
    }


    /*
     * Lay toan bo danh sach user
     * Dung cho Manager xem danh sach tai khoan trong he thong
     */
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "select * from users order by id";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                list.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach users", e);
        }
        return list;
    }

    public  boolean insertUser (User user){
        String sql = "insert into users(username, password_hash, role, status) values(?, ?, ?, ?)";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
              ps.setString(1,user.getUsername());
              ps.setString(2, user.getPasswordHash());
              ps.setString(3, user.getRole());
              ps.setString(4, user.getStatus());
              int rowAffected =  ps.executeUpdate() ;
              return rowAffected >0;

        } catch (Exception e) {
            throw new RuntimeException("Loi ",e);
        }

    }

    /*
     * Cap nhat trang thai user
     * Vi du:
     * ACTIVE -> INACTIVE
     */
    public boolean updateStatus(int userId, String status) {
        String sql = "update users set status = ? where id = ?";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, status);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi cap nhat status user", e);
        }
    }

    /*
     * Dem so luong manager dang ACTIVE
     *
     * Dung de chong truong hop:
     * - vo hieu hoa manager cuoi cung trong he thong
     */
    public int countActiveManagers() {
        String sql = "select count(*) as total from users where role = 'MANAGER' and status = 'ACTIVE'";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi dem so luong manager ACTIVE", e);
        }

        return 0;
    }
}
