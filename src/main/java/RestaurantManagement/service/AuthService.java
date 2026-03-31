package RestaurantManagement.service;

import RestaurantManagement.dao.UserDAO;
import RestaurantManagement.model.User;
import RestaurantManagement.utils.PasswordUtil;

public class AuthService {
    private  final UserDAO userDAO = new UserDAO();
    public User login(String username, String password){
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()){
            return null;
        }
        User user = userDAO.findByUsername(username.trim());
        if (user == null){
            return null;
        }
        if(!"ACTIVE".equalsIgnoreCase(user.getStatus())){
            System.out.println("Tai khoan nay da bi vo hieu hoa");
            return null;
        }
        boolean isCorrectPassword = PasswordUtil.verifyPassword(password, user.getPasswordHash());
        if (!isCorrectPassword){
            return null;
        }
        return user;
    }

    // dang ki
    public  String registerCustomer(String username, String password){
        if (username == null| username.trim().isEmpty()){
            return "Username khong duoc de trong";
        }
        if (password == null || password.trim().isEmpty()){
            return "Password khong duoc de trong";
        }
        User existingUser = userDAO.findByUsername(username.trim());
        if (existingUser != null){
            return "Username da ton tai vui long chon username khac";
        }
        String hashedPassword = PasswordUtil.hashPassword(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(hashedPassword);
        newUser.setStatus("ACTIVE");
        newUser.setRole("CUSTOMER");
        Boolean insertUser = userDAO.insertUser(newUser);
        if (insertUser){
            return "SUCCESS";
        }
        return "Dang ki that bai. Thu lai";
    }
}
