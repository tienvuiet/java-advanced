package session13.Gioi1;

import session12.Xuatsac1.common.DBUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {

        Connection conn = null;

        try {
            conn = DBUtility.getConnectionPatient();
            conn.setAutoCommit(false);

            String sqlCheckBalance =
                    "SELECT so_du_tam_ung FROM Patient_Wallet WHERE patient_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheckBalance);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                throw new Exception("Không tìm thấy ví tạm ứng của bệnh nhân #" + maBenhNhan);
            }

            double soDuHienTai = rs.getDouble("so_du_tam_ung");

            if (soDuHienTai < tienVienPhi) {
                throw new Exception(
                        "Số dư không đủ! Hiện có: " + soDuHienTai
                                + " VNĐ | Cần thanh toán: " + tienVienPhi + " VNĐ"
                );
            }

            String sqlTruTien =
                    "UPDATE Patient_Wallet SET so_du_tam_ung = so_du_tam_ung - ? " +
                            "WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sqlTruTien);
            ps1.setDouble(1, tienVienPhi);
            ps1.setInt(2, maBenhNhan);

            int row1 = ps1.executeUpdate();
            if (row1 == 0) {
                throw new Exception("Không tìm thấy bệnh nhân #" + maBenhNhan + " khi trừ tiền.");
            }

            String sqlGiaiPhongGiuong =
                    "UPDATE BedStatus SET trang_thai = N'Trống' " +
                            "WHERE patient_id = ? AND trang_thai = N'Đang sử dụng'";
            PreparedStatement ps2 = conn.prepareStatement(sqlGiaiPhongGiuong);
            ps2.setInt(1, maBenhNhan);

            int row2 = ps2.executeUpdate();
            if (row2 == 0) {
                throw new Exception("Không tìm thấy giường đang sử dụng của bệnh nhân #" + maBenhNhan);
            }

            String sqlCapNhatBenhNhan =
                    "UPDATE Patient SET trang_thai = N'Đã xuất viện' " +
                            "WHERE patient_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(sqlCapNhatBenhNhan);
            ps3.setInt(1, maBenhNhan);

            int row3 = ps3.executeUpdate();
            if (row3 == 0) {
                throw new Exception("Không tìm thấy bệnh nhân #" + maBenhNhan + " khi cập nhật trạng thái.");
            }

            conn.commit();
            System.out.println("Xuất viện thành công cho bệnh nhân #" + maBenhNhan
                    + " | Đã thanh toán: " + tienVienPhi + " VNĐ");

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException re) {
                    System.out.println("Rollback thất bại: " + re.getMessage());
                }
            }

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ce) {
                    System.out.println("Lỗi đóng connection: " + ce.getMessage());
                }
            }
        }
    }
}
