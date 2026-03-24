package session12.Xuatsac1.dao;

import session12.Xuatsac1.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public interface PatientDao {
    boolean insertPatient(Patient patient);
    boolean updatePatient(Integer id, String partment);
    List<Patient> getAllPatient();
    int countPatienAge20();
}
