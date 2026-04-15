import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class RoundedButton extends JButton
{
    private boolean hover = false;

    public RoundedButton(String text, int width, int height)
    {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setForeground(GUIColors.WHITE);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(width, height));

        addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                hover = true;
                repaint();
            }
            public void mouseExited(MouseEvent e)
            {
                hover = false;
                repaint();
            }
        });
    }

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