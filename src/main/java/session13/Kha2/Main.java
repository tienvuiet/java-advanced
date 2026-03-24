package session13.Kha2;

import session12.Xuatsac1.common.DBUtility;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {


        public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
            Connection conn = null;
            try {
                conn = DBUtility.getConnectionPatient();
                conn.setAutoCommit(false);
                String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(sqlDeductWallet);
                ps1.setDouble(1, amount);
                ps1.setInt(2, patientId);
                ps1.executeUpdate();
                String sqlUpdateInvoice = "UPDATE Invoices SET status = 'PAID' WHERE invoice_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sqlUpdateInvoice);
                ps2.setInt(1, invoiceId);
                ps2.executeUpdate();
                conn.commit();
                System.out.println("Thanh toán hoàn tất!");

            } catch (SQLException e) {
                if (conn != null) {
                    try { conn.rollback(); } catch (Exception re) { re.printStackTrace(); }
                }
                System.out.println("Lỗi hệ thống: Không thể hoàn tất thanh toán. Chi tiết: " + e.getMessage());

            } finally {
                if (conn != null) {
                    try { conn.setAutoCommit(true); conn.close(); } catch (Exception ce) {}
                }
            }
        }

}
