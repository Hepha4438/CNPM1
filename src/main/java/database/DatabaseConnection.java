package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://mysql-4438-cnpm-1.g.aivencloud.com:18300/defaultdb?sslmode=require";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    ;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("❌ Kết nối thất bại! Kiểm tra lại thông tin kết nối.");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("✅ Kết nối MySQL Aiven thành công!");
        }
    }
}
