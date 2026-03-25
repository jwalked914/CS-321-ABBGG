import java.util.ArrayList;

/**
 * Collection class allows users to store unlimited games
 * and create unlimited amount of collections
 * It also inherits from the GameDatabase to access the full game library
 */

public class Collection extends GameDatabase
{
    private String name;
    private ArrayList<String> gameIDs;

    /**
     *  Name and list of Collections made in the library
     */

    /**
     * Creates a new collection with a given ID and name.
     * @param  gameIDs collection
     * @param name display collection name
     */
    public Collection(String name, ArrayList<Game> gameDB)
    {
        super(gameDB);
        this.name = name;
        this.gameIDs = new ArrayList<>();
    }

    /**
     * Methods
     * addGame adds a game to the list
     * removeGame would remove the game from a list
     * boolean containsGame to check iff it is in the list
     */

    public void addGame(String game)
    {
        gameIDs.add(game);
    }

    public void removeGame(String gameId)
    {
        gameIDs.remove(gameId);
    }

    public boolean containsGame(String gameId)
    {
        return gameIDs.contains(gameId);
    }



    /**
     *
     * @return
     * the id of Collection
     * name of Collection
     * games in the Collection
     */


    public String getName()
    {
        return name;
    }


    public ArrayList<String> getGameIds()
    {
        return gameIDs;
    }


}
