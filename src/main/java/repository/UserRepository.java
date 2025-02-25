package repository;

import database.DatabaseConnection;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    // Lấy tất cả người dùng
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Lấy user theo ID
    public Optional<User> findById(int id) {
        return findUserByQuery("SELECT * FROM users WHERE id = ?", id);
    }

    // Lấy user theo username
    public Optional<User> findByUsername(String username) {
        return findUserByQuery("SELECT * FROM users WHERE username = ?", username);
    }

    // Lấy user theo email
    public Optional<User> findByEmail(String email) {
        return findUserByQuery("SELECT * FROM users WHERE email = ?", email);
    }

    // Lưu hoặc cập nhật user
    public User save(User user) {
        String query;
        boolean isUpdate = user.getId() > 0;

        if (isUpdate) {
            // Nếu ID tồn tại, cập nhật
            query = "UPDATE users SET username = ?, password = ?, email = ?, full_name = ?, phone_number = ?, role = ? WHERE id = ?";
        } else {
            // Nếu chưa có ID, thêm mới
            query = "INSERT INTO users (username, password, email, full_name, phone_number, role) VALUES (?, ?, ?, ?, ?, ?)";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getPhoneNumber());
            stmt.setString(6, user.getRole());

            if (isUpdate) {
                stmt.setInt(7, user.getId());
            }

            int affectedRows = stmt.executeUpdate();

            if (!isUpdate && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Xóa user theo ID
    public boolean deleteById(int id) {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hàm hỗ trợ tìm user theo query
    private Optional<User> findUserByQuery(String query, Object param) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (param instanceof Integer) {
                stmt.setInt(1, (Integer) param);
            } else {
                stmt.setString(1, (String) param);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Ánh xạ ResultSet sang User
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("role"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
