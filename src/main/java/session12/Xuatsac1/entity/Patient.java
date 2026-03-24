package session12.Xuatsac1.entity;

public class Patient {
    Integer idPatient ;
    String namePatient;
    Integer age;
    String treatmentDepartment;

    public Patient() {
    }

    public Patient(Integer idPatient, String namePatient, Integer age, String treatmentDepartment) {
        this.idPatient = idPatient;
        this.namePatient = namePatient;
        this.age = age;
        this.treatmentDepartment = treatmentDepartment;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(Integer idPatient) {
        this.idPatient = idPatient;
    }

    public String getNamePatient() {
        return namePatient;
    }

    public void setNamePatient(String namePatient) {
        this.namePatient = namePatient;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTreatmentDepartment() {
        return treatmentDepartment;
    }

    public void setTreatmentDepartment(String treatmentDepartment) {
        this.treatmentDepartment = treatmentDepartment;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "idPatient=" + idPatient +
                ", namePatient='" + namePatient + '\'' +
                ", age=" + age +
                ", treatmentDepartment='" + treatmentDepartment + '\'' +
                '}';
    }
}
