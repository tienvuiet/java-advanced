package RestaurantManagement.dao;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.model.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//Connection → mở kết nối
//PreparedStatement → gửi lệnh SQL
//ResultSet → nhận và đọc dữ liệu
public class MenuItemDAO {
    // lay danh sach mon an
    public List<MenuItem> findAll(){

        List<MenuItem> list = new ArrayList<>();
        String sql = "select * from menu_items ";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ){
            while (rs.next()){
                MenuItem menuItem = new MenuItem();
                menuItem.setId(rs.getInt("id"));
                menuItem.setName(rs.getString("name"));
                menuItem.setCategory(rs.getString("category"));
                menuItem.setPrice(rs.getBigDecimal("price"));
                menuItem.setStockQuantity(rs.getInt("stock_quantity"));
                menuItem.setAvailable(rs.getBoolean("is_available"));
                list.add(menuItem);
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach mon an",e);
        }
        return list;
    }
    // tim mon theo id
    public  MenuItem findById(int id){
        String sql = "select * from menu_items where id = ?";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setInt(1,id);
            try (  ResultSet rs = ps.executeQuery();){
                if (rs.next()){
                    MenuItem menuItem = new MenuItem();
                    menuItem.setId(rs.getInt("id"));
                    menuItem.setName(rs.getString("name"));
                    menuItem.setCategory(rs.getString("category"));
                    menuItem.setPrice(rs.getBigDecimal("price"));
                    menuItem.setStockQuantity(rs.getInt("stock_quantity"));
                    menuItem.setAvailable(rs.getBoolean("is_available"));
                    return  menuItem;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi tim mon an theo id",e);
        }
        return null;
    }
    // tim kiem mon an theo ten gab dyng
    public  List<MenuItem> findByNameLike(String key){
        List<MenuItem> list = new ArrayList<>();
        String sql = "select * from menu_items where name like ?";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ) {
                ps.setString(1, "%" + key +"%");
                try(ResultSet rs = ps.executeQuery();){
                    while (rs.next()){
                        MenuItem menuItem = new MenuItem();
                        menuItem.setId(rs.getInt("id"));
                        menuItem.setName(rs.getString("name"));
                        menuItem.setCategory(rs.getString("category"));
                        menuItem.setPrice(rs.getBigDecimal("price"));
                        menuItem.setStockQuantity(rs.getInt("stock_quantity"));
                        menuItem.setAvailable(rs.getBoolean("is_available"));
                        list.add(menuItem);
                    }
                }
                return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi tim kiem mon an theo ten gan dung",e);
        }

    }
    // them mon moi vao db
    public  boolean insert(MenuItem menuItem){
        String sql = "insert into menu_items(name, category, price, stock_quantity, is_available) values (?, ?, ?, ?, ?)";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setString(1, menuItem.getName());
            ps.setString(2, menuItem.getCategory());
            ps.setBigDecimal(3, menuItem.getPrice());
            ps.setInt(4, menuItem.getStockQuantity());
            ps.setBoolean(5, menuItem.isAvailable());
            return  ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi insert",e);
        }
    }

    // cap nhat mon theo id;
    public  boolean update(MenuItem menuItem){
        String sql = "update menu_items set name = ?, category = ?, price = ?, stock_quantity = ?, is_available = ? where  id = ?";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setString(1, menuItem.getName());
            ps.setString(2, menuItem.getCategory());
            ps.setBigDecimal(3, menuItem.getPrice());
            ps.setInt(4, menuItem.getStockQuantity());
            ps.setBoolean(5, menuItem.isAvailable());
            ps.setInt(6, menuItem.getId());
            return  ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // xoa mon theo id
    public boolean detele(int id){
        String sql = "delete from menu_items where id = ?";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setInt(1, id);
            return  ps.executeUpdate()>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
