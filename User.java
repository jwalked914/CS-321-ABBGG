import java.util.ArrayList;

/**
 * PLACEHOLDER User class for testing UserDatabase only.
 * Replace with the full implementation.
 */
public class User
{
    private String username;
    private String password;
    private boolean isAdmin;
    private ArrayList<UserCollection> collections; // simplified - real class uses ArrayList<Collection>
    private ArrayList<String> reviews;     // simplified - real class uses ArrayList<Review>

    public User(String username, String password, boolean isAdmin, ArrayList<UserCollection> collections)
    {
        this.username    = username;
        this.password    = password;
        this.isAdmin     = isAdmin;
        this.collections = collections;
        this.reviews     = new ArrayList<>();
    }

    // --- Getters ---
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    public boolean isAdmin()
    {
        return isAdmin;
    }
    public ArrayList<UserCollection> getCollections()
    {
        return collections;
    }
    public ArrayList<String> getReviews()
    {
        return reviews;
    }

    // --- Setters ---
    public void setPassword(String password)
    {
        this.password = password;
    }

    public void addCollection(UserCollection collection)
    {
        collections.add(collection);
    }

    // --- Utility ---
    @Override
    public String toString()
    {
        return "User{" +
                "username='" + username + '\'' +
                ", isAdmin=" + isAdmin +
                ", collections=" + collections +
                ", reviews=" + reviews +
                '}';
    }
}