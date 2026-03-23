import java.util.ArrayList;
import java.util.List;

/**
 * Collection class allows users to store unlimited games
 * and create unlimited amount of collections
 */

public class Collection
{
    /** Is an Id for identifying a certain collection */
    private int id;

    /** This will display the collection's name */
    private String name ;

    /**
     * List of games in this collection
     */
    private List<String> games;

    /**
     *  Name and list of Collections made in the library
     */
    private List<Collection> subCollections;

    /**
     * Creates a new collection with a given ID and name.
     * @param id collection id
     * @param name display collection name
     */
    public Collection(int id,String name)
    {
        this.id = id;
        this.name = name;
        this.games = new ArrayList<>();
        this.subCollections = new ArrayList<>();
    }

    /**
     * Methods
     * addGame adds a game to the list
     * removeGame would remove the game from a list
     */

    public void addGame(String game)
    {
        games.add(game);
    }

    public void removeGame(String game)
    {
        games.remove(game);
    }

    /**
     * method to add a collection and remove a collection
     * @param collection adds a collection into a collection
     */

    public void addSubCollection(Collection collection)
    {
        subCollections.add(collection);
    }

    public void removeSubcollection(Collection collection)
    {
        subCollections.remove(collection);
    }

    /**
     *
     * @return
     * the id of Collection
     * name of Collection
     * games in the Collection
     * the nested Collection or SubCollection in the collection
     */
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public List<String> getGames()
    {
        return games;
    }

    public List<Collection> getSubCollections()
    {
        return subCollections;
    }

}
