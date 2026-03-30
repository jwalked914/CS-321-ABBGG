import java.util.ArrayList;


/**
 * Collection class allows users to store unlimited games
 * and create unlimited amount of collections
 * It also inherits from the GameDatabase to access the full game library
 */

public class UserCollection extends GameDatabase
{
    /** This displays the name of the Colelction*/
    private String name;

    /** List of the games ID String that is in the collection*/
    private ArrayList<String> gameIds;

    /** Is the GameDatabase that helps validate and locate games in collection */
    private GameDatabase gameDB;


    /** 
    * This is a Constructor that creates a new Collection 
    * @param name displays the collection's name
    * @param gameDB This validates if the game exist, or is already in a collection
    */
    public UserCollection(String name, GameDatabase gameDB)
    {
        super(gameDB.getAllGames());
        this.name = name;
        this.gameDB = gameDB;
        this.gameIds = new ArrayList<>();
    }

    
    /**
    * This is a method that adds game into a collection by its Id
    * This checks and validates if a game exist or is already in the collection
    */
    public void addGame(String gameId)
    {
        // This checks if the game exists 
        Game g = gameDB.getGameById(Integer.parseInt(gameId));
        if (g == null) return;

        // This checks if the game is already in the collection
        if (containsGame(gameId)) return;

        // This adds the game into the collection onces everything is checked
        gameIds.add(gameId);
    }


    /**
    * This is a method that removes the game 
    * This validates if the game is alreadyy not in the list
    */
    public void removeGame(String gameId)
    {
        // Checks if the game is alreadyy not in the list
        if (!containsGame(gameId)) return;

        // Removes the game if it is in the list
        gameIds.remove(gameId);
    }
    

    /**
    * This method checks if the game exists
    */
    public boolean containsGame(String gameId)
    {
        return gameIds.contains(gameId);
    }

    /**
    * This gets the name of the name of the collection
    */
    public String getName()
    {
        return name;
    }


    /**
    * This method stores the changes you made in users collection
    */
    public ArrayList<String> getGameIds()
    {
        return new ArrayList<>(gameIds);
    }
}
