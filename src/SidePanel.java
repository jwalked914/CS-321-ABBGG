import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class SidePanel {

    private JPanel createdSidePanel;
    private JButton libraryButton;
    private JButton settingsButton;
    private JButton logoutButton;

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

    private JPanel leftAlign(JComponent c)
    {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height + 2));
        p.add(c);
        return p;
    }

}