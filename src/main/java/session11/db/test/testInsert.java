package session11.db.test;

import session11.db.entity.Product;
import session11.db.repository.impl.ProductRepositoryImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class testInsert {
    static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Product product = new Product(0,"Am dun nuoc", "dell", 2004, sdf.parse("21/02/2004"), 23000.0);
            boolean result =  new ProductRepositoryImpl().addProduct(product);
            if (result == true){
                System.out.println("Da them san pham thanh cong");
            }else{
                System.out.println("them san pham that bai");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }
}
