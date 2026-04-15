import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class CollectionDialog extends JDialog
{
    public CollectionDialog(Component parent, Game game, User user, boolean isAdding)
    {
        setModal(true);
        setUndecorated(true);
        setSize(200, 300);
        setLocationRelativeTo(parent);

        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBackground(GUIColors.DARK);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel(isAdding ? "Add to Collection" : "Remove from Collection");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(GUIColors.LIGHT);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(GUIColors.DARK);

        ArrayList<UserCollection> collections = user.getCollections();

        for (UserCollection collection : collections)
        {
            RoundedButton button = new RoundedButton(collection.getName(), 50, 35);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            if (isAdding && collection.containsGame(game))
            {
                button.setEnabled(false);
            }
            else if (!isAdding && !collection.containsGame(game))
            {
                button.setEnabled(false);
            }
            else
            {
                button.addActionListener(e ->
                {
                    if (isAdding)
                        collection.addGame(game);
                    else
                        collection.removeGame(game);

                    dispose();
                    JOptionPane.showMessageDialog(parent,
                            game.getName() + (isAdding ? " added to " : " removed from ") + collection.getName());
                });
            }

            listPanel.add(button);
            listPanel.add(Box.createVerticalStrut(6));
        }

        JScrollPane listScroll = new JScrollPane(listPanel);
        listScroll.setBorder(null);
        listScroll.getViewport().setBackground(GUIColors.DARK);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottomPanel.setBackground(GUIColors.DARK);

        // only show + Add button when adding
        if (isAdding)
        {
            RoundedButton newCollectionButton = new RoundedButton("+ New", 90, 35);
            newCollectionButton.addActionListener(e ->
            {
                dispose();

                JDialog nameDialog = new JDialog();
                nameDialog.setModal(true);
                nameDialog.setUndecorated(true);
                nameDialog.setSize(280, 160);
                nameDialog.setLocationRelativeTo(parent);

                JPanel nameContent = new JPanel();
                nameContent.setLayout(new BoxLayout(nameContent, BoxLayout.Y_AXIS));
                nameContent.setBackground(GUIColors.DARK);
                nameContent.setBorder(new EmptyBorder(20, 20, 20, 20));

                JLabel nameLabel = new JLabel("Collection Name");
                nameLabel.setForeground(GUIColors.LIGHT);
                nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JTextField nameField = new JTextField();
                nameField.setBackground(GUIColors.MID);
                nameField.setForeground(GUIColors.LIGHT);
                nameField.setCaretColor(GUIColors.LIGHT);
                nameField.setBorder(new EmptyBorder(5, 8, 5, 8));
                nameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
                nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

                JPanel nameButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
                nameButtons.setBackground(GUIColors.DARK);

                RoundedButton confirmButton = new RoundedButton("Create", 100, 30);
                RoundedButton cancelButton = new RoundedButton("Cancel", 100, 30);

                confirmButton.addActionListener(ev ->
                {
                    String newName = nameField.getText().trim();
                    if (!newName.isEmpty())
                    {
                        user.createCollection(newName);
                        UserCollection newCol = user.getCollections().get(user.getCollections().size() - 1);
                        newCol.addGame(game);
                        JOptionPane.showMessageDialog(parent, game.getName() + " added to " + newName);
                    }
                    nameDialog.dispose();
                });

                cancelButton.addActionListener(ev -> nameDialog.dispose());

                nameButtons.add(cancelButton);
                nameButtons.add(confirmButton);

                nameContent.add(nameLabel);
                nameContent.add(Box.createVerticalStrut(8));
                nameContent.add(nameField);
                nameContent.add(Box.createVerticalStrut(10));
                nameContent.add(nameButtons);

                nameDialog.add(nameContent);
                nameDialog.setVisible(true);
            });
            bottomPanel.add(newCollectionButton);
        }

        RoundedButton cancelButton = new RoundedButton("Cancel", 90, 35);
        cancelButton.addActionListener(e -> dispose());
        bottomPanel.add(cancelButton);

        content.add(title, BorderLayout.NORTH);
        content.add(listScroll, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);

        add(content);
    }
}