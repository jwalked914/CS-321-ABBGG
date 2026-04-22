import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidePanel extends JPanel
{
    /** The main container panel holding all SidePanel UI components */
    //private JPanel createdSidePanel;

    private final MainFrame mainFrame;
    private User currentUser;

    /** Constructs the SidePanel screen and displays it. */
    public SidePanel(MainFrame mainFrame, User CurrentUser)
    {

        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GUIColors.LIGHT);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setPreferredSize(new Dimension(200, 900));

        buildComponents();
    }

    /**
     *  Creates button layout for side panel.
     *
     */
    private void buildComponents() {

        //username
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        userLabel.setForeground(GUIColors.DARK);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // home button
        JButton homeButton = new RoundedButton("Home", 40, 40);
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.addActionListener(e -> mainFrame.navigateHome());

        // library button
        JButton libraryButton = new RoundedButton("Library", 40, 40);
        libraryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        libraryButton.addActionListener(e -> mainFrame.navigateLibrary());

        // settings button
        JButton settingsButton = new RoundedButton("Settings", 20, 10);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(e -> mainFrame.navigateSettings());

        // logout button
        JButton logoutButton = new RoundedButton("Logout", 20, 10);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> mainFrame.logout());

        add(Box.createVerticalStrut(6));
        add(userLabel);
        add(Box.createVerticalStrut(6));
        add(homeButton);
        add(Box.createVerticalStrut(6));
        add(libraryButton);

        // manage user button (only display for users marked as admin)
        if (currentUser.getIsAdmin())
        {
            JButton manageUserButton= new RoundedButton("Manage Users", 40, 40);
            manageUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            manageUserButton.addActionListener(e -> mainFrame.navigateManageUsers());
            add(manageUserButton);
            add(Box.createVerticalStrut(6));
        }

        add(Box.createVerticalStrut(6));
        add(settingsButton);
        add(Box.createVerticalStrut(6));
        add(logoutButton);
    }

}
