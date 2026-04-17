import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UserSettingsPanel extends JPanel
{
    public UserSettingsPanel(User user) {
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

        JLabel userLabel = new JLabel("Logged in as: " + user.getUsername());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(GUIColors.DARK);
        userCard.add(userLabel, BorderLayout.WEST);

        headerPanel.add(userCard, BorderLayout.EAST);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground((GUIColors.MID));
        contentPanel.setBorder(new EmptyBorder(40, 40, 40, 40));


       JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
       bottomRight.setBackground(GUIColors.MID);


       contentPanel.add(bottomRight,BorderLayout.SOUTH);

        this.add(contentPanel, BorderLayout.CENTER);
    }
}


