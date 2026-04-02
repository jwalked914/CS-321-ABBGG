/**
 * Collection class allows users to store unlimited games
 * and create unlimited amount of collections
 * It also inherits from the GameDatabase to access the full game library
 */




/**
 * UserCollection inherits GameDatabase
 * This class will have methods that add, remove, and shows that it contains the users games
 */
public class UserCollection extends GameDatabase
{
    private String name;
    private GameDatabase masterDB;


    /**
     *  This is a  constructor that creates an empty collection with a name.
     *  the masterDB will be used to check if the game is real and store the game into a collection and be used to retrieve it as well.
     */
    public UserCollection(String name, GameDatabase masterDB)
    {
        super();
        this.name = name;
        this.masterDB = masterDB;
    }


    /**
     * This method Adds a game by ID to a collection
     * It checks if the game does not exist in the master database then returns nothing if it actually doesn't exist.
     * It also checks if the game is already in the collection and if not then it adds the game to the collection.
     * @param gameId this is the gameID
     */
    public void addGame(String gameId)
    {
        Game g = masterDB.getGameById(Integer.parseInt(gameId));
        if (g == null) return;
        if (containsGame(gameId)) return;
        super.addGame(g);
    }


    /**
     * This checks if the game exists and if it already is not in the collection
     * if it is in the collection, then it deletes the gameid from the collection.
     * @param gameId
     */
    public void removeGame(String gameId)
    {
        Game g = masterDB.getGameById(Integer.parseInt(gameId));
        if (!containsGame(gameId)) return;
        super.removeFromMaps(g);
    }

    /**
     * This checks if the collection contains the game ID
     * @param gameId
     * @return this returns if the game in the collection or not by boolean, true or false.
     */

    public boolean containsGame(String gameId)
    {
        return getGameById(Integer.parseInt(gameId)) != null;
    }

    /**
     * This returns the name of the Collection
     * @return the name of the collection
     */
    public String getName()
    {
        return name;
    }
}
