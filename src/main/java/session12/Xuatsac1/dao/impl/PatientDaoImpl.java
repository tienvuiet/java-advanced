package session12.Xuatsac1.dao.impl;

import session12.Xuatsac1.common.DBUtility;
import session12.Xuatsac1.dao.PatientDao;
import session12.Xuatsac1.entity.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements PatientDao {
    @Override
    public List<Patient> getAllPatient() {
        List<Patient> list = new ArrayList<>();
        String sql = "select * from patiens";
        try(
                Connection con = DBUtility.getConnectionPatient();
                PreparedStatement pr = con.prepareStatement(sql);
                ResultSet rs = pr.executeQuery();
                ){
            while (rs.next()){
                Patient p = new Patient();
                p.setIdPatient(rs.getInt("idPatient"));
                p.setNamePatient(rs.getString("namePatient"));
                p.setAge(rs.getInt("age"));
                p.setTreatmentDepartment(rs.getString("treatmentDepartment"));
                list.add(p);
            }

        }catch (Exception e){
            System.out.println("Loi: "+ e.getMessage());
        }
        return list;
    }

    @Override
    public boolean insertPatient(Patient patient) {
        boolean result = false ;
        String sql = "insert into patiens(namePatient,age,treatmentDepartment) values (?, ?, ?)";
        try(Connection con = DBUtility.getConnectionPatient();
           PreparedStatement pr = con.prepareStatement(sql);
        ) {
            pr.setString(1, patient.getNamePatient());
            pr.setInt(2, patient.getAge());
            pr.setString(3, patient.getTreatmentDepartment());
            int i = pr.executeUpdate();
            if(i> 0 ) result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    @Override
    public boolean updatePatient(Integer id, String partment) {
        boolean result = false;
        String sql = "Update patiens set treatmentDepartment = ? where idPatient = ?";
        try(Connection con = DBUtility.getConnectionPatient();
            PreparedStatement pr = con.prepareStatement(sql);
        ){
            pr.setInt(1, id);
            pr.setString(2, partment);
            int i = pr.executeUpdate();
            if(i>0) result = true;
        } catch (SQLException e) {
            System.out.println("Loi: "+ e.getMessage());
        }
        return  result;
    }

    @Override
    public int countPatienAge20() {
        int count  = 0;
        String sql = "{call countPatientAge20(?)}";
        try(Connection con = DBUtility.getConnectionPatient();
            CallableStatement pr = con.prepareCall(sql);
        ){
           pr.registerOutParameter(1, Types.INTEGER);
           pr.execute();
           count = pr.getInt(1);
        }catch (SQLException e){
            System.out.println("Loi: " + e.getMessage());
        }
        return count;
    }

}
