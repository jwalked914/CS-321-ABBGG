package viewAndControl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

import data.UserDatabase;
import model.User;

public class ManageUsersPanel extends JPanel
{
    private UserDatabase userDatabase;
    private JScrollPane scrollPane;
    private JPanel listPanel;

    public ManageUsersPanel(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
        this.setLayout(new BorderLayout());
        this.setBackground(GUIColors.MID);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(50, 80));
        headerPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel titleLabel = new JLabel("Manage Users");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(GUIColors.MID);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        this.add(headerPanel, BorderLayout.NORTH);

        buildUserList();
    }

    private void buildUserList()
    {
        // model.User List
        ArrayList<User> users = userDatabase.getAllUsers();

        //create list panel
        listPanel =new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(GUIColors.MID);
        listPanel.setBorder(new EmptyBorder(20,40,20,40));

        for (User user:users)
        {
            RoundedPanel userRow = new RoundedPanel(10, GUIColors.CREAM);
            userRow.setLayout(new BorderLayout());
            userRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            userRow.setBorder(new EmptyBorder(10, 15, 10, 15));

            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            nameLabel.setForeground(GUIColors.DARK);

            // Buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setBackground(GUIColors.CREAM);

            //determine if demote or promote user's admin abilitiy
            String buttonText=user.getIsAdmin() ? "Demote": "Promote";
            RoundedButton promoteButton = new RoundedButton(buttonText, 150 ,50);
            promoteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            promoteButton.addActionListener(event ->
            {
                //get user admin status
                userDatabase.setAdmin(user.getUsername(), !user.getIsAdmin());
                refresh();
            });

            RoundedButton deleteButton = new RoundedButton("Delete", 150, 50);
            deleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            deleteButton.addActionListener(e ->
            {
                // Confirm deletion
                int confirmDelete=JOptionPane.showConfirmDialog(
                        this,
                        "Delete user \"" + user.getUsername() + "\"?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmDelete==JOptionPane.YES_OPTION)
                {
                    userDatabase.deleteUser(user.getUsername());
                    refresh();
                }
            });
            buttonPanel.add(promoteButton);
            buttonPanel.add(deleteButton);

            userRow.add(nameLabel, BorderLayout.WEST);
            userRow.add(buttonPanel, BorderLayout.EAST);

            listPanel.add(userRow);
            listPanel.add(Box.createVerticalStrut(10));
        }
        //wrap in scroll pane
        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add to the panel
        this.add(scrollPane, BorderLayout.CENTER);
    }
    public void refresh()
    {
        if (scrollPane != null)
        {
            this.remove(scrollPane);
        }
        buildUserList();
        revalidate();
        repaint();
    }

}
