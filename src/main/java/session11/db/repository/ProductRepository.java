package session11.db.repository;

import session11.db.entity.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> getProducts();
    boolean addProduct(Product product);
    boolean updateproduct(Integer proId, Product product);
    boolean deleteProduct(Integer proId);
}
