package ui;

import model.User;
import service.UserService;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginForm extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, backButton;
    private UserService userService;
    private MainFrame mainFrame;

    public LoginForm(MainFrame mainFrame, UserService userService) {
        this.mainFrame = mainFrame;
        this.userService = userService;
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Tên đăng nhập:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Đăng nhập");
        backButton = new JButton("Quay lại");

        add(loginButton);
        add(backButton);

        loginButton.addActionListener(e -> loginUser());
        backButton.addActionListener(e -> mainFrame.showMainMenu());
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        Optional<User> user = userService.loginUser(username, password);
        if (user.isPresent()) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            mainFrame.showHomePage(user.get());
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
