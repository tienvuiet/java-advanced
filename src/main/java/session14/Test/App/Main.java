package session14.Test.App;

import session14.Test.Dao.Transfer;

import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap id tai khoan gui: ");
        String fromId = sc.nextLine();
        System.out.print("Nhap id tai khoan nhan: ");
        String toId = sc.nextLine();
        System.out.print("Nhap so tien: ");
        double amount = sc.nextDouble();
        Transfer transfer = new Transfer();
        transfer.TransferMoney(fromId, toId, amount);
    }
}
