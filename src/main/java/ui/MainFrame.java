package ui;

import model.User;
import service.UserService;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserService userService;

    public MainFrame(UserService userService) {
        this.userService = userService;
        setTitle("Ứng dụng Quản lý Người dùng");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new MainMenu(this), "MainMenu");
        mainPanel.add(new LoginForm(this, userService), "Login");
        mainPanel.add(new RegisterForm(this, userService), "Register");

        add(mainPanel);
        showMainMenu();
    }

    public void showMainMenu() {
        cardLayout.show(mainPanel, "MainMenu");
    }

    public void showLoginForm() {
        cardLayout.show(mainPanel, "Login");
    }

    public void showRegisterForm() {
        cardLayout.show(mainPanel, "Register");
    }

    public void showHomePage(User user) {
        mainPanel.add(new HomePage(this, user), "Home");
        cardLayout.show(mainPanel, "Home");
    }

    public static void main(String[] args) {
        UserService userService = new UserService(new repository.UserRepository());
        SwingUtilities.invokeLater(() -> new MainFrame(userService).setVisible(true));
    }
}
