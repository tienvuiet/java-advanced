package RestaurantManagement.ui;

import RestaurantManagement.model.User;
import RestaurantManagement.service.AuthService;
import RestaurantManagement.utils.InputUtil;

import java.util.Scanner;

public class AuthUI {
    private  Scanner sc = new Scanner(System.in);
    private final AuthService authService = new AuthService();
    private  final  ManagerUI managerUI = new ManagerUI();
    private  final  CustomerUI customerUI = new CustomerUI();
    private final  ChefUI chefUI = new ChefUI();
    public void start() {
        while (true) {
            System.out.println("\n==================================");
            System.out.println("   HE THONG QUAN LY NHA HANG");
            System.out.println("==================================");
            System.out.println("1. Dang nhap");
            System.out.println("2. Dang ky tai khoan khach hang");
            System.out.println("0. Thoat");
            System.out.println("==================================");
            System.out.println("Lua chon cua ban: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    showLogin();
                    break;
                case "2":
                    showRegisterCustomer();
                    break;
                case "0":
                    System.out.println("Tam biet!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    // Màn hình đăng nhập
    public void showLogin() {
        while (true) {
            System.out.println("\n===== DANG NHAP HE THONG =====");
            System.out.println("Nhap username: ");
            String username = sc.nextLine();
            System.out.println("Nhap password: ");
            String password = sc.nextLine();
            User user = authService.login(username, password);
            if (user == null) {
                System.out.println("Sai tai khoan hoac mat khau. Vui long nhap lai!");
                continue;
            }
            System.out.println("Dang nhap thanh cong!");
            System.out.println("Xin chao, " + user.getUsername());
            redirectByRole(user);
            break;
        }
    }

    // Màn hình đăng ký customer
    public void showRegisterCustomer() {
        System.out.println("\n===== DANG KY TAI KHOAN KHACH HANG =====");
        System.out.println("Nhap username dang ki: ");
        String username = sc.nextLine().trim();
        System.out.println("Nhap password dang ki: ");
        String password = sc.nextLine().trim();



        String result = authService.registerCustomer(username, password);

        if ("SUCCESS".equals(result)) {
            System.out.println("Dang ky tai khoan thanh cong!");
        } else {
            System.out.println(result);
        }
    }

    // Phân quyền
    private void redirectByRole(User user) {
        switch (user.getRole().toUpperCase()) {
            case "MANAGER":
                managerUI.setCurrentUser(user);
                managerUI.showManagerMenu();

                break;
            case "CHEF":
                chefUI.showChefMenu(user);
                break;
            case "CUSTOMER":
                customerUI.showCustomerMenu(user);
                break;
            default:
                System.out.println("Khong xac dinh duoc quyen cua tai khoan.");
        }
    }





}