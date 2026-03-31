package RestaurantManagement.utils;

import java.io.IOException;

public class InputUtil {
    public static String readPasswordWithAsterisk() {
        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                int ch = System.in.read();
                // Một số môi trường console sẽ gửi '\r' trước '\n'
                if (ch == '\r') {
                    continue;
                }
                // Nhấn Enter thì kết thúc nhập mật khẩu
                if (ch == '\n') {
                    break;
                }
                // Xử lý phím backspace
                if (ch == 8 || ch == 127) {
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b");
                    }
                } else {
                    // Lưu ký tự thật vào password
                    password.append((char) ch);

                    // In ra dấu * thay vì ký tự thật
                    System.out.print("*");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Loi khi nhap mat khau", e);
        }
        System.out.println();
        return password.toString();
    }
}
