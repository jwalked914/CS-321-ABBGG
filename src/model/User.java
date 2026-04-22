package model;

import java.util.ArrayList;

/**
 *  Represents a user and its core attributes.
 *  Each model.User object stores a username, password, isAdmin boolean, collection ArrayList
 *  and a review ArrayList.
 */
public class User
{
    private String username;
    private String password;
    private String profilePicturePath;
    public Boolean isAdmin;
    private final ArrayList<UserCollection> collections;
    // private final data.GameDatabase gameDB;
    public ArrayList<Review> reviews;

    /**
     * Constructs a user object from various parameters
     *
     * @param username the username
     * @param password the password
     * @param isAdmin if the user is an admin
     * @param collections an array that contains the user's collections
     */

    public User(String username, String password, Boolean isAdmin, ArrayList<UserCollection> collections,
                ArrayList<Review> reviews)
    {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.collections = collections;
        this.reviews = reviews;
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
        collections.add(new UserCollection(name));
    }

    /**
     * add a collection
     */
    public void addCollection(UserCollection collection)
    {
        collections.add(collection);
    }

    /**
     * Delete a collection
     */
    public void deleteCollection(String name)
    {
        collections.removeIf(collection -> collection.getName().equals(name));
    }

    /**
     * Creates a model.Review
     */
    public void createReview(int gameRef, String user, int rating, String reviewText)
    {
        reviews.add(new Review(gameRef, user, rating, reviewText));
    }

    /**
     * Returns isAdmin
     */
    public boolean getIsAdmin()
    {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin)
    {
        this.isAdmin=isAdmin;
    }

    public String getProfilePicturePath()
    {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath)
    {
        this.profilePicturePath=profilePicturePath;
    }
}
