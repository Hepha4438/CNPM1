package main;

import dao.UserDAO;
import model.User;
import repository.UserRepository;
import service.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        User newUser = new User("admin2", "admin123", "Quản trị viên", "admin2@example.com", "0223456789", "ADMIN");
//        userDAO.addUser(newUser);
        UserRepository repo=new UserRepository();
        Optional<User> user = repo.findByUsername("Hepha2");
        System.out.println(user.get().getPassword());
        System.out.println(user.isPresent() && new UserService(repo).verifyPassword("1234", user.get().getPassword()));
    }
}
