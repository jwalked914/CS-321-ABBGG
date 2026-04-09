
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class  SidePanel
{
    private JButton library;
    private JButton settings;
    private JButton logout;
    private JPanel sidePanel;

    public static void main()
    {
        JFrame frame = new JFrame("SidePanel");
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton libraryButton = new JButton("Library");
        panel.add(button);

        // Add panel to frame
        frame.add(panel);

        // Show window
        frame.setVisible(true);
    }
}