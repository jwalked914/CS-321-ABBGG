import java.util.ArrayList;
import java.io.File;

public class main {
    public static void main (String []args) {
        File file = new File("bgg90Games.xml");
        FileScannerXML scanner = new FileScannerXML(file);

        ArrayList<Game> gameList = scanner.parseGamesFromXML();
        GameDatabase db = new GameDatabase(gameList);

        db.printAllGames();
    }
}
