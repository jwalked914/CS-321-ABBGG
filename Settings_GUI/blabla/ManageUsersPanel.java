import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class ManageUsersPanel extends JPanel
{
    public ManageUsersPanel()
    {
        this.setLayout(new BorderLayout());
        this.setBackground(GUIColors.MID);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(50,80));
        headerPanel.setBorder(new EmptyBorder(8,10,8,10));

        JLabel titleLabel = new JLabel("Manage Users");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD,22));
        titleLabel.setForeground(GUIColors.MID);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        this.add(headerPanel, BorderLayout.NORTH);

        // PLACEHOLDER - REPLACE WITH ACTUAL USER LIST -
        ArrayList<String> users = new ArrayList<>();
        users.add("Snoopy");
        users.add("Tung Tung Sahur");
        users.add("Shalyssa");
        users.add("John Pork");

        // User List
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(GUIColors.MID);
        listPanel.setBorder(new EmptyBorder(20,40,20,40));

        for(String username:users)
        {
            RoundedPanel userRow = new RoundedPanel(10, GUIColors.CREAM);
            userRow.setLayout(new BorderLayout());
            userRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            userRow.setBorder(new EmptyBorder(10, 15, 10, 15));

            JLabel nameLabel = new JLabel(username);
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            nameLabel.setForeground(GUIColors.DARK);

            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setBackground(GUIColors.CREAM);

            RoundedButton promoteButton = new RoundedButton("Promote", 150 ,50);
            promoteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            promoteButton.addActionListener(e ->
            {
                // NEEDS TO BE WIRED TO .promoteUser()
                System.out.println("Promoted: " + username);
            });

            RoundedButton deleteButton = new RoundedButton("Delete", 150, 50);
            deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            deleteButton.addActionListener(e ->
            {
                // NEEDS TO BE WIRED TO .deleteUser()
                System.out.println("Deleted: " + username);
            });

            buttonPanel.add(promoteButton);
            buttonPanel.add(deleteButton);

            userRow.add(nameLabel, BorderLayout.WEST);
            userRow.add(buttonPanel, BorderLayout.EAST);

            listPanel.add(userRow);
            listPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane, BorderLayout.CENTER);
    }
}
