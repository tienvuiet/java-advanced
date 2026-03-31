package RestaurantManagement.service;

import RestaurantManagement.dao.MenuItemDAO;
import RestaurantManagement.model.MenuItem;
import RestaurantManagement.utils.ValidationUtil;

import java.math.BigDecimal;
import java.util.List;

public class MenuService {
    private  final MenuItemDAO menuItemDAO = new MenuItemDAO();
    // lay danh sach mon
    public List<MenuItem> getAllMenuItems(){
        return menuItemDAO.findAll();
    }
    // lay mon theo id
    public MenuItem getMenuItemById(int id){
        return menuItemDAO.findById(id);
    }
    // Tim kiem theo ten gan dung
    public List<MenuItem> searchMenuItemByName(String key){
        return menuItemDAO.findByNameLike(key);
    }

    // them moi
    public String addMenuItem(String name, String category, BigDecimal price,int stockQuantity, boolean isAvaible ){
        String validationMessage = ValidationUtil.validateMenuItem(name, category, price,stockQuantity);
        if(!"VALID".equalsIgnoreCase(validationMessage)){
            return validationMessage;
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setCategory(category.toUpperCase());
        menuItem.setPrice(price);
        menuItem.setStockQuantity(stockQuantity);
        menuItem.setAvailable(isAvaible);

        boolean inserted = menuItemDAO.insert(menuItem);
        return inserted ? "SUCCESS": "Them mon that bai";
    }
    // update
    public String updateMenuItem(int id,String name, String category, BigDecimal price, int stockQuantity, boolean isAvailable ){
        MenuItem existingItem = menuItemDAO.findById(id);
        if (existingItem == null){
            return "Khong tim thay mon co id: "+ id;
        }
        String validationMessage = ValidationUtil.validateMenuItem(name, category, price, stockQuantity);
        if (!"VALID".equalsIgnoreCase(validationMessage)){
            return validationMessage;
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setId(id);
        menuItem.setName(name);
        menuItem.setCategory(category.toUpperCase());
        menuItem.setPrice(price);
        menuItem.setStockQuantity(stockQuantity);
        menuItem.setAvailable(isAvailable);

        boolean updated = menuItemDAO.update(menuItem);
        return updated ? "SUCCESS":"Cap nhat mon that bai";
    }
    // xoa mon
    public  String deleteMenuItem (int id){
        MenuItem existingMenuItems = menuItemDAO.findById(id);
        if (existingMenuItems == null){
            return "Khong tim thay mon an co id: "+ id;
        }
        boolean deleted = menuItemDAO.detele(id);
        return deleted ? "SUCCESS": "Xoa mon an that bai";
    }

}
