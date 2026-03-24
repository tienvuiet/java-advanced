package session11.db.repository.impl;

import session11.db.DBUtility;
import session11.db.entity.Product;
import session11.db.repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Connection con;
        Statement stmt = null;
        ResultSet rs = null;


        con = DBUtility.getConnection();
        try {
            stmt = con.createStatement();
            String  sql = "select * from products";
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                Product product = new Product();
                product.setProId(rs.getInt("product_id"));
                product.setProName(rs.getString("product_name"));
                product.setProducer(rs.getString("producer"));
                product.setYearMaking(rs.getInt("year_making"));
                product.setExpiryDate(rs.getDate("expire_date"));
                product.setPrice(rs.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtility.closeAll(con);
        }
        return  products;

    }

    @Override
    public boolean addProduct(Product product) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtility.getConnection();

            String sql = "INSERT INTO products(product_name, producer, year_making, expire_date, price) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, product.getProName());
            ps.setString(2, product.getProducer());
            ps.setInt(3, product.getYearMaking());
            ps.setDate(4, new java.sql.Date(product.getExpiryDate().getTime()));
            ps.setDouble(5, product.getPrice());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public boolean updateproduct(Integer proId, Product product) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtility.getConnection();
            String sql = "UPDATE products SET product_name = ?, producer = ?, year_making = ?, expire_date = ?, price = ? WHERE product_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, product.getProName());
            ps.setString(2, product.getProducer());
            ps.setInt(3, product.getYearMaking());
            ps.setDate(4, new java.sql.Date(product.getExpiryDate().getTime()));
            ps.setDouble(5, product.getPrice());
            ps.setInt(6, proId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public boolean deleteProduct(Integer proId) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtility.getConnection();
            String sql = "DELETE FROM products WHERE product_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, proId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                result = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
