package session14.MiniPrj.dao;


import session14.MiniPrj.Db.DBMiniprj;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class ReportDAO {
    public  void getTopBuyers(){
        String sql = "{call SP_GetTopBuyers()}";
        try(
                Connection con = DBMiniprj.getConnectionMiniPrj();
                CallableStatement cs = con.prepareCall(sql);
                ResultSet rs = cs.executeQuery();
        ){
            System.out.println("Top 5 khach hang");
            while (rs.next()){
                System.out.println(
                        rs.getInt("id") +" | "+
                                rs.getString("name")+ " | "+
                                rs.getInt("total_quantity")
                );
            }
        }catch (Exception e){
            System.out.println("loi: "+ e.getMessage());
        }
    }

    public void  getRevenueByCategory(String category){
        String sql = "call SP_GetRevenueByCategory(?)";
        try(Connection con = DBMiniprj.getConnectionMiniPrj();
           CallableStatement cs = con.prepareCall(sql);
        ){
            cs.setString(1, category);
            try( ResultSet rs = cs.executeQuery();) {
                System.out.println("Doanh thu cua danh muc: "+ category);
                while (rs.next()){
                    System.out.println(
                            rs.getString("category") + " | Doanh thu: "+
                                    rs.getDouble("total_revenue")
                    );
                }
            }
        }catch (Exception e){
            System.out.println("Loi:" + e.getMessage());
        }
    }
}
