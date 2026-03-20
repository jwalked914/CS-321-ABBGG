
import java.util.ArrayList;

/**
 *  Represents a board game with its core attributes.
 *  Each Game object stores its unique ID, name, description, publication year,
 *  minimum and maximum player counts, and lists of categories and mechanics.
 */
public class Game {

    private final int id;
    private final String name;
    private final String desc;
    private final int pubYear;
    private final int minPlayer;
    private final int maxPlayer;
    private final int playingTime;
    private final ArrayList<String> bgCategories;
    private final ArrayList<String> bgMechanics;

    /**
     * Constructs a game object from various parameters
     *
     * @param id the game id
     * @param name the game name
     * @param desc the game description
     * @param pubYear the game publication year as a string
     * @param minPlayer required minimum player count as string; returns as an int
     * @param maxPlayer maximum player count; returns as an int
     * @param playingTime gameplay time
     * @param bgCategories list of game categories
     * @param bgMechanics list of game mechanics
     */
    Game(String id, String name, String desc, String pubYear, String minPlayer, String maxPlayer, String playingTime, ArrayList<String> bgCategories, ArrayList<String> bgMechanics)
    {
        this.id = stringToInt(id);
        this.name = name;
        this.desc = desc;
        this.pubYear = stringToInt(pubYear);
        this.minPlayer = stringToInt(minPlayer);
        this.maxPlayer = stringToInt(maxPlayer);
        this.playingTime = stringToInt(playingTime);
        this.bgCategories = new ArrayList<>(bgCategories);
        this.bgMechanics = new ArrayList<>(bgMechanics);

    }

    public int getID()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return desc;
    }

    public int getYearPublished()
    {
        return pubYear;
    }

    public int getMinPlayer()
    {
        return minPlayer;
    }

    public int getMaxPlayer()
    {
        return maxPlayer;
    }

    public ArrayList<String> getBgCategories()
    {
        return bgCategories;
    }

    public ArrayList<String> getBgMechanics()
    {
        return bgMechanics;
    }

    private int stringToInt(String num)
    {
        try
        {
            return Integer.parseInt(num);
        }
        catch (NumberFormatException e)
        {
            return 1;
        }
    }

    @Override
    public String toString()
    {
        return "GameID: " + id + '\n' +
                "Name: " + name + '\n' +
                "Year: " + pubYear + '\n' +
                "Description: " + desc + '\n' +
                "Minimum Players: " + minPlayer + '\n' +
                "Playing Time: " + playingTime + '\n' +
                "Maximum Players: " + maxPlayer + '\n' +
                "Categories: " + bgCategories + '\n' +
                "Mechanics: " + bgMechanics + '\n' +
                "-------------------------------\n";
    }
}
