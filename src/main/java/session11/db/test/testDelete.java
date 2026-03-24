package session11.db.test;

import session11.db.repository.impl.ProductRepositoryImpl;

public class testDelete {
    static void main(String[] args) {
        boolean result =  new ProductRepositoryImpl().deleteProduct(4);
        if (result == true){
            System.out.println("Xoa san pham thanh cong");
        }else{
            System.out.println("Xoa san pham that bai");
        }

    }
}
