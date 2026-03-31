package RestaurantManagement.service;

import RestaurantManagement.dao.UserDAO;
import RestaurantManagement.model.User;
import RestaurantManagement.utils.PasswordUtil;

import java.util.List;

public class UserManagementService {
    private final UserDAO userDAO = new UserDAO();
    /*
     * Lay tat ca user cho Manager xem
     */
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }
    /*
     * Tao tai khoan dau bep moi
     *
     * Rule:
     * - username khong duoc trong
     * - password khong duoc trong
     * - username khong duoc trung
     * - role luon la CHEF
     * - status mac dinh la ACTIVE
     * - password phai duoc hash
     */
    public String createChefAccount(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Username khong duoc de trong";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Password khong duoc de trong";
        }

        User existingUser = userDAO.findByUsername(username.trim());
        if (existingUser != null) {
            return "Username da ton tai, vui long chon username khac";
        }

        String hashedPassword = PasswordUtil.hashPassword(password);

        User chef = new User();
        chef.setUsername(username.trim());
        chef.setPasswordHash(hashedPassword);
        chef.setRole("CHEF");
        chef.setStatus("ACTIVE");

        boolean inserted = userDAO.insertUser(chef);
        return inserted ? "SUCCESS" : "Tao tai khoan dau bep that bai";
    }

    /*
     * Vo hieu hoa tai khoan
     *
     * Rule:
     * - user phai ton tai
     * - neu da INACTIVE roi thi khong can vo hieu hoa tiep
     * - neu la MANAGER va chi con 1 manager ACTIVE duy nhat
     *   -> KHONG DUOC vo hieu hoa
     */
    public String deactivateUser(int userId) {
        User existingUser = userDAO.findById(userId);

        if (existingUser == null) {
            return "Khong tim thay user co id = " + userId;
        }

        if ("INACTIVE".equalsIgnoreCase(existingUser.getStatus())) {
            return "Tai khoan nay da o trang thai INACTIVE";
        }

        /*
         * Chan vo hieu hoa manager duy nhat
         */
        if ("MANAGER".equalsIgnoreCase(existingUser.getRole())) {
            int activeManagerCount = userDAO.countActiveManagers();

            if (activeManagerCount <= 1) {
                return "Khong the vo hieu hoa quan ly duy nhat trong he thong";
            }
        }

        boolean updated = userDAO.updateStatus(userId, "INACTIVE");
        return updated ? "SUCCESS" : "Vo hieu hoa tai khoan that bai";
    }
}