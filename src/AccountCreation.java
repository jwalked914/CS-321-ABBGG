/**
 * Displays the account creation screen for the ABBG Board Games application.
 * Handles user creation by adding credentials to the UserDatabase.
 * On successful account creation, closes the account creation window and opens the main screen.
 *
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class AccountCreation {

    /** The main container panel holding all account creation UI components */
    private JPanel accountCreationPanel;
    /** Input field for the user's username */
    private JTextField usernameField;
    /** Input field for the user's password */
    private JPasswordField passwordField;
    /** Second input for the user's password to see if they typed it in correctly */
    private JPasswordField confirmField;
    /** Displays error or validation message for the user */
    private JLabel statusLabel;
    /** Button that submits the account creation form */
    private JButton createAccountButton;
    /** The user database used to place the user's newly made info */
    private final UserDatabase userDB;


    /**
     * Application entry point. Builds the backend database chain and
     * launches the account creation screen on the Swing event dispatch thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        GameDatabase gameDB = new GameDatabase();
        UserDatabase userDB = new UserDatabase(gameDB);

        SwingUtilities.invokeLater(() -> {
            new AccountCreation(userDB);
        });
    }

    /**
     * Constructs the account creation screen and displays it.
     *
     * @param userDB the user database used for authentication
     */
    public AccountCreation(UserDatabase userDB)
    {
        this.userDB=userDB;
        JFrame frame = buildFrame();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    /**
     * Builds and configures the main application frame.
     *
     * @return the fully constructed JFrame for the account creation screen
     */
    private JFrame buildFrame()
    {
        JFrame frame = new JFrame("Account Creation");
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
     * Builds the central account creation card panel containing all UI components.
     *
     * @param frame the parent frame used for dialog interactions
     * @return the constructed login panel
     */
    private JPanel buildCard(JFrame frame)
    {
        accountCreationPanel=new RoundedPanel(20,GUIColors.LIGHT);
        accountCreationPanel.setLayout(new BoxLayout(accountCreationPanel, BoxLayout.Y_AXIS));
        accountCreationPanel.setBorder(new EmptyBorder(100,150,100,150));
        accountCreationPanel.setPreferredSize(new Dimension(1500,900));

        //welcome screem for creating account
        JLabel welcomeCreationTag = new JLabel("Account Creation");
        welcomeCreationTag.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeCreationTag.setFont(new Font("Segoe UI", Font.BOLD,30));
        welcomeCreationTag.setForeground(GUIColors.DARK);

        //username field
        JLabel userLabel= new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userLabel.setForeground(GUIColors.DARK);

        //password tag
        JLabel passLabel= new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passLabel.setForeground(GUIColors.DARK);

        // confirm password field
        JLabel confirmLabel= new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        confirmLabel.setForeground(GUIColors.DARK);

        usernameField = new JTextField(80);
        passwordField = new JPasswordField(80);
        confirmField = new JPasswordField(80);

        //status label
        statusLabel=new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI",Font.PLAIN,11));
        statusLabel.setForeground(GUIColors.ERR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //login button
        createAccountButton=new RoundedButton("Create Account",100,100);
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //create action listener for createAccountButton
        createAccountButton.addActionListener(event->
        {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmField.getPassword());
            // check empty fields FIRST, before hitting the database
            if (username.isEmpty() || password.isEmpty())
            {
                statusLabel.setText("Please fill in both fields.");
                statusLabel.revalidate();
                statusLabel.repaint();
            }
            else if (!password.equals(confirmPassword))
            {
                statusLabel.setText("Passwords do not match.");
                statusLabel.revalidate();
                statusLabel.repaint();
            }
            else if (userDB.isUsernameTaken(username))
            {
                statusLabel.setText("Username is taken.");
                statusLabel.revalidate();
                statusLabel.repaint();
            }
            else{
                //users are initalized to non-admins
                //userDatabase handles creating users internally
                userDB.addUser(username,password,false);
                frame.dispose();
                new LoginScreen(userDB);
            }
        });

        //assemble login parts
        accountCreationPanel.add(welcomeCreationTag);
        accountCreationPanel.add(Box.createVerticalStrut(6));
        accountCreationPanel.add(leftAlign(userLabel));
        accountCreationPanel.add(Box.createVerticalStrut(10));
        accountCreationPanel.add(usernameField);
        accountCreationPanel.add(Box.createVerticalStrut(18));
        accountCreationPanel.add(leftAlign(passLabel));
        accountCreationPanel.add(Box.createVerticalStrut(6));
        accountCreationPanel.add(passwordField);
        accountCreationPanel.add(Box.createVerticalStrut(18));
        accountCreationPanel.add(leftAlign(confirmLabel));
        accountCreationPanel.add(Box.createVerticalStrut(6));
        accountCreationPanel.add(confirmField);
        accountCreationPanel.add(Box.createVerticalStrut(12));
        accountCreationPanel.add(createAccountButton);
        accountCreationPanel.add(Box.createVerticalStrut(6));
        accountCreationPanel.add(statusLabel);

        return accountCreationPanel;
    }
    /**
     * Wraps a component in a left-aligned panel for consistent layout.
     *
     * @param c the component to align
     * @return a JPanel containing the component aligned to the left
     */
    private JPanel leftAlign(JComponent c)
    {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height + 2));
        p.add(c);
        return p;
    }




}