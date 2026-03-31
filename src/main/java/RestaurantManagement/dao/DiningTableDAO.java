package RestaurantManagement.dao;

import RestaurantManagement.config.DBConnection;
import RestaurantManagement.model.DiningTable;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiningTableDAO {
    // lay toan bo danh sach ban
    public List<DiningTable> findAll(){

        List<DiningTable> list = new ArrayList<>();
        String sql  = "select * from dining_tables";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                ){
             while (rs.next()){
                 DiningTable diningTable = new DiningTable();
                 diningTable.setId(rs.getInt("id"));
                 diningTable.setTableNumber((rs.getString("table_number")));
                 diningTable.setCapacity(rs.getInt("capacity"));
                 diningTable.setStatus(rs.getString("status"));
                 list.add(diningTable);
             }
             return  list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //tim ban theo id
    public  DiningTable findById(int id){
        DiningTable diningTable = new DiningTable();
        String sql = "select * from dining_tables where id = ?";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
                ps.setInt(1, id);
                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()){
                        diningTable.setId(rs.getInt("id"));
                        diningTable.setTableNumber((rs.getString("table_number")));
                        diningTable.setCapacity(rs.getInt("capacity"));
                        diningTable.setStatus(rs.getString("status"));
                        return  diningTable;
                    }
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  null;
    }
    // tim ban theo ten gan dung
    public  List<DiningTable> findByTableNumberLike(String key){
        List<DiningTable> list = new ArrayList<>();

        String sql = "select * from dining_tables where table_number like  ?";
        try(
            Connection con = DBConnection.getConnectionRM();
            PreparedStatement ps = con.prepareStatement(sql);
                ) {
            ps.setString(1, "%" +  key+ "%");
            try {
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    DiningTable diningTable = new DiningTable();
                    diningTable.setId(rs.getInt("id"));
                    diningTable.setTableNumber((rs.getString("table_number")));
                    diningTable.setCapacity(rs.getInt("capacity"));
                    diningTable.setStatus(rs.getString("status"));
                    list.add(diningTable);
                }
                return  list;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // lay danh sach ban dang trong
    public List<DiningTable> findAvailableTables() {
        List<DiningTable> list = new ArrayList<>();
        String sql = "select * from dining_tables where status = 'AVAILABLE' order by id";

        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                DiningTable diningTable = new DiningTable();
                diningTable.setId(rs.getInt("id"));
                diningTable.setTableNumber(rs.getString("table_number"));
                diningTable.setCapacity(rs.getInt("capacity"));
                diningTable.setStatus(rs.getString("status"));
                list.add(diningTable);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Loi lay danh sach ban trong", e);
        }
    }

    // ham them moi
    public  boolean insert(DiningTable diningTable){
        String sql = "insert into dining_tables(table_number, capacity, status) values (?, ? , ?)";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setString(1, diningTable.getTableNumber());
            ps.setInt(2, diningTable.getCapacity());
            ps.setString(3, diningTable.getStatus());
            return  ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // cap nhat ban
    public  boolean update(DiningTable diningTable){
        String sql = "update  dining_tables set table_number = ?, capacity = ?, status = ? where  id = ?";
        try(
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setString(1, diningTable.getTableNumber());
            ps.setInt(2, diningTable.getCapacity());
            ps.setString(3, diningTable.getStatus());
            ps.setInt(4, diningTable.getId());
            return  ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // cap nhat trang thai ban bang transaction connection
    public boolean updateStatus(Connection con, int tableId, String status) {
        String sql = "update dining_tables set status = ? where id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, tableId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Loi cap nhat trang thai ban", e);
        }
    }
    // ham xoa
    public  boolean delete(int id){
        String sql = "delete from dining_tables where id = ?";
        try (
                Connection con = DBConnection.getConnectionRM();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setInt(1, id);
            return  ps.executeUpdate()>0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    // thanh toan
    // doi trang thai ban sau khi thanh toan
    // ban dang occupied -> available

}
