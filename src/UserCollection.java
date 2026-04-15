/**
 * Collection class allows users to store unlimited games
 * and create unlimited amount of collections
 */

/**
 * UserCollection inherits GameDatabase
 * This class will have methods that add and remove the user's selected games
 */
public class UserCollection extends GameDatabase
{
    private final String name;

    /**
     *  Constructor that creates an empty collection with user-inputted name.
     */
    public UserCollection(String name)
    {
        super();
        this.name = name;
    }


    /**
     * Adds game object to a collection.
     *
     * @param game add game
     */
    public void addGame(Game game)
    {
        if(!containsGame(game))
        {
            super.addGame(game);
        }
    }


    /**
     * Removes user selected game from a collection
     *
     * @param game object
     */
    public void removeGame(Game game)
    {
        super.removeFromMaps(game);
    }

    /**
     * Checks whether a game is already in collection
     *
     * @param game
     * @return boolean
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

