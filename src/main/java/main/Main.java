package main;

import dao.UserDAO;
import entity.User;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        User newUser = new User(0, "admin", "admin123", "Quản trị viên", "admin@example.com", "0123456789", "ADMIN", LocalDateTime.now());
        userDAO.addUser(newUser);
    }
}
