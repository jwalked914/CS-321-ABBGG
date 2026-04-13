import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserSettingsPanel extends JPanel
{
    public UserSettingsPanel(String username) {
        this.setLayout(new BorderLayout());
        this.setBackground(GUIColors.MID);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(GUIColors.DARK);
        headerPanel.setPreferredSize(new Dimension(50, 80));
        headerPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(GUIColors.LIGHT);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Username display
        RoundedPanel userCard = new RoundedPanel(10, GUIColors.CREAM);
        userCard.setLayout(new BorderLayout());
        userCard.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(GUIColors.DARK);
        userCard.add(userLabel, BorderLayout.WEST);

        headerPanel.add(userCard, BorderLayout.EAST);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground((GUIColors.MID));
        contentPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Logout Button
        RoundedButton logoutButton = new RoundedButton("Log Out", 150, 50);
        logoutButton.setMaximumSize(new Dimension(400, 50));
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        logoutButton.addActionListener(e ->
        {
            // logout logic goes here
            System.out.println("User logged out");
        });


       JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       bottomRight.setBackground(GUIColors.MID);
       bottomRight.add(logoutButton);

       contentPanel.add(bottomRight,BorderLayout.SOUTH);

        this.add(contentPanel, BorderLayout.CENTER);
    }
}


