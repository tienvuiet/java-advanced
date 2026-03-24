package session14.MiniPrj.dao;

import session14.MiniPrj.Db.DBMiniprj;
import session14.MiniPrj.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAO {
     // lay danh sach user;
     public List<User> getAllUser(){
         List<User> listUser = new ArrayList<>();
         String sql = "select * from Users ";
         try(
                 Connection con = DBMiniprj.getConnectionMiniPrj();
                 PreparedStatement pr = con.prepareStatement(sql);
                 ResultSet rs = pr.executeQuery();
         ){
             while (rs.next()){
                 User u = new User();
                 u.setId(rs.getInt("id"));
                 u.setName(rs.getString("name"));
                 u.setEmail(rs.getString("email"));
                 listUser.add(u);
             }
         }catch (Exception e){
             System.out.println("Loi: " + e.getMessage());
         }
         return  listUser;
     }


     public boolean insertUser(User user){
         boolean result = false;
         String sql = "insert into Users( name, email) values(?, ?)";
         try(
                 Connection con = DBMiniprj.getConnectionMiniPrj();
                 PreparedStatement pr =con.prepareStatement(sql);
         ) {
                pr.setString(1, user.getName());
                pr.setString(2, user.getEmail());
                int i = pr.executeUpdate();
                if (i>0) result = true;
         }catch (Exception e){
             System.out.println("Loi: "+ e.getMessage());
         }
         return result;
     }

     public boolean updateUser(int idUpdate, String nameUpdate, String emailUpdate){
         boolean result = false;
         String sql = "update Users set name = ? , email = ? where id = ?";
         try(Connection con = DBMiniprj.getConnectionMiniPrj();
            PreparedStatement pr = con.prepareStatement(sql);
         ){
             pr.setString(1, nameUpdate);
             pr.setString(2, emailUpdate);
             pr.setInt(3, idUpdate);
             int i = pr.executeUpdate();
             if (i>0) result = true;
         }catch (Exception e){
             System.out.println("Loi: "+ e.getMessage());
         }
         return result;
     }
     public  boolean deleteUser(int idDelete){
         boolean result = false;
         String sql = "delete from Users where id = ?";
         try(
                 Connection con = DBMiniprj.getConnectionMiniPrj();
                 PreparedStatement pr = con.prepareStatement(sql);
                 ) {
                   pr.setInt(1, idDelete);
                   int i = pr.executeUpdate();
                   if(i > 0) result = true;
         } catch (Exception e) {
             System.out.println("loi: "+ e.getMessage());
         }
         return  result;
     }
     public void menu(Scanner sc){
         while (true){
             System.out.println("=== User Menu ===");
             System.out.println("1. Them User");
             System.out.println("2. Sua User");
             System.out.println("3. Xoa User");
             System.out.println("4. Xem danh sach User");
             System.out.println("0. Quay lai Menu chinh");
             System.out.print("Chon: ");
             int choice = Integer.parseInt(sc.nextLine());
             switch (choice){
                 case 1:
                     System.out.print("Nhập tên: ");
                     String name = sc.nextLine();
                     System.out.print("Nhập email: ");
                     String email = sc.nextLine();
                   boolean check =  insertUser(new User(0, name, email));
                   if (check){
                       System.out.println("them thanh cong");
                   }else{
                       System.out.println("That bai");
                   }
                     break;
                 case 2:
                     System.out.print("Nhập ID User cần sửa: ");
                     int idUpdate = Integer.parseInt(sc.nextLine());
                     System.out.print("Nhập tên mới: ");
                     String newName = sc.nextLine();
                     System.out.print("Nhập email mới: ");
                     String newEmail = sc.nextLine();
                     boolean ab = updateUser(idUpdate, newName, newEmail);
                     if(ab){
                         System.out.println("Sua thanh cong");
                     }else{
                         System.out.println("Sua that bai");
                     }
                     break;
                 case 3:
                     System.out.print("Nhập ID User cần xóa: ");
                     int idDelete = Integer.parseInt(sc.nextLine());
                    boolean delete =  deleteUser(idDelete);
                    if(delete){
                        System.out.println("Xoa thanh cong");
                    }else{
                        System.out.println("Xoa that bai");
                    }
                     break;
                 case 4:
                     List<User> users = getAllUser();
                     System.out.println("Danh sach user");
                     for (User u : users) System.out.println(u);
                     break;
                 case 0:
                     return;
             }
         }
     }
}
