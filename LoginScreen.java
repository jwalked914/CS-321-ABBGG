/**
 * Displays the login screen for the ABBG Board Games application.
 * Handles user authentication by validating credentials against the UserDatabase.
 * On successful login, closes the login window and opens the main screen.
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class LoginScreen
{
    static final Color DARK  = new Color(0x72, 0x7D, 0x73);
    static final Color MID   = new Color(0xAA, 0xB9, 0x9A);
    static final Color LIGHT = new Color(0xD0, 0xDD, 0xD0);
    static final Color CREAM = new Color(0xF0, 0xF0, 0xD7);
    static final Color WHITE = new Color(0xFF, 0xFF, 0xFF);
    static final Color ERR   = new Color(0xB0, 0x4A, 0x4A);

    /** Displays the application title. */
    private JLabel         welcomeTag;
    /** Displays error or validation messages to the user. */
    private JLabel         statusLabel;
    /** Input field for the user's username. */
    private JTextField     usernameField;
    /** Input field for the user's password, masking characters as typed. */
    private JPasswordField passwordField;
    /** Input field for the user's password, masking characters as typed. */
    private JButton        loginButton;
    /** Button that submits the login form. */
    //private JButton        exitButton;
    /** The main container panel holding all login UI components. */
    private JPanel         loginPanel;
    /** The user database used to validate login credentials. */
    private UserDatabase userDB;

    /**
     * Application entry point. Builds the backend database chain and
     * launches the login screen on the Swing event dispatch thread.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            // build the backend
            File gamesFile = new File("bgg90Games.xml");
            FileScannerXML gameScanner = new FileScannerXML(gamesFile, null);
            ArrayList<Game> gameList   = gameScanner.parseGamesFromXML();
            GameDatabase gameDB        = new GameDatabase(gameList);
            UserDatabase userDB        = new UserDatabase(gameDB);

            // hand it to the screen
            new LoginScreen(userDB);
        });
    }
    /**
     * Constructs the login screen and displays it.
     *
     * @param userDB the user database used for authentication
     */
    public LoginScreen(UserDatabase userDB)
    {
        this.userDB=userDB;
        JFrame frame=buildFrame(); //build login screen first
        frame.pack(); //size the window
        frame.setLocationRelativeTo(null); //center it
        frame.setVisible(true);
    }
    /**
     * Builds and configures the main application frame.
     *
     * @return the fully constructed JFrame for the login screen
     */
    private JFrame buildFrame()
    {
        JFrame frame= new JFrame("ABBG Board Games - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        frame.setSize(1500,900);

        //Outer background panel to fill the whole window with CREAM
        JPanel background=new JPanel(new GridBagLayout());
        background.setBackground(CREAM);
        background.setBorder(new EmptyBorder(60,60,60,60));
        frame.add(background);
        background.add(buildCard(frame)); // white rounded card in center of screen
        return frame;
    }
    /**
     * Builds the central login card panel containing all UI components.
     *
     * @param frame the parent frame used for dialog interactions
     * @return the constructed login panel
     */
    private JPanel buildCard(JFrame frame)
    {
        loginPanel=new RoundedPanel(20,LIGHT);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(new EmptyBorder(60,150,60,150));
        loginPanel.setPreferredSize(new Dimension(1500,900));

        //welcome sign
        JLabel subtitle=new JLabel("Welcome To");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(DARK);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        //welcome tag
        welcomeTag=new JLabel("ABBG Board Games");
        welcomeTag.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeTag.setForeground(DARK);
        welcomeTag.setAlignmentX(Component.CENTER_ALIGNMENT);

        //username field
        JLabel userLabel= new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userLabel.setForeground(DARK);

        //password tag
        JLabel passLabel= new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passLabel.setForeground(DARK);

        usernameField = new JTextField(80);
        passwordField = new JPasswordField(80);

        //create account link
        JLabel createLink=new JLabel("Create Account");
        createLink.setFont(new Font("Segoe UI", Font.PLAIN,11));
        createLink.setForeground(MID);
        createLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createLink.setAlignmentX(Component.LEFT_ALIGNMENT);
        createLink.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                createLink.setForeground(DARK);
            }
            public void mouseExited(MouseEvent e)
            {
                createLink.setForeground(MID);
            }
            public void mouseClicked(MouseEvent e)
            {
                JOptionPane.showMessageDialog(frame,
                        "Account creation coming soon!",
                        "Create Account",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //status label
        statusLabel=new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI",Font.PLAIN,11));
        statusLabel.setForeground(ERR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //login button
        loginButton=new RoundedButton("Log In");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        //create action listener for loginbutton
        loginButton.addActionListener(event->
        {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
            // check empty fields FIRST, before hitting the database
            if (username.isEmpty() || password.isEmpty())
            {
                statusLabel.setText("Please fill in both fields.");
                statusLabel.revalidate();
                statusLabel.repaint();
            }
            else
            {
                User user = userDB.validateCredentials(username, password);

                if (user != null)
                {
                    frame.dispose();
                    // open main screen here later
                }
                else
                {
                    statusLabel.setText("Invalid username or password.");
                    statusLabel.revalidate();
                    statusLabel.repaint();
                    passwordField.setText("");
                }
            }
        });

        passwordField.addActionListener(loginButton.getActionListeners()[0]);


        //assemble login parts
        loginPanel.add(Box.createVerticalStrut(4));
        loginPanel.add(subtitle);
        loginPanel.add(Box.createVerticalStrut(4));
        loginPanel.add(welcomeTag);
        loginPanel.add(Box.createVerticalStrut(6));
        loginPanel.add(leftAlign(userLabel));
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(usernameField);
        loginPanel.add(Box.createVerticalStrut(18));
        loginPanel.add(leftAlign(passLabel));
        loginPanel.add(Box.createVerticalStrut(6));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createVerticalStrut(8));
        loginPanel.add(leftAlign(createLink));
        loginPanel.add(Box.createVerticalStrut(8));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(6));
        loginPanel.add(statusLabel);

        return loginPanel;
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
    /**
     * A custom JPanel with rounded corners and a configurable background color.
     * Used to create the card-style UI container.
     */
    static class RoundedPanel extends JPanel
    {
        private final int radius;
        private final Color background;
        /**
         * Constructs a rounded panel.
         *
         * @param radius the corner radius
         * @param background the background color
         */
        public RoundedPanel(int radius, Color background) {
            this.radius = radius;
            this.background = background;
            setOpaque(false);
        }
        /**
         * Paints the rounded background of the panel.
         *
         * @param g the Graphics context
         */
        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(background);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 4, getHeight() - 4, radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }
    /**
     * A custom JButton with rounded corners and hover effects.
     */
    static class RoundedButton extends JButton
    {
        private boolean hover=false;
        /**
         * Constructs a rounded button with the given label.
         *
         * @param text the button text
         */
        public RoundedButton(String text)
        {
            /**
             * Constructs a rounded button with the given label.
             *
             * @param text the button text
             */
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD,14));
            setForeground(WHITE);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(290,50));
            //setMaximumSize(new Dimension(290,50));

            addMouseListener(new MouseAdapter()
            {
                public void mouseEntered(MouseEvent e)
                {
                    hover=true;
                    repaint();
                }
                public void mouseExited(MouseEvent e)
                {
                    hover=false;
                    repaint();
                }
            });
        }
        /**
         * Paints the button with a rounded shape and hover color effect.
         *
         * @param g the Graphics context
         */
        @Override
        protected void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hover ? MID : DARK);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
            g2.dispose();
            super.paintComponent(g);
        }
    }

}
