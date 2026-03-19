import java.util.List;
import java.io.File;

public class main {
    public static void main (String []args) {
        File file = new File("bgg90Games.xml");
        FileScannerXML GameDB = new FileScannerXML(file);

        List<Game> games = GameDB.parseXMLGame();

        for(Game g : games) {
            System.out.println(g);
        }
    }
}
