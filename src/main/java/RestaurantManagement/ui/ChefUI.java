package RestaurantManagement.ui;

import RestaurantManagement.model.ChefOrderItemView;
import RestaurantManagement.model.User;
import RestaurantManagement.service.ChefService;

import java.util.List;
import java.util.Scanner;

public class ChefUI {
    private final Scanner sc = new Scanner(System.in);
    private final ChefService chefService = new ChefService();

    public void showChefMenu(User currentUser) {
        while (true) {
            System.out.println("\n=================================================");
            System.out.println("           HE THONG QUAN LY NHA HANG - CHEF");
            System.out.println("=================================================");
            System.out.println("1. Xem danh sach mon dang cho xu ly (PENDING)");
            System.out.println("2. Xem tat ca mon trong bep");
            System.out.println("3. Cap nhat trang thai mon");
            System.out.println("0. Dang xuat");
            System.out.println("=================================================");
            System.out.print("Chon chuc nang: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    showPendingItems();
                    break;
                case "2":
                    showKitchenActiveItems();
                    break;
                case "3":
                    updateItemStatus();
                    break;
                case "0":
                    System.out.println("Dang xuat khoi menu Chef...");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    // in du lieu cho dau bep
    private void printChefItemTable(List<ChefOrderItemView> items) {
        System.out.println("\n================================================ DANH SACH MON TRONG BEP ===============================================");
        System.out.printf("%-12s %-10s %-12s %-18s %-20s %-10s %-12s %-20s%n",
                "OrderItemID", "OrderID", "So ban", "Khach hang", "Ten mon", "So luong", "Trang thai", "Thoi gian goi");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

        for (ChefOrderItemView item : items) {
            System.out.printf("%-12d %-10d %-12s %-18s %-20s %-10d %-12s %-20s%n",
                    item.getOrderItemId(),
                    item.getOrderId(),
                    item.getTableNumber(),
                    item.getCustomerUsername(),
                    item.getMenuItemName(),
                    item.getQuantity(),
                    item.getStatus(),
                    item.getOrderedAt());
        }
    }

    // hien thi cac mon dang pending
    private  void showPendingItems(){
        List<ChefOrderItemView> items = chefService.getPendingItems();
        if (items.isEmpty()){
            System.out.println("Khong co mon nao o trang thai pending");
            return;
        }
        printChefItemTable(items);
    }

    //hien thi tat ca cac mon trong bep
    // pending cooking ready
    private  void showKitchenActiveItems(){
        List<ChefOrderItemView> items = chefService.getKitchenActiveItems();
        if (items.isEmpty()){
            System.out.println("Khong co mon nao trong bep");
            return ;
        }
        printChefItemTable(items);
    }
    private  void  updateItemStatus(){
        // hien thi danh sach cho chef chon
        showKitchenActiveItems();
        while (true){
            try {
                System.out.println("Nhap order_item_id can cap nhat (0 de quay lai): ");
                int orderItemId = Integer.parseInt(sc.nextLine().trim());
                if (orderItemId == 0){
                    return;
                }
                String result = chefService.updateOrderItemStatus(orderItemId);
                if (result.startsWith("SUCCESS:")){
                    String nextStatus = result.split(":")[1];
                    System.out.println("Cap nhat trang thai thanh cong -> "+ nextStatus);
                }else {
                    System.out.println(result);
                }
            } catch (Exception e) {
                throw new RuntimeException("Order item id khong hop le.",e);
            }
        }
    }
}
