import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;

public class main {
    public static void main (String []args) {
        File file = new File("bgg90Games.xml");
        FileScannerXML scanner = new FileScannerXML(file);

        ArrayList<Game> gameList = scanner.parseGamesFromXML();
        GameDatabase db = new GameDatabase(gameList);
        db.filterByCategory(new HashSet<>(Collections.singleton("Adventure")));
        db.filterByMechanic(new HashSet<>(Collections.singleton("Deck Construction")));

    }
}
