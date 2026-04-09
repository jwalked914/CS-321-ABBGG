import javax.swing.*;
import java.awt.*;

public class SidePanel {
    static final Color DARK  = new Color(0x72, 0x7D, 0x73);
    static final Color MID   = new Color(0xAA, 0xB9, 0x9A);
    static final Color LIGHT = new Color(0xD0, 0xDD, 0xD0);
    static final Color CREAM = new Color(0xF0, 0xF0, 0xD7);
    static final Color WHITE = new Color(0xFF, 0xFF, 0xFF);
    static final Color ERR   = new Color(0xB0, 0x4A, 0x4A);



    public static void main(String[] args) {

        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBackground(CREAM);

        frame.add(panel);

        frame.setSize(100, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}