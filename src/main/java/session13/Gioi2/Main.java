//package session13.Gioi2;
//
//import session12.Xuatsac1.common.DBUtility;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//public class Main {
//    public List<BenhNhanDTO> layDanhSachBenhNhanHienTai() {
//
//        List<BenhNhanDTO> result = new ArrayList<>();
//        Connection conn = null;
//
//        try {
//            conn = DBUtility.getConnectionPatient();
//
//            String sqlBenhNhan =
//                    "SELECT ma_benh_nhan, ho_ten, tuoi, gioi_tinh, chan_doan " +
//                            "FROM BenhNhan " +
//                            "WHERE ngay_nhap_vien = CAST(GETDATE() AS DATE) " +
//                            "  AND trang_thai = N'Đang điều trị'";
//
//            PreparedStatement ps1 = conn.prepareStatement(sqlBenhNhan);
//            ResultSet rs1 = ps1.executeQuery();
//
//            LinkedHashMap<Integer, BenhNhanDTO> mapBenhNhan = new LinkedHashMap<>();
//
//            while (rs1.next()) {
//                BenhNhanDTO dto = new BenhNhanDTO();
//                dto.setMaBenhNhan(rs1.getInt("ma_benh_nhan"));
//                dto.setHoTen(rs1.getString("ho_ten"));
//                dto.setTuoi(rs1.getInt("tuoi"));
//                dto.setGioiTinh(rs1.getString("gioi_tinh"));
//                dto.setChanDoan(rs1.getString("chan_doan"));
//                dto.setDsDichVu(new ArrayList<>());
//                mapBenhNhan.put(dto.getMaBenhNhan(), dto);
//            }
//
//            if (mapBenhNhan.isEmpty()) {
//                return result;
//            }
//
//            List<Integer> dsMaBenhNhan = new ArrayList<>(mapBenhNhan.keySet());
//
//            StringBuilder placeholders = new StringBuilder();
//            for (int i = 0; i < dsMaBenhNhan.size(); i++) {
//                placeholders.append(i == 0 ? "?" : ",?");
//            }
//
//            String sqlDichVu =
//                    "SELECT ma_benh_nhan, ten_dich_vu, loai, gio_su_dung, lieu_luong " +
//                            "FROM DichVuSuDung " +
//                            "WHERE ma_benh_nhan IN (" + placeholders + ") " +
//                            "ORDER BY ma_benh_nhan, gio_su_dung";
//
//            PreparedStatement ps2 = conn.prepareStatement(sqlDichVu);
//            for (int i = 0; i < dsMaBenhNhan.size(); i++) {
//                ps2.setInt(i + 1, dsMaBenhNhan.get(i));
//            }
//
//            ResultSet rs2 = ps2.executeQuery();
//
//            while (rs2.next()) {
//                int maBenhNhan = rs2.getInt("ma_benh_nhan");
//
//                DichVu dichVu = new DichVu();
//                dichVu.setTenDichVu(rs2.getString("ten_dich_vu"));
//                dichVu.setLoai(rs2.getString("loai"));
//                dichVu.setGioSuDung(rs2.getString("gio_su_dung"));
//                dichVu.setLieuLuong(rs2.getString("lieu_luong"));
//
//                mapBenhNhan.get(maBenhNhan).getDsDichVu().add(dichVu);
//            }
//
//            result = new ArrayList<>(mapBenhNhan.values());
//
//        } catch (SQLException e) {
//            System.out.println("Lỗi truy vấn Dashboard: " + e.getMessage());
//
//        } finally {
//            if (conn != null) {
//                try { conn.close(); } catch (SQLException ce) {}
//            }
//        }
//
//        return result;
//    }
//}
//public class BenhNhanDTO {
//    private int maBenhNhan;
//    private String hoTen;
//    private int tuoi;
//    private String gioiTinh;
//    private String chanDoan;
//    private List<DichVu> dsDichVu;
//
//    public int getMaBenhNhan()               { return maBenhNhan; }
//    public void setMaBenhNhan(int v)         { this.maBenhNhan = v; }
//    public String getHoTen()                 { return hoTen; }
//    public void setHoTen(String v)           { this.hoTen = v; }
//    public int getTuoi()                     { return tuoi; }
//    public void setTuoi(int v)               { this.tuoi = v; }
//    public String getGioiTinh()              { return gioiTinh; }
//    public void setGioiTinh(String v)        { this.gioiTinh = v; }
//    public String getChanDoan()              { return chanDoan; }
//    public void setChanDoan(String v)        { this.chanDoan = v; }
//    public List<DichVu> getDsDichVu()        { return dsDichVu; }
//    public void setDsDichVu(List<DichVu> v)  { this.dsDichVu = v; }
//}
//public class DichVu {
//    private String tenDichVu;
//    private String loai;
//    private String gioSuDung;
//    private String lieuLuong;
//
//    public String getTenDichVu()          { return tenDichVu; }
//    public void setTenDichVu(String v)    { this.tenDichVu = v; }
//    public String getLoai()               { return loai; }
//    public void setLoai(String v)         { this.loai = v; }
//    public String getGioSuDung()          { return gioSuDung; }
//    public void setGioSuDung(String v)    { this.gioSuDung = v; }
//    public String getLieuLuong()          { return lieuLuong; }
//    public void setLieuLuong(String v)    { this.lieuLuong = v; }
//}