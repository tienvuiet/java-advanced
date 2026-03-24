package session11.db.test;

import session11.db.entity.Product;
import session11.db.repository.ProductRepository;
import session11.db.repository.impl.ProductRepositoryImpl;

import java.util.List;

public class displayPro {
    static void main(String[] args) {
        ProductRepository repo = new ProductRepositoryImpl();
        List<Product> products =  repo.getProducts();
        for (Product product: products){
            System.out.println(product);
        }
    }
}
