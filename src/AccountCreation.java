import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

        usernameField = new JTextField(80);
        passwordField = new JPasswordField(80);

        //assemble login parts
        accountCreationPanel.add(Box.createVerticalStrut(6));
        accountCreationPanel.add(leftAlign(userLabel));
        accountCreationPanel.add(Box.createVerticalStrut(10));
        accountCreationPanel.add(usernameField);
        accountCreationPanel.add(Box.createVerticalStrut(18));
        accountCreationPanel.add(leftAlign(passLabel));
        accountCreationPanel.add(Box.createVerticalStrut(6));
        accountCreationPanel.add(passwordField);

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

}