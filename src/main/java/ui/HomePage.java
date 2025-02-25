package ui;

import model.User;
import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {
    private MainFrame mainFrame;
    private JLabel welcomeLabel;
    private JButton backButton;

    public HomePage(MainFrame mainFrame, User user) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Chào mừng, " + user.getFullName() + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        backButton = new JButton("Quay lại");
        backButton.addActionListener(e -> mainFrame.showMainMenu());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        add(welcomeLabel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
