import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import javax.swing.*;

public class main {
    public static void main (String []args) {
        File file = new File("bgg90Games.xml");
        FileScannerXML scanner = new FileScannerXML(file);

        ArrayList<Game> gameList = scanner.parseGamesFromXML();
        GameDatabase db = new GameDatabase(gameList);

        SwingUtilities.invokeLater(()->
        {
            JFrame frame = new JFrame("ABBGG");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900,700);

            GameBrowserPanel panel = new GameBrowserPanel(db);
            frame.add(panel);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.requestFocusInWindow();
        });

    }
}
