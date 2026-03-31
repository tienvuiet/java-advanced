package RestaurantManagement.ui;

import RestaurantManagement.model.*;
import RestaurantManagement.service.MenuService;
import RestaurantManagement.service.OrderService;
import RestaurantManagement.service.ReviewService;
import RestaurantManagement.utils.TablePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerUI {
    private final Scanner sc = new Scanner(System.in);
    private final MenuService menuService = new MenuService();
    private final OrderService orderService = new OrderService();
    private  final ReviewService reviewService = new ReviewService();
    public void showCustomerMenu(User currentUser) {
        while (true) {
            System.out.println("\n=================================================");
            System.out.println("        HE THONG QUAN LY NHA HANG - CUSTOMER");
            System.out.println("=================================================");
            System.out.println("1. Xem menu");
            System.out.println("2. Chon ban va goi mon");
            System.out.println("3. Theo doi mon da goi");
            System.out.println("4. Huy mon");
            System.out.println("5. Danh gia mon an");
            System.out.println("6. Xem lich su review");
            System.out.println("0. Dang xuat");
            System.out.println("=================================================");
            System.out.print("Chon chuc nang: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    viewMenu();
                    break;
                case "2":
                    placeOrder(currentUser);
                    break;
                case "3":
                    viewMyOrderedItems(currentUser);
                    break;
                case "4":
                    cancelOrderItem(currentUser);
                    break;
                case "5":
                    addMenuItemReview(currentUser);
                    break;
                case "6":
                    viewMyReviews(currentUser);
                    break;
                case "0":
                    System.out.println("Dang xuat khoi menu Customer...");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    // xem menu
    private  void  viewMenu(){
        List<MenuItem> items = menuService.getAllMenuItems();
        if (items.isEmpty()){
            System.out.println("Chua co mon nao trong thuc don");
            return;
        }
        TablePrinter.printMenuItemTable(items);
    }
    // chon ban va goi mon
    // hien thi ban trong
    // khach hang chon 1 ban
    // hien thi menu
    // cho phep nhap nhieu mon
    // gui xu ly service
    private void placeOrder(User currentUser) {
        List<DiningTable> availableTables = orderService.getAvailableTables();

        if (availableTables.isEmpty()) {
            System.out.println("Khong co ban trong.");
            return;
        }

        System.out.println("=============== DANH SACH BAN TRONG ===============");
        TablePrinter.printDiningTableTable(availableTables);

        int tableId;
        try {
            System.out.print("Nhap id ban muon chon: ");
            tableId = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("ID ban khong hop le.");
            return;
        }

        // Kiem tra tableId co nam trong danh sach ban trong khong
        boolean tableExistsInAvailableList = false;
        for (DiningTable table : availableTables) {
            if (table.getId() == tableId) {
                tableExistsInAvailableList = true;
                break;
            }
        }

        if (!tableExistsInAvailableList) {
            System.out.println("Ban ban chon khong ton tai hoac khong con trong.");
            return;
        }

        // hien thi menu truoc khi goi mon
        List<MenuItem> menuItems = menuService.getAllMenuItems();
        if (menuItems.isEmpty()) {
            System.out.println("Khong co mon nao de goi.");
            return;
        }

        System.out.println("================ THUC DON HIEN CO ================");
        TablePrinter.printMenuItemTable(menuItems);

        // Danh sach mon se duoc gom lai o day
        List<OrderItem> orderItems = new ArrayList<>();

        while (true) {
            try {
                System.out.print("Nhap id mon muon goi: ");
                int menuItemId = Integer.parseInt(sc.nextLine().trim());

                MenuItem menuItem = menuService.getMenuItemById(menuItemId);
                if (menuItem == null) {
                    System.out.println("Khong tim thay mon co id: " + menuItemId);
                    continue;
                }

                // Neu mon dang ngung ban thi khong cho goi
                if (!menuItem.isAvailable()) {
                    System.out.println("Mon nay hien dang ngung ban.");
                    continue;
                }

                int quantity;
                while (true) {
                    try {
                        System.out.print("Nhap so luong: ");
                        quantity = Integer.parseInt(sc.nextLine().trim());

                        if (quantity <= 0) {
                            System.out.println("So luong phai > 0");
                            continue;
                        }

                        // Neu la DRINK thi phai kiem tra ton kho
                        if ("DRINK".equalsIgnoreCase(menuItem.getCategory())) {
                            if (quantity > menuItem.getStockQuantity()) {
                                System.out.println("Khong du ton kho! Ton hien tai: " + menuItem.getStockQuantity());
                                continue;
                            }
                        }

                        break;
                    } catch (Exception e) {
                        System.out.println("Vui long nhap so hop le.");
                    }
                }

                // Tao 1 dong mon trong orderItems
                OrderItem orderItem = new OrderItem();
                orderItem.setMenuItemId(menuItemId);
                orderItem.setQuantity(quantity);

                // CHI THEM VAO LIST, CHUA LUU DB
                orderItems.add(orderItem);

                System.out.print("Ban co muon goi them mon khong (Y/N): ");
                String choice = sc.nextLine().trim().toUpperCase();

                if (!"Y".equals(choice)) {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Du lieu nhap vao khong hop le.");
            }
        }

        // Neu khach chua chon mon nao thi khong goi service
        if (orderItems.isEmpty()) {
            System.out.println("Ban chua chon mon nao.");
            return;
        }

        // CHI GOI 1 LAN DUY NHAT O DAY
        String result = orderService.placeOrder(currentUser.getId(), tableId, orderItems);

        if ("SUCCESS".equals(result)) {
            System.out.println("Goi mon thanh cong! Ban da duoc dat.");
        } else {
            System.out.println(result);
        }
    }
    // theo doi mon da goi
    private void viewMyOrderedItems(User currentUser){
        List<OrderItemView> items = orderService.getMyOrderedItems(currentUser.getId());
        if (items.isEmpty()){
            System.out.println("Ban chua goi mon nao");
            return;
        }
        System.out.println("\n============================== DANH SACH MON DA GOI ==============================");
        System.out.printf("%-12s %-10s %-25s %-10s %-12s %-12s%n",
                "OrderItemID", "OrderID", "Ten mon", "So luong", "Don gia", "Trang thai");
        System.out.println("------------------------------------------------------------------------------------");

        for (OrderItemView item : items) {
            System.out.printf("%-12d %-10d %-25s %-10d %-12s %-12s%n",
                    item.getOrderItemId(),
                    item.getOrderId(),
                    item.getMenuItemName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getStatus());
        }
    }
    // huy mon
    private  void cancelOrderItem(User currentUser){
        viewMyOrderedItems(currentUser);
        try{
            System.out.println("Nhap order_item_id mon muon huy: ");
            int orderItemId = Integer.parseInt(sc.nextLine().trim());
            String result = orderService.cancelOrderItem(currentUser.getId(), orderItemId);
            if ("SUCCESS".equals(result)){
                System.out.println("Huy mon thanh cong");
            }else {
                System.out.println(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    // danh gia mon cu the
    /*
     * Customer danh gia 1 mon cu the trong order
     *
     * Rule:
     * - order phai la cua customer
     * - order phai COMPLETED
     * - mon phai thuoc order
     * - mon phai SERVED
     */
    private void addMenuItemReview(User currentUser) {
        try {
            System.out.println("\n===== DANH GIA MON AN =====");
            if (!displayCompletedOrdersForReview(currentUser)){
                System.out.println("không có order COMPLETED");
                return;
            }
            System.out.print("Nhap order_id chua mon muon danh gia: ");
            int orderId = Integer.parseInt(sc.nextLine().trim());
            displayServedItemsForReview(orderId);
            System.out.print("Nhap menu_item_id muon danh gia: ");
            int menuItemId = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Nhap so sao (1-5): ");
            int rating = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Nhap binh luan: ");
            String comment = sc.nextLine();

            String result = reviewService.addMenuItemReview(
                    currentUser.getId(),
                    orderId,
                    menuItemId,
                    rating,
                    comment
            );

            if ("SUCCESS".equals(result)) {
                System.out.println("Danh gia mon thanh cong.");
            } else {
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println("Du lieu nhap vao khong hop le.");
        }
    }


    // xem lich su review
    private void viewMyReviews(User currentUser) {
        List<Review> reviews = reviewService.getMyReviews(currentUser.getId());
        if (reviews.isEmpty()) {
            System.out.println("Ban chua co review nao.");
            return;
        }
        System.out.println("\n===================== LICH SU REVIEW =====================");
        System.out.printf("%-5s %-10s %-12s %-8s %-30s %-20s%n",
                "ID", "OrderID", "MenuItemID", "Rating", "Comment", "CreatedAt");
        System.out.println("------------------------------------------------------------------------------------------");
        for (Review review : reviews) {
            String menuItemText = review.getMenuItemId() == null ? "NULL" : String.valueOf(review.getMenuItemId());

            System.out.printf("%-5d %-10d %-12s %-8d %-30s %-20s%n",
                    review.getId(),
                    review.getOrderId(),
                    menuItemText,
                    review.getRating(),
                    review.getComment(),
                    review.getCreatedAt());
        }
    }



    /*
     * Hien thi danh sach order da COMPLETED cua customer
     *
     * Muc dich:
     * - Giup customer nhin thay order_id hop le
     * - Tranh nhap sai order_id
     */
    private boolean displayCompletedOrdersForReview(User currentUser) {
        List<Order> orders = reviewService.getCompletedOrdersOfCustomer(currentUser.getId());

        if (orders.isEmpty()) {
            System.out.println("Ban chua co order nao da COMPLETED de danh gia.");
            return false;
        }

        System.out.println("\n===================== CAC ORDER DA HOAN TAT =====================");
        System.out.printf("%-10s %-10s %-15s %-20s%n",
                "OrderID", "TableID", "Status", "CreatedAt");
        System.out.println("---------------------------------------------------------------");

        for (Order order : orders) {
            System.out.printf("%-10d %-10d %-15s %-20s%n",
                    order.getId(),
                    order.getTableId(),
                    order.getStatus(),
                    order.getCreatedAt());
        }

        return true;
    }


    /*
     * Hien thi danh sach mon da SERVED cua order
     *
     * Muc dich:
     * - Giup customer nhin thay menu_item_id hop le
     * - Chi cho review mon da phuc vu
     */
    private void displayServedItemsForReview(int orderId) {
        List<OrderItemView> items = reviewService.getServedItemsOfOrder(orderId);

        if (items.isEmpty()) {
            System.out.println("Order nay khong co mon nao da SERVED de danh gia.");
            return;
        }

        System.out.println("\n===================== CAC MON DA SERVED =====================");
        System.out.printf("%-12s %-12s %-25s %-10s %-12s %-12s%n",
                "OrderItemID", "MenuItemID", "Ten mon", "So luong", "Don gia", "Trang thai");
        System.out.println("----------------------------------------------------------------------------");

        for (OrderItemView item : items) {
            System.out.printf("%-12d %-12d %-25s %-10d %-12s %-12s%n",
                    item.getOrderItemId(),
                    item.getMenuItemId(),
                    item.getMenuItemName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getStatus());
        }
    }
}
