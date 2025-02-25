package ui;

import service.UserService;
import model.User;
import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JPanel {
    private JTextField usernameField, fullNameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private UserService userService;
    private MainFrame mainFrame;

    public RegisterForm(MainFrame mainFrame, UserService userService) {
        this.mainFrame = mainFrame;
        this.userService = userService;
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Tên đăng nhập:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Họ và tên:"));
        fullNameField = new JTextField();
        add(fullNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Số điện thoại:"));
        phoneField = new JTextField();
        add(phoneField);

        registerButton = new JButton("Đăng ký");
        backButton = new JButton("Quay lại");

        add(registerButton);
        add(backButton);

        registerButton.addActionListener(e -> registerUser());
        backButton.addActionListener(e -> mainFrame.showMainMenu());
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        try {
            User newUser = userService.registerUser(username, password, fullName, email, phone, "user");
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            mainFrame.showMainMenu();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
