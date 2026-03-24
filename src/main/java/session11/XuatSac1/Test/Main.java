package session11.XuatSac1.Test;

import session11.XuatSac1.Entity.Doctor;
import session11.XuatSac1.repository.DoctorImpl;

import javax.print.Doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        DoctorImpl repo = new DoctorImpl();
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("--------------------");
            System.out.println("1. Xem danh sach bac si");
            System.out.println("2. Them bac si");
            System.out.println("3. Thong ke bac si");
            System.out.println("4. Thoat chuong trinh");
            int choice = Integer.parseInt(sc.nextLine());
            switch (choice){
                case 1:
                    List<Doctor> doctors = repo.getDoctors();
                    System.out.println("Danh sach bac si");
                    for (Doctor dc : doctors){
                        System.out.println(dc);
                    }
                    break;
                case 2:
                    Doctor newDoctor = new Doctor();
                    System.out.println("Ten bac si: ");
                    newDoctor.setDoctorName(sc.nextLine());
                    System.out.println("Chuyen khoa: ");
                    newDoctor.setSpecialty(sc.nextLine());
                    boolean add = repo.addDoctor(newDoctor);
                    if(add){
                        System.out.println("them thanh cong");
                    }else{
                        System.out.println("Them that bai");
                }
                    break;
                case 3:
                    Map<String, Integer> doctorSpecialty = repo.countDoctorBySpecialty();
                    System.out.println("Thong ke chuyen khoa: ");
                    for (Map.Entry<String, Integer> entry: doctorSpecialty.entrySet()){
                        System.out.println(entry.getKey() + " : "+entry.getValue());
                    }
                    break;
                case 4:
                    System.out.println("ban da thoat chuong trinh");
                    break;
                default:
                    System.out.println("Lua chon khong hop le");
            }
        }
    }
}
