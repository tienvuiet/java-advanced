package RestaurantManagement.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    // ham ma hoa mat khau thanh chuoi hash
    public static String hashPassword(String rawPassword){
       return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
       // so 12 la do kho
    }
    public static  boolean verifyPassword(String rawPassword, String hashedPassword){
        if (rawPassword == null|| hashedPassword == null){
            return false;
        }
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
