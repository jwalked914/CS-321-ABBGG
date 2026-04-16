import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class SidePanel {
    /** The main container panel holding all SidePanel UI components */
    private JPanel createdSidePanel;
    /** Button to send the user to their library */
    private JButton libraryButton;
    /** Button to send the user to the settings menu */
    private JButton settingsButton;
    /** Button to send the user to logout */
    private JButton logoutButton;

    /**
     * Application entry point. Builds the backend database chain and
     * launches the Side Panel screen on the Swing event dispatch thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SidePanel();
        });
    }

    /** Constructs the SidePanel screen and displays it. */
    public SidePanel()
    {
        JFrame frame = buildFrame();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Builds and configures the main application frame.
     *
     * @return the fully constructed JFrame for the SidePanel screen
     */
    private JFrame buildFrame()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Outer background panel to fill the whole window with CREAM
        JPanel background=new JPanel(new GridBagLayout());
        background.setBackground(GUIColors.CREAM);
        background.setBorder(new EmptyBorder(60,60,60,60));
        frame.add(background);
        background.add(buildCard(frame)); // white rounded card in center of screen
        return frame;
    }

    /**
     * Builds the central side panel containing all UI components.
     *
     * @param frame the parent frame used for dialog interactions
     * @return the constructed side panel
     */
    private JPanel buildCard(JFrame frame) {
        createdSidePanel=new RoundedPanel(20,GUIColors.LIGHT);
        createdSidePanel.setLayout(new BoxLayout(createdSidePanel, BoxLayout.Y_AXIS));
        createdSidePanel.setBorder(new EmptyBorder(10,20,10,20));
        createdSidePanel.setPreferredSize(new Dimension(200,900));

        //username field
        JLabel userLabel= new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI",Font.BOLD,25));
        userLabel.setForeground(GUIColors.DARK);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //library button
        libraryButton=new RoundedButton("Library",40,40);
        libraryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // create action listener for libraryButton
        libraryButton.addActionListener(event->{});

        //settings button
        settingsButton=new RoundedButton("Settings",20,10);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // action listener for the settingsButton
        settingsButton.addActionListener(event->{});

        //logout button
        logoutButton=new RoundedButton("Logout",20,10);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // action listener for the logoutButton
        logoutButton.addActionListener(event->{});



        createdSidePanel.add(Box.createVerticalStrut(6));
        createdSidePanel.add(userLabel);
        createdSidePanel.add(Box.createVerticalStrut(6));
        createdSidePanel.add(libraryButton);
        createdSidePanel.add(Box.createVerticalStrut(700));
        createdSidePanel.add(settingsButton);
        createdSidePanel.add(Box.createVerticalStrut(6));
        createdSidePanel.add(logoutButton);


        return createdSidePanel;
    }


}