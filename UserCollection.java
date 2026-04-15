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
    //private final GameDatabase masterDB;


    /**
     *  This is a  constructor that creates an empty collection with a name.
     *  the masterDB will be used to check if the game is real and store the game into a collection and be used to retrieve it as well.
     */
    public UserCollection(String name)//GameDatabase masterDB)
    {
        super();
        this.name = name;
        //this.masterDB = masterDB;
    }


    /**
     * This method Adds a game by ID to a collection
     * It checks if the game does not exist in the master database then returns nothing if it actually doesn't exist.
     * It also checks if the game is already in the collection and if not then it adds the game to the collection.
     * @param game add game
     */
    public void addGame(Game game)
    {
        //if (!masterDB.getAllGames().contains(game)) return;
        if (getAllGames().contains(game))
        {
            return;
        }
        super.addGame(game);
    }


    /**
     * This checks if the game exists and if it already is not in the collection
     * if it is in the collection, then it deletes the game from the collection.
     * @param game remove game
     */
    public void removeGame(Game game)
    {

        if (!getAllGames().contains(game)) return;
        super.removeFromMaps(game);
    }

    /**
     * This checks if the collection contains the game ID
     * @param game
     * @return this returns if the game in the collection or not by boolean, true or false.
     */

    public boolean containsGame(Game game)
    {
        return getAllGames().contains(game);
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