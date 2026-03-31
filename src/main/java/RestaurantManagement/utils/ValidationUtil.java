package RestaurantManagement.utils;

import java.math.BigDecimal;

public class ValidationUtil {
     // validate mon an/ do uong
    public  static  String validateMenuItem(String name , String category, BigDecimal price, int stockQuantity){
        if (name == null || name.trim().isEmpty()){
            return "Ten mon khong duoc de trong";
        }
        if (name.trim().length() < 2 || name.trim().length() > 100){
            return "Ten mon phai tu 2 den 100 ki tu";
        }
        // chi chap nhan FOOD, DRINK
        if (!"FOOD".equalsIgnoreCase(category) && !"DRINK".equalsIgnoreCase(category)){
            return "Loai mon phai la DRINK or FOOD";
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            return "Gia mon phai la so duong";
        }
        // compareTo method ss 2 gia tri ob
        if (stockQuantity < 0){
            return "Ton kho khong duoc am";
        }
        return "VALID";
    }

    // validate ban an
    public static  String validateDiningTable(String table_number,int capacity,String status ){
        if (table_number == null || table_number.trim().isEmpty()){
            return "So ban khong duoc de trong";
        }
        // do dai ban
        if (table_number.trim().length() < 2 || table_number.trim().length() > 20){
            return "Ten ban phai trong khoan 2- 20 ki tu";
        }
        if (capacity < 0){
            return "suc chua phai > 0";
        }
        if (!"AVAILABLE".equalsIgnoreCase(status) && !"OCCUPIED".equalsIgnoreCase(status)){
            return "Trang thai ban phai la AVAILABLE or OCCUPIED";
        }
        return "VALID";
    }
}
