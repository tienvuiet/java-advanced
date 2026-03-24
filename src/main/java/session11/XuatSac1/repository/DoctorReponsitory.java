package session11.XuatSac1.repository;

import session11.XuatSac1.Entity.Doctor;

import java.util.List;
import java.util.Map;

public interface DoctorReponsitory {
    public List<Doctor> getDoctors();
    boolean addDoctor(Doctor doctor);
    public Map<String, Integer> countDoctorBySpecialty();

}
