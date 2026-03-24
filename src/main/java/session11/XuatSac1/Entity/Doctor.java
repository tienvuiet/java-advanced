package session11.XuatSac1.Entity;

public class Doctor {
    private  int doctorId;
    private String doctorName;
    private String specialty;

    public Doctor() {
    }

    public Doctor(int doctorId, String doctorName, String specialty) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialty = specialty;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
