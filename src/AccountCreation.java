import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class AccountCreation {
    static final Color DARK  = new Color(0x72, 0x7D, 0x73);
    static final Color MID   = new Color(0xAA, 0xB9, 0x9A);
    static final Color LIGHT = new Color(0xD0, 0xDD, 0xD0);
    static final Color CREAM = new Color(0xF0, 0xF0, 0xD7);
    static final Color WHITE = new Color(0xFF, 0xFF, 0xFF);
    static final Color ERR   = new Color(0xB0, 0x4A, 0x4A);

    private JPanel accountCreationPanel;
    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel         statusLabel;
    private JButton         createAccountButton;
    // private UserDatabase userDB;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AccountCreation();
        });
    }

    public AccountCreation()
    {
        JFrame frame = buildFrame();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JFrame buildFrame()
    {
        JFrame frame = new JFrame("Account Creation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Outer background panel to fill the whole window with CREAM
        JPanel background=new JPanel(new GridBagLayout());
        background.setBackground(CREAM);
        background.setBorder(new EmptyBorder(60,60,60,60));
        frame.add(background);
        background.add(buildCard(frame)); // white rounded card in center of screen
        return frame;
    }

    private JPanel buildCard(JFrame frame)
    {
        accountCreationPanel=new RoundedPanel(20,LIGHT);
        accountCreationPanel.setLayout(new BoxLayout(accountCreationPanel, BoxLayout.Y_AXIS));
        accountCreationPanel.setBorder(new EmptyBorder(60,150,60,150));
        accountCreationPanel.setPreferredSize(new Dimension(1500,900));

        //username field
        JLabel userLabel= new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userLabel.setForeground(DARK);

        //password tag
        JLabel passLabel= new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        passLabel.setForeground(DARK);

        // confirm password field
        JLabel confirmLabel= new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        confirmLabel.setForeground(DARK);

        usernameField = new JTextField(80);
        passwordField = new JPasswordField(80);
        confirmField = new JPasswordField(80);

        //status label
        statusLabel=new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI",Font.PLAIN,11));
        statusLabel.setForeground(ERR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //login button
        createAccountButton=new RoundedButton("Create Account");
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
            else
            {
            }
        });


        //assemble login parts
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

    private JPanel leftAlign(JComponent c)
    {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height + 2));
        p.add(c);
        return p;
    }

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