package service;

import model.User;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy thông tin người dùng theo ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // Đăng ký tài khoản mới
    public User registerUser(String username, String password, String fullName, String email, String phoneNumber, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Mã hóa mật khẩu giả lập (cần thay thế bằng thuật toán mã hóa thực tế)
        String hashedPassword = hashPassword(password);

        User newUser = new User(username, hashedPassword, fullName, email, phoneNumber, role);
        return userRepository.save(newUser);
    }

    // Xác thực đăng nhập
    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent() && verifyPassword(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    // Cập nhật thông tin người dùng
    public User updateUser(int id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            updatedUser.setId(id); // Giữ nguyên ID cũ
            return userRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    // Xóa người dùng theo ID
    public boolean deleteUser(int id) {
        return userRepository.deleteById(id);
    }

    // Xác thực thông tin người dùng
    public boolean validateUser(String username, String password) {
        return loginUser(username, password).isPresent();
    }

    // Giả lập mã hóa mật khẩu (thay bằng thư viện thực tế như BCrypt, Argon2, v.v.)
    private String hashPassword(String password) {
        return "hashed_" + password;
    }

    // Kiểm tra mật khẩu (thay thế bằng thuật toán thực tế)
    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return hashedPassword.equals(hashPassword(rawPassword));
    }
}
