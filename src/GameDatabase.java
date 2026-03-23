import java.util.ArrayList;

/**
 * Represents a database of board games that provides access to and
 * operations on a collection of {@link Game} objects.
 *
 * <p>The database acts as a container for games loaded from an external
 * source (such as an XML parser) and serves as the primary data source
 * for queries and user collections.</p>
 */
public class GameDatabase
{
    private ArrayList<Game> games;

    /**
     * Constructs a game database of Game objects
     * @param gameDB parsed boardgame array list
     */
    public GameDatabase(ArrayList<Game> gameDB)
    {
        this.games = gameDB;
    }

    public void printAllGames()
    {
        for(Game g : games)
        {
            System.out.println(g);
        }
    }

    public ArrayList<Game> getAllGames()
    {
        return games;
    }

}
