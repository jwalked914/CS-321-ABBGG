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
        createdSidePanel=new RoundedPanel(20,GUIColors.LIGHT);
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

    private JPanel leftAlign(JComponent c)
    {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height + 2));
        p.add(c);
        return p;
    }

}