package RestaurantManagement.utils;

import RestaurantManagement.model.DiningTable;
import RestaurantManagement.model.MenuItem;
import RestaurantManagement.model.User;

import java.util.List;

/*
 * Class này dùng để in dữ liệu ra màn hình theo dạng bảng.
 * Mục đích:
 * - hiển thị đẹp hơn
 * - căn lề thẳng hàng
 * - đúng tiêu chí chấm điểm
 */
public class TablePrinter {

    /*
     * In danh sách món theo dạng bảng
     */
    public static void printMenuItemTable(List<MenuItem> items) {
        System.out.println("================================= DANH SACH THUC DON =================================");
        System.out.printf("%-5s %-25s %-10s %-12s %-12s %-12s%n",
                "ID", "Ten mon", "Loai", "Gia", "Ton kho", "Trang thai");
        System.out.println("--------------------------------------------------------------------------------------");

        for (MenuItem item : items) {
            System.out.printf("%-5d %-25s %-10s %-12s %-12d %-12s%n",
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStockQuantity(),
                    item.isAvailable() ? "Con ban" : "Ngung ban");
        }
    }

    /*
     * In danh sách bàn theo dạng bảng
     */
    public static void printDiningTableTable(List<DiningTable> tables) {
        System.out.println("\n===================== DANH SACH BAN AN =====================");

        System.out.printf("%-5s %-20s %-15s %-15s%n",
                "ID", "So ban", "Suc chua", "Trang thai");

        System.out.println("-----------------------------------------------------------");

        for (DiningTable table : tables) {
            System.out.printf("%-5d %-20s %-15d %-15s%n",
                    table.getId(),
                    table.getTableNumber(),
                    table.getCapacity(),
                    table.getStatus());
        }
    }

    /*
     * In chi tiết 1 món
     * Dùng khi sửa hoặc xóa để hiển thị thông tin hiện tại
     */
    public static void printSingleMenuItem(MenuItem item) {
        if (item == null) return;

        System.out.println("\nThong tin mon hien tai:");
        System.out.println("ID: " + item.getId());
        System.out.println("Ten mon: " + item.getName());
        System.out.println("Loai: " + item.getCategory());
        System.out.println("Gia: " + item.getPrice());
        System.out.println("Ton kho: " + item.getStockQuantity());
        System.out.println("Trang thai: " + (item.isAvailable() ? "Con ban" : "Ngung ban"));
    }

    /*
     * In chi tiết 1 bàn
     */
    public static void printSingleDiningTable(DiningTable table) {
        if (table == null) return;

        System.out.println("\nThong tin ban hien tai:");
        System.out.println("ID: " + table.getId());
        System.out.println("So ban: " + table.getTableNumber());
        System.out.println("Suc chua: " + table.getCapacity());
        System.out.println("Trang thai: " + table.getStatus());
    }

    // in danh sach user -> cho manager nhin
    public static void printUserTable(List<User> users) {
        System.out.println("\n===================== DANH SACH NGUOI DUNG =====================");
        System.out.printf("%-5s %-20s %-15s %-15s%n",
                "ID", "Username", "Role", "Status");
        System.out.println("---------------------------------------------------------------");
        for (User user : users) {
            System.out.printf("%-5d %-20s %-15s %-15s%n",
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getStatus());
        }
    }

    // in thong tin chi tiet 1 user
    public static void printSingleUser(User user) {
        if (user == null) return;
        System.out.println("\nThong tin user hien tai:");
        System.out.println("ID: " + user.getId());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Role: " + user.getRole());
        System.out.println("Status: " + user.getStatus());
    }


}