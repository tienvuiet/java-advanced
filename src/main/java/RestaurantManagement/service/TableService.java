package RestaurantManagement.service;

import RestaurantManagement.dao.DiningTableDAO;
import RestaurantManagement.model.DiningTable;
import RestaurantManagement.utils.ValidationUtil;

import java.util.List;

public class TableService {
    private final DiningTableDAO diningTableDAO = new DiningTableDAO();


    public List<DiningTable> getAllTables() {
        return diningTableDAO.findAll();
    }


    public DiningTable getTableById(int id) {
        return diningTableDAO.findById(id);
    }


    public List<DiningTable> searchTablesByNumber(String keyword) {
        return diningTableDAO.findByTableNumberLike(keyword);
    }


    public String addTable(String tableNumber, int capacity, String status) {

        String validationMessage = ValidationUtil.validateDiningTable(tableNumber, capacity, status);
        if (!"VALID".equals(validationMessage)) {
            return validationMessage;
        }


        DiningTable table = new DiningTable();
        table.setTableNumber(tableNumber.trim());
        table.setCapacity(capacity);
        table.setStatus(status.toUpperCase());
        boolean inserted = diningTableDAO.insert(table);
        return inserted ? "SUCCESS" : "Them ban that bai.";
    }

    /*
     * Cập nhật bàn
     */
    public String updateTable(int id, String tableNumber, int capacity, String status) {
        // Kiểm tra bàn có tồn tại không
        DiningTable existingTable = diningTableDAO.findById(id);
        if (existingTable == null) {
            return "Khong tim thay ban co id = " + id;
        }


        String validationMessage = ValidationUtil.validateDiningTable(tableNumber, capacity, status);
        if (!"VALID".equals(validationMessage)) {
            return validationMessage;
        }

        existingTable.setId(id);
        existingTable.setTableNumber(tableNumber.trim());
        existingTable.setCapacity(capacity);
        existingTable.setStatus(status.toUpperCase());

        boolean updated = diningTableDAO.update(existingTable);
        return updated ? "SUCCESS" : "Cap nhat ban that bai.";
    }

    /*
     * Xóa bàn
     */
    public String deleteTable(int id) {
        // Kiểm tra tồn tại
        DiningTable existingTable = diningTableDAO.findById(id);
        if (existingTable == null) {
            return "Khong tim thay ban co id = " + id;
        }

        boolean deleted = diningTableDAO.delete(id);
        return deleted ? "SUCCESS" : "Xoa ban that bai.";
    }
}
