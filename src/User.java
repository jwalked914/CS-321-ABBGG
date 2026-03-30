import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *  Represents a user and its core attributes.
 *  Each User object stores a username, password, isAdmin boolean, collection ArrayList
 *  and a review ArrayList.
 */
public class User
{
    private String username;
    private String password;
    private final Boolean isAdmin;
    private final ArrayList<UserCollection> collections;
    // private final ArrayList<review> reviews;

    /**
     * Constructs a user object from various parameters
     *
     * @param username the username
     * @param password the password
     * @param isAdmin if the user is an admin
     * @param collections an array that contains the user's collections
     * @param review and array that contains the user's reviews
     */

    User(String username, String password, Boolean isAdmin, ArrayList<UserCollection> collections)
         //ArrayList<review> reviews)
    {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.collections = collections;
        // this.reviews = new ArrayList<>();
    }

    /**
     * sets the username of the user when they are logging in for the first time
     */

    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * returns the user's username
     *
     * @return the username
     */

    public String getUsername()
    {
        return username;
    }

    /**
     * returns the user's password
     *
     * @return the password
     */

    public String getPassword()
    {
        return password;
    }

    /**
     * sets the password of the user when they are logging in for the first time
     */

    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * returns if the user is an admin
     *
     * @return isAdmin
     */

    public Boolean getIsAdmin()
    {
        return isAdmin;
    }

    /**
     * returns Collection
     *
     * @return collection
     */
    public ArrayList<UserCollection> getCollections()
    {
        return collections;
    }

    /**
     * Creates a collection
     */
    public void createCollection(String name)
    {
        collections.add(new UserCollection(name, gameDB));
    }

    /**
     * Delete a collection
     */
    public void deleteCollection(String name)
    {
        
    }


}
