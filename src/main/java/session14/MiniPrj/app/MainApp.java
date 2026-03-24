package session14.MiniPrj.app;

import session14.MiniPrj.dao.FlashSaleService;
import session14.MiniPrj.dao.ReportDAO;
import session14.MiniPrj.dao.UserDAO;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        ReportDAO reportDAO = new ReportDAO();
        while(true) {
            System.out.println("==== FLASH SALE MENU ====");
            System.out.println("1. Quan ly Users");
            System.out.println("2. Quan ly Products");
            System.out.println("3. Dat hang Flash Sale");
            System.out.println("4. Xem Orders");
            System.out.println("5. Bao cao top Top Buyers");
            System.out.println("6. Bao cao doanh thu theo danh muc");
            System.out.println("0. Thoat");
            System.out.print("Lua chon cua ban: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch(choice) {
                case 1:
                    // Gọi menu Users (thêm/sửa/xóa/ds)
                    userDAO.menu(sc);
                    break;
//                case 2:
//                    // Gọi menu Products (thêm/sửa/xóa/ds)
//                    productDAO.menu(sc);
//                    break;
                case 3:
                    // Đặt hàng Flash Sale
                    FlashSaleService service = new FlashSaleService();

                    System.out.print("userId: ");
                    int userId = Integer.parseInt(sc.nextLine());

                    System.out.print("productId: ");
                    int productId = Integer.parseInt(sc.nextLine());

                    System.out.print("số lượng: ");
                    int quantity = Integer.parseInt(sc.nextLine());

                    service.placeOrder(userId, productId, quantity);
                    // het hang
//                    service.placeOrder(1, 4, 1000);
                    // ok 1 1 2
                    break;
//                case 4:
//                    // Xem Orders
//                    orderDAO.menu(sc);
//                    break;
                case 5:
                    // Top buyers
                     reportDAO.getTopBuyers();
                    break;
                case 6:
//                    // Doanh thu theo danh mục
                    System.out.print("Nhap danh muc: ");
                    String cat = sc.nextLine();
                    reportDAO.getRevenueByCategory(cat);
                    break;
                case 0:
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}
