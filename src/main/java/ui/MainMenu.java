package ui;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    private JButton loginButton, registerButton;
    private MainFrame mainFrame;

    public MainMenu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        loginButton = new JButton("Đăng nhập");
        registerButton = new JButton("Đăng ký");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginButton, gbc);

        gbc.gridy = 1;
        add(registerButton, gbc);

        loginButton.addActionListener(e -> mainFrame.showLoginForm());
        registerButton.addActionListener(e -> mainFrame.showRegisterForm());
    }
}
