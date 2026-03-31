package RestaurantManagement.model;

public class DiningTable {
    private int id;
    private String tableNumber;
    private int capacity;
    private String status;

    public DiningTable() {}

    public DiningTable(int id, String tableNumber, int capacity, String status) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Table{id=" + id + ", number='" + tableNumber + "', capacity=" + capacity + ", status='" + status + "'}";
    }
}
