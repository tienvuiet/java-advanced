package RestaurantManagement.service;

import RestaurantManagement.dao.OrderItemDAO;
import RestaurantManagement.model.ChefOrderItemView;
import RestaurantManagement.model.OrderItem;

import java.util.List;

public class ChefService {
    private  final OrderItemDAO orderItemDAO = new OrderItemDAO();
    // lay danh sach mon uu tien
    public List<ChefOrderItemView> getPendingItems(){
        return orderItemDAO.findChefItemsByStatus("PENDING");
    }
    // lay tat ca mon trong bep can follow
    public List<ChefOrderItemView> getKitchenActiveItems(){
        return  orderItemDAO.findKitchenActiveItems();
    }

    // cap nhat trang thai theo thu tu PENDING -> COOKING -> READY -> SERVED
    public  String updateOrderItemStatus(int orderItemId){
        OrderItem existingItem = orderItemDAO.findByIdForChef(orderItemId);
        if (existingItem ==  null){
            return "Khong tim thay mon co order_item_id = "+ orderItemId;
        }
        String currentStatus = existingItem.getStatus();
        String nextStatus;
        switch (currentStatus.toUpperCase()) {
            case "PENDING":
                nextStatus = "COOKING";
                break;
            case "COOKING":
                nextStatus = "READY";
                break;
            case "READY":
                nextStatus = "SERVED";
                break;
            case "SERVED":
                return "Mon nay da o trang thai SERVED, khong the cap nhat tiep";
            case "CANCELLED":
                return "Mon nay da bi huy, khong the cap nhat";
            default:
                return "Trang thai hien tai khong hop le: " + currentStatus;
        }
        boolean updated = orderItemDAO.updateStatus(orderItemId, nextStatus);
        return updated ? "SUCCESS:" + nextStatus : "Cap nhat trang thai that bai";
    }

    // ham nay dau bep tu tay cap nhat trang thai thay vi tu dong
    public String updateOrderItemStatusManually(int orderItemId, String newStatus) {
        OrderItem existingItem = orderItemDAO.findByIdForChef(orderItemId);

        if (existingItem == null) {
            return "Khong tim thay mon co order_item_id = " + orderItemId;
        }

        String currentStatus = existingItem.getStatus().toUpperCase();
        newStatus = newStatus.toUpperCase();

        boolean valid = false;

        if ("PENDING".equals(currentStatus) && "COOKING".equals(newStatus)) {
            valid = true;
        } else if ("COOKING".equals(currentStatus) && "READY".equals(newStatus)) {
            valid = true;
        } else if ("READY".equals(currentStatus) && "SERVED".equals(newStatus)) {
            valid = true;
        }

        if (!valid) {
            return "Khong duoc cap nhat tu " + currentStatus + " sang " + newStatus;
        }

        boolean updated = orderItemDAO.updateStatus(orderItemId, newStatus);
        return updated ? "SUCCESS" : "Cap nhat trang thai that bai";
    }
}
