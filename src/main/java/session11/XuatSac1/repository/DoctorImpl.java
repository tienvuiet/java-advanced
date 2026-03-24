package session11.XuatSac1.repository;

import session11.XuatSac1.Db.DBKetNoi;
import session11.XuatSac1.Entity.Doctor;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorImpl implements DoctorReponsitory{
    @Override
    public List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        con = DBKetNoi.getConnectionDoctor();
        try {
            stmt = con.createStatement();
            String sql = "select * from doctor";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                Doctor doctor = new Doctor();
                doctor.setDoctorId(rs.getInt("doctorId"));
                doctor.setDoctorName(rs.getString("doctorName"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return doctors;
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBKetNoi.getConnectionDoctor();
            String sql = "insert into doctor(doctorName, specialty) values (?, ?)";
            ps = con.prepareStatement(sql);

            ps.setString(1, doctor.getDoctorName());
            ps.setString(2,doctor.getSpecialty());

            int rows = ps.executeUpdate();
            if (rows> 0 ){
                result = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;

    }

    @Override
    public Map<String, Integer> countDoctorBySpecialty() {
        Map<String, Integer> map = new HashMap<>();
        try (
            Connection con = DBKetNoi.getConnectionDoctor();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select specialty, count(*) as total from doctor group by specialty"))
        {
                    while (rs.next()){
                        map.put(rs.getString("specialty"), rs.getInt("total"));
                    }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  map;
    }
}
