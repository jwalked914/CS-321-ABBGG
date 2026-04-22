import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for admin to manage user accounts.
 * Displays a scrollable list of all users with a Promote and Delete button.
 *
 */
public class ManageUsersPanel extends JPanel
{
    private final UserDatabase UserDB;
    private final JPanel listPanel;

    /**
     * ManageUserPanel constructor builds initial user list.
     *
     * @param UserDB UserDatabase to retrieve, promote, and delete users
     */
    public ManageUsersPanel(UserDatabase UserDB)
    {
        this.UserDB = UserDB;
        this.setLayout(new BorderLayout());
        this.setBackground(GUIColors.MID);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(50, 80));
        headerPanel.setBackground(GUIColors.DARK);
        headerPanel.setOpaque(true);
        headerPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel titleLabel = new JLabel("Manage Users");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(GUIColors.CREAM);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        this.add(headerPanel, BorderLayout.NORTH);

        // User List
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(GUIColors.MID);
        listPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane, BorderLayout.CENTER);

        buildUserList();
    }

    /**
     * Constructs the initial user list build and handles any updates
     * from promotions and deletions.
     *
     */
    private void buildUserList()
    {
        listPanel.removeAll();

        for (User user : UserDB.getUsers())
        {
            String username = user.getUsername();

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

            RoundedButton promoteButton = new RoundedButton("Promote", 150, 50);
            promoteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            promoteButton.addActionListener(e ->
            {
                UserDB.setAdmin(username, true);
                buildUserList();
            });

            RoundedButton deleteButton = new RoundedButton("Delete", 150, 50);
            deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            deleteButton.addActionListener(e ->
            {
                UserDB.deleteUser(username);
                buildUserList();
            });

            buttonPanel.add(promoteButton);
            buttonPanel.add(deleteButton);

            userRow.add(nameLabel, BorderLayout.WEST);
            userRow.add(buttonPanel, BorderLayout.EAST);

            listPanel.add(userRow);
            listPanel.add(Box.createVerticalStrut(10));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

}
