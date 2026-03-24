package session11.db.test;

import session11.db.entity.Product;
import session11.db.repository.impl.ProductRepositoryImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class testUpdate {
    static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Product product = new Product(4,"Tien dep trai", "dell", 2004, sdf.parse("21/02/2004"), 3200.0);
            boolean result =  new ProductRepositoryImpl().updateproduct(4,product);
            if (result == true){
                System.out.println("Da cap nhat san pham thanh cong");
            }else{
                System.out.println("Cap nhat san pham that bai");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
