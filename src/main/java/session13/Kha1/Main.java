package session13.Kha1;

import session12.Xuatsac1.common.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Main {
//    Trong JDBC, mặc định auto-commit = true, nghĩa là mỗi câu executeUpdate() là một transaction độc lập và được commit ngay lập tức xuống database — không chờ các lệnh tiếp theo.
public void capPhatThuoc(int medicineId, int patientId) {
    Connection conn = null;
    try {
        conn = DBUtility.getConnectionPatient();

        conn.setAutoCommit(false);
        String sqlUpdateInventory = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
        PreparedStatement ps1 = conn.prepareStatement(sqlUpdateInventory);
        ps1.setInt(1, medicineId);
        ps1.executeUpdate();
        String sqlInsertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, GETDATE())";
        PreparedStatement ps2 = conn.prepareStatement(sqlInsertHistory);
        ps2.setInt(1, patientId);
        ps2.setInt(2, medicineId);
        ps2.executeUpdate();
        conn.commit();
        System.out.println("Cấp phát thuốc thành công!");

    } catch (Exception e) {

        if (conn != null) {
            try { conn.rollback(); } catch (Exception re) { re.printStackTrace(); }
        }
        System.out.println("Có lỗi xảy ra, đã rollback: " + e.getMessage());

    } finally {
        if (conn != null) {
            try { conn.setAutoCommit(true); conn.close(); } catch (Exception ce) {}
        }
    }
}
}
