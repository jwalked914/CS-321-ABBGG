import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class SidePanel {

    private JPanel createdSidePanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SidePanel();
        });
    }

    public SidePanel()
    {
        JFrame frame = buildFrame();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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

    private JPanel buildCard(JFrame frame) {
        createdSidePanel=new SidePanel.RoundedPanel(20,GUIColors.LIGHT);
        createdSidePanel.setLayout(new BoxLayout(createdSidePanel, BoxLayout.Y_AXIS));
        createdSidePanel.setBorder(new EmptyBorder(60,150,60,150));
        createdSidePanel.setPreferredSize(new Dimension(300,900));

        //username field
        JLabel userLabel= new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        userLabel.setForeground(GUIColors.DARK);

        createdSidePanel.add(Box.createVerticalStrut(6));
        createdSidePanel.add(leftAlign(userLabel));

        return createdSidePanel;
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
            setForeground(GUIColors.WHITE);
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
            g2.setColor(hover ? GUIColors.MID : GUIColors.DARK);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private JPanel leftAlign(JComponent c)
    {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height + 2));
        p.add(c);
        return p;
    }

}