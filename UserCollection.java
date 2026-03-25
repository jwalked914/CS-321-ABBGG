/**
 * PLACEHOLDER UserCollection class for testing UserDatabase only.
 * Replace with the full implementation.
 */

import java.util.ArrayList;
public class UserCollection extends GameDatabase
{
    private String name;
    private ArrayList<String> gameIds;

    public UserCollection(String name) {
        super(new ArrayList<>());
        this.name = name;
    }

    public void addGame(Game game) {
        getAllGames().add(game);
    }

    public String getName() {
        return name;
    }

    public void removeGame(Game game) {
        getAllGames().remove(game);
    }

    public boolean containsGame(Game game) {
        return getAllGames().contains(game);
    }

}
