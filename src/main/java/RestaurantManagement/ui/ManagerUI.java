package RestaurantManagement.ui;

import RestaurantManagement.model.DiningTable;
import RestaurantManagement.model.MenuItem;
import RestaurantManagement.model.OrderItemView;
import RestaurantManagement.model.User;
import RestaurantManagement.service.MenuService;
import RestaurantManagement.service.PaymentService;
import RestaurantManagement.service.TableService;
import RestaurantManagement.service.UserManagementService;
import RestaurantManagement.utils.InputUtil;
import RestaurantManagement.utils.TablePrinter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ManagerUI {
    private final MenuService menuService = new MenuService();
    private final TableService tableService = new TableService();
    private final PaymentService paymentService = new PaymentService();
    private final UserManagementService userManagementService = new UserManagementService();

    private Scanner sc = new Scanner(System.in);
    private User currentUser;
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    /*
     * Menu chính của Manager
     */
    public void showManagerMenu() {
        while (true) {
            System.out.println("\n=================================================");
            System.out.println("        HE THONG QUAN LY NHA HANG - MANAGER");
            System.out.println("=================================================");
            System.out.println("1. Quan ly thuc don");
            System.out.println("2. Quan ly ban an");
            System.out.println("3. Thanh toan & Hoa don");
            System.out.println("4. Quan ly nguoi dung");
            System.out.println("0. Dang xuat");
            System.out.println("=================================================");
            System.out.println("Chon chuc nang: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    showMenuManagement();
                    break;
                case "2":
                    showTableManagement();
                    break;
                case "3":
                    showPaymentMenu();
                    break;
                case "4":
                    showUserManagementMenu();
                    break;
                case "0":
                    System.out.println("Dang xuat khoi menu Manager...");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    /*
     * Menu con để quản lý thực đơn
     */
    private void showMenuManagement() {
        while (true) {
            System.out.println("\n================ QUAN LY THUC DON ================");
            System.out.println("1. Hien thi danh sach mon");
            System.out.println("2. Them mon");
            System.out.println("3. Sua mon");
            System.out.println("4. Xoa mon");
            System.out.println("5. Tim kiem mon theo ten");
            System.out.println("0. Quay lai");
            System.out.println("==================================================");
            System.out.println("Chon chuc nang: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    displayAllMenuItems();
                    break;
                case "2":
                    addMenuItem();
                    break;
                case "3":
                    updateMenuItem();
                    break;
                case "4":
                    deleteMenuItem();
                    break;
                case "5":
                    searchMenuItemByName();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    /*
     * Menu con để quản lý bàn ăn
     */
    private void showTableManagement() {
        while (true) {
            System.out.println("\n================ QUAN LY BAN AN ==================");
            System.out.println("1. Hien thi danh sach ban");
            System.out.println("2. Them ban");
            System.out.println("3. Sua ban");
            System.out.println("4. Xoa ban");
            System.out.println("5. Tim kiem ban theo so ban");
            System.out.println("0. Quay lai");
            System.out.println("==================================================");
            System.out.println("Chon chuc nang: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    displayAllTables();
                    break;
                case "2":
                    addDiningTable();
                    break;
                case "3":
                    updateDiningTable();
                    break;
                case "4":
                    deleteDiningTable();
                    break;
                case "5":
                    searchDiningTableByNumber();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    // quan ly mon an
    // hien thi danh sach mon
    private  void displayAllMenuItems(){
        List<MenuItem> items = menuService.getAllMenuItems();
        if (items.isEmpty()){
            System.out.println("Danh sach mon an trong");
        }
        TablePrinter.printMenuItemTable(items);
    }
    // them mon an
    private  void   addMenuItem(){
        System.out.println("---------------THEM MON MOI---------------");
        System.out.println("Nhap ten mon: ");
        String name = sc.nextLine().trim();
        System.out.println("Nhap loai mon (DRINK/FOOD): ");
        String category = sc.nextLine().trim().toUpperCase();
        System.out.println("Nhap gia mon: ");
        BigDecimal price =  new BigDecimal(sc.nextLine());

        // chi nhap ton kho khi la dink
        int stockQuantity = 0;
        if("DRINK".equalsIgnoreCase(category)){
            try {
                System.out.println("Nhap so luong ton kho: ");
                stockQuantity = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                throw new RuntimeException("So luong ton kho khong hop le",e);
            }
        }
        System.out.println("Con ban khong? (Y/N): ");
        String availableInput = sc.nextLine().trim();
        boolean isAvailable = "Y".equals(availableInput);
        String result = menuService.addMenuItem(name, category, price, stockQuantity, isAvailable);
        if ("SUCCESS".equals(result)){
            System.out.println("Them mon thanh cong");
        }else{
            System.out.println(result);
        }
    }
    // sua mon an
    private  void  updateMenuItem(){
        System.out.println("-------------SUA MON------------");
        System.out.println("Nhap id mon can sua");
        int id = sc.nextInt();
        sc.nextLine();
        MenuItem existingItem = menuService.getMenuItemById(id);
        if (existingItem == null){
            System.out.println("Khong tim thay mon co id: " + id);
            return ;
        }
        TablePrinter.printSingleMenuItem(existingItem);

        System.out.println("Nhap ten mon moi: ");
        String name = sc.nextLine().trim();
        System.out.println("Nhap the loai moi (DRINK/FOOD): ");
        String category = sc.nextLine().trim().toUpperCase();
        System.out.println("Nhap gia moi: ");
        BigDecimal price =  new BigDecimal(sc.nextLine());
        // chi nhap ton kho khi la dink
        int stockQuantity = 0;
        if("DRINK".equalsIgnoreCase(category)){
            try {
                System.out.println("Nhap so luong ton kho moi: ");
                stockQuantity = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                throw new RuntimeException("So luong ton kho khong hop le",e);
            }
        }
        System.out.println("Con ban khong? (Y/N): ");
        String availableInput = sc.nextLine().trim();
        boolean isAvailable = "Y".equals(availableInput);

        String result = menuService.updateMenuItem(id, name, category, price, stockQuantity, isAvailable);
        if ("SUCCESS".equals(result)){
            System.out.println("Cap nhat mon thanh cong");
        }else{
            System.out.println(result);
        }

    }
    //deleteMenuItem();
    private void deleteMenuItem(){
        System.out.println("--------------XOA MON---------------");
        System.out.println("Nhap id mon can xoa: ");
        int id = sc.nextInt();
        sc.nextLine();
        MenuItem existingItem = menuService.getMenuItemById(id);
        if (existingItem == null) {
            System.out.println("Khong tim thay mon co id = " + id);
            return;
        }
        TablePrinter.printSingleMenuItem(existingItem);
        System.out.println("Ban co chac chan muon xoa? (Y/N): ");
        String confirm = sc.nextLine().trim().toUpperCase();
        if (!"Y".equals(confirm)) {
            System.out.println("Da huy thao tac xoa.");
            return;
        }
        String result = menuService.deleteMenuItem(id);
        if ("SUCCESS".equals(result)){
            System.out.println("Xoa mon thanh cong");
        }else{
            System.out.println(result);
        }
    }
    // tim kiem mon theo ten gan dung
    private  void searchMenuItemByName(){
        System.out.println("\n===== TIM KIEM MON THEO TEN =====");

        System.out.println("Nhap ten mon muon tim: ");
        String keyword = sc.nextLine().trim();
        List<MenuItem> results = menuService.searchMenuItemByName(keyword);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay mon nao phu hop.");
            return;
        }
        TablePrinter.printMenuItemTable(results);
    }




    // ================= QUAN LY BAN =================

    // hien thi danh sach ban
    private void displayAllTables() {
        List<DiningTable> tables = tableService.getAllTables();
        if (tables.isEmpty()) {
            System.out.println("Danh sach ban trong");
            return;
        }
        TablePrinter.printDiningTableTable(tables);
    }

    // them ban moi
    private void addDiningTable() {
        System.out.println("---------------THEM BAN MOI---------------");

        System.out.println("Nhap so ban: ");
        String tableNumber = sc.nextLine().trim();

        System.out.println("Nhap suc chua: ");
        int capacity;
        try {
            capacity = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Suc chua khong hop le");
            return;
        }

        System.out.println("Nhap trang thai (AVAILABLE/OCCUPIED): ");
        String status = sc.nextLine().trim().toUpperCase();

        String result = tableService.addTable(tableNumber, capacity, status);
        if ("SUCCESS".equals(result)) {
            System.out.println("Them ban thanh cong");
        } else {
            System.out.println(result);
        }
    }

    // sua ban
    private void updateDiningTable() {
        System.out.println("---------------SUA BAN---------------");
        displayAllTables();
        System.out.println("Nhap id ban can sua: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("ID khong hop le");
            return;
        }

        DiningTable existingTable = tableService.getTableById(id);
        if (existingTable == null) {
            System.out.println("Khong tim thay ban co id = " + id);
            return;
        }

        TablePrinter.printSingleDiningTable(existingTable);

        System.out.println("Nhap so ban moi: ");
        String tableNumber = sc.nextLine().trim();

        System.out.println("Nhap suc chua moi: ");
        int capacity;
        try {
            capacity = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Suc chua khong hop le");
            return;
        }

        System.out.println("Nhap trang thai moi (AVAILABLE/OCCUPIED): ");
        String status = sc.nextLine().trim().toUpperCase();

        String result = tableService.updateTable(id, tableNumber, capacity, status);
        if ("SUCCESS".equals(result)) {
            System.out.println("Cap nhat ban thanh cong");
        } else {
            System.out.println(result);
        }
    }

    // xoa ban
    private void deleteDiningTable() {
        System.out.println("---------------XOA BAN---------------");
        displayAllTables();
        System.out.println("Nhap id ban can xoa: ");
        int id;
        try {
            id = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("ID khong hop le");
            return;
        }

        DiningTable existingTable = tableService.getTableById(id);
        if (existingTable == null) {
            System.out.println("Khong tim thay ban co id = " + id);
            return;
        }

        TablePrinter.printSingleDiningTable(existingTable);

        System.out.println("Ban co chac chan muon xoa? (Y/N): ");
        String confirm = sc.nextLine().trim().toUpperCase();
        if (!"Y".equals(confirm)) {
            System.out.println("Da huy thao tac xoa.");
            return;
        }

        String result = tableService.deleteTable(id);
        if ("SUCCESS".equals(result)) {
            System.out.println("Xoa ban thanh cong");
        } else {
            System.out.println(result);
        }
    }

    // tim kiem ban theo so ban gan dung
    private void searchDiningTableByNumber() {
        System.out.println("\n===== TIM KIEM BAN THEO SO BAN =====");

        System.out.println("Nhap so ban muon tim: ");
        String keyword = sc.nextLine().trim();

        List<DiningTable> results = tableService.searchTablesByNumber(keyword);
        if (results.isEmpty()) {
            System.out.println("Khong tim thay ban nao phu hop.");
            return;
        }

        TablePrinter.printDiningTableTable(results);
    }




    // ==================THANH TOAN
    private void showPaymentMenu() {
        while (true) {
            System.out.println("\n================ THANH TOAN & HOA DON ================");
            System.out.println("1. Xem truoc hoa don theo ban");
            System.out.println("2. Thanh toan ban");
            System.out.println("0. Quay lai");
            System.out.println("======================================================");
            System.out.print("Chon chuc nang: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    previewInvoiceByTable();
                    break;
                case "2":
                    checkoutTable();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    //xem truoc hoa don thanh toan
    private void previewInvoiceByTable() {
        try {
            displayAllTablesForPayment();

            System.out.print("Nhap id ban can xem hoa don: ");
            int tableId = Integer.parseInt(sc.nextLine().trim());
            List<OrderItemView> items = paymentService.preview(tableId);

            if (items.isEmpty()) {
                System.out.println("Khong co mon trừ CANCELLED va REJECTED de hien thi hoa don.");
                return;
            }
            System.out.println("\n================ HOA DON TAM TINH ================");
            System.out.printf("%-12s %-25s %-10s %-12s %-12s%n",
                    "OrderItemID", "Ten mon", "So luong", "Don gia", "Thanh tien");
            System.out.println("--------------------------------------------------------------");

            BigDecimal total = BigDecimal.ZERO;

            for (OrderItemView item : items) {
                BigDecimal lineTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(lineTotal);

                System.out.printf("%-12d %-25s %-10d %-12s %-12s%n",
                        item.getOrderItemId(),
                        item.getMenuItemName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        lineTotal);
            }

            System.out.println("Tong thanh toan: " + total);

        } catch (Exception e) {
            System.out.println("ID ban khong hop le.");
        }
    }

    // thanh toan ban

    private void checkoutTable() {
        try {
            displayAllTablesForPayment();

            System.out.print("Nhap id ban can thanh toan: ");
            int tableId = Integer.parseInt(sc.nextLine().trim());

            // Xem truoc cac mon se duoc tinh tien
            List<OrderItemView> items = paymentService.preview(tableId);
            if (items.isEmpty()) {
                System.out.println("Khong co mon tru REJECTED va CANCELLED de thanh toan.");
                return;
            }

            System.out.println("\n================ CAC MON SE DUOC THANH TOAN ================");
            System.out.printf("%-12s %-25s %-10s %-12s %-12s%n",
                    "OrderItemID", "Ten mon", "So luong", "Don gia", "Thanh tien");
            System.out.println("--------------------------------------------------------------");

            BigDecimal total = BigDecimal.ZERO;
//            multiply:  phép nhân
            for (OrderItemView item : items) {
                BigDecimal lineTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(lineTotal);

                System.out.printf("%-12d %-25s %-10d %-12s %-12s%n",
                        item.getOrderItemId(),
                        item.getMenuItemName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        lineTotal);
            }

            System.out.println("Tong tien: " + total);

            System.out.print("Xac nhan thanh toan? (Y/N): ");
            String confirm = sc.nextLine().trim().toUpperCase();

            if (!"Y".equals(confirm)) {
                System.out.println("Da huy thanh toan.");
                return;
            }

            String result = paymentService.checkout(tableId);

            if (result.startsWith("SUCCESS:")) {
                System.out.println("Thanh toan thanh cong!");
                System.out.println("Tong tien hoa don = " + result.replace("SUCCESS:", "").trim());
                System.out.println("Ban da duoc tra ve trang thai AVAILABLE.");
            } else {
                System.out.println(result);
            }

        } catch (Exception e) {
            System.out.println("ID ban khong hop le.");
        }
    }

    private void displayAllTablesForPayment() {
        List<DiningTable> tables = tableService.getAllTables();

        if (tables.isEmpty()) {
            System.out.println("Khong co ban nao trong he thong.");
            return;
        }

        System.out.println("\n================ DANH SACH TAT CA CAC BAN =================");
        TablePrinter.printDiningTableTable(tables);
    }










    // quan ly nguoi dung
    private void showUserManagementMenu() {
        while (true) {
            System.out.println("\n================ QUAN LY NGUOI DUNG ================");
            System.out.println("1. Hien thi danh sach nguoi dung");
            System.out.println("2. Tao tai khoan dau bep moi");
            System.out.println("3. Vo hieu hoa tai khoan");
            System.out.println("0. Quay lai");
            System.out.println("====================================================");
            System.out.print("Chon chuc nang: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    displayAllUsers();
                    break;
                case "2":
                    createChefAccount();
                    break;
                case "3":
                    deactivateUser();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }
    private void displayAllUsers() {
        List<User> users = userManagementService.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("Danh sach nguoi dung trong.");
            return;
        }
        TablePrinter.printUserTable(users);
    }
    private void createChefAccount() {
        System.out.println("\n=============== TAO TAI KHOAN DAU BEP ===============");
        System.out.print("Nhap username cho dau bep moi: ");
        String username = sc.nextLine().trim();
        System.out.print("Nhap password cho dau bep moi: ");
        String password = sc.nextLine().trim();
        String result = userManagementService.createChefAccount(username, password);
        if ("SUCCESS".equals(result)) {
            System.out.println("Tao tai khoan dau bep thanh cong!");
        } else {
            System.out.println(result);
        }
    }
    private void deactivateUser() {
        displayAllUsers();
        try {
            System.out.print("Nhap id user can vo hieu hoa: ");
            int userId = Integer.parseInt(sc.nextLine().trim());
            if (currentUser == null) {
                System.out.println("Khong xac dinh duoc tai khoan dang dang nhap.");
                return;
            }
           // tranh vo hieu  hoa tk dang dn
            if (userId == currentUser.getId()) {
                System.out.println("Khong the vo hieu hoa chinh tai khoan dang dang nhap");
                return;
            }
            System.out.print("Ban co chac chan muon vo hieu hoa tai khoan nay? (Y/N): ");
            String confirm = sc.nextLine().trim().toUpperCase();
            if (!"Y".equals(confirm)) {
                System.out.println("Da huy thao tac.");
                return;
            }
            String result = userManagementService.deactivateUser(userId);

            if ("SUCCESS".equals(result)) {
                System.out.println("Vo hieu hoa tai khoan thanh cong.");
            } else {
                System.out.println(result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Loi nhap user block",e);
        }
    }
}
