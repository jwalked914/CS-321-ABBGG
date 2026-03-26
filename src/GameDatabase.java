import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Represents a database of board games that provides access to and
 * operations on a collection of {@link Game} objects.
 *
 * <p>The database acts as a container for games loaded from an external
 * source (such as an XML parser) and serves as the primary data source
 * for queries and user collections.</p>
 */
public class GameDatabase
{
    private final ArrayList<Game> games;
    private HashSet<String> allCategories;
    private HashSet<String> allMechanics;
    private HashMap<String, HashSet<Game>> wordMap;
    private HashMap<String, HashSet<Game>> categoryMap;
    private HashMap<String, HashSet<Game>> mechanicMap;

    /**
     * Constructs a game database of Game objects
     * @param gameDB parsed boardgame array list
     */
    public GameDatabase(ArrayList<Game> gameDB)
    {
        this.games = gameDB;
        buildCategoryAndMechanicSets();
        buildWordMap();
        buildCategoryMap();
        buildMechanicMap();
    }

    public void printAllGames()
    {
        for(Game g : games)
        {
            System.out.println(g);
        }
    }

    public ArrayList<Game> getAllGames()
    {
        return games;
    }

    /**
     *
     * @param id
     * @return
     */
    public Game getGameById(int id)
    {
        for (Game game : games)
        {
            if (game.getID() == id)
            {
                return game;
            }
        }
        return null;
    }

    /**
     * Returns all unique categories.
     *
     * @return list of categories
     */
    public ArrayList<String> getAllCategories()
    {
        return new ArrayList<>(allCategories);
    }

    /**
     * Returns all unique mechanics
     *
     * @return list of mechanics
     */
    public ArrayList<String> getAllMechanics()
    {
        return new ArrayList<>(allMechanics);
    }

    /**
     * Returns games that match user query
     *
     * @param Query user inputted string
     * @return list of games matching user query
     */
    public ArrayList<Game> searchByName(String Query)
    {
        HashSet<Game> results = new HashSet<>();
        String lowerQuery = Query.toLowerCase();

        if(wordMap.containsKey(lowerQuery)) // add any games with exact name matches
        {
            results.addAll(wordMap.get(lowerQuery));
        }

        for (String key: wordMap.keySet()) // handles substring matches
        {
            if(key.contains(lowerQuery) && !key.equals(lowerQuery)) // compares query to keys of wordMap
            {                                                       // but prevents double adding exact matches
                results.addAll(wordMap.get(key));
            }
        }

        return new ArrayList<>(results);
    }

    /**
     *
     * @param selectedCategories
     * @return
     */
    public ArrayList<Game> filterByCategory(HashSet<String> selectedCategories)
    {
        // empty selection
        if(selectedCategories == null || selectedCategories.isEmpty())
        {
            return new ArrayList<>(games);
        }

        Iterator<String> iter = selectedCategories.iterator();
        if(!iter.hasNext()) return new ArrayList<>();

        // iterate through first categories set
        HashSet<Game> intersection = new HashSet<>(categoryMap.getOrDefault(iter.next(), new HashSet<>()));

        // intersect with the rest
        while(iter.hasNext())
        {
            String cat = iter.next();
            HashSet<Game> catSet = categoryMap.getOrDefault(cat, new HashSet<>());
            intersection.retainAll(catSet);
        }
        System.out.println(intersection);

        return new ArrayList<>(intersection);
    }

    /**
     *
     * @param selectedMechanics
     * @return
     */
    public ArrayList<Game> filterByMechanic(HashSet<String> selectedMechanics)
    {
        // empty selection
        if(selectedMechanics == null || selectedMechanics.isEmpty())
        {
            return new ArrayList<>(games);
        }

        Iterator<String> iter = selectedMechanics.iterator();
        if(!iter.hasNext()) return new ArrayList<>();

        // iterate through first categories set
        HashSet<Game> intersection = new HashSet<>(mechanicMap.getOrDefault(iter.next(), new HashSet<>()));

        // intersect with the rest
        while(iter.hasNext())
        {
            String cat = iter.next();
            HashSet<Game> mechSet = mechanicMap.getOrDefault(cat, new HashSet<>());
            intersection.retainAll(mechSet);
        }
        System.out.println(intersection);

        return new ArrayList<>(intersection);
    }

    /**
     * Builds unique sets for categories and mechanics for filter drop down.
     *
     */
    private void buildCategoryAndMechanicSets()
    {
        allCategories = new HashSet<>();
        allMechanics = new HashSet<>();

        for(Game g: games)
        {
            allCategories.addAll(g.getBgCategories());
            allMechanics.addAll(g.getBgMechanics());
        }
    }

    /**
     *  Builds categories map for fast filtering
     */
    private void buildCategoryMap()
    {
        categoryMap = new HashMap<>();

        for(Game g: games)
        {
        ArrayList<String> categories = g.getBgCategories();
            for(String cat: categories)
            {
                HashSet<Game> set = categoryMap.get(cat);
                if(set == null)
                {
                    set = new HashSet<>();
                    categoryMap.put(cat,set);
                }
                set.add(g);
            }
        }
    }

    /**
     *  Builds mechanics map for fast filtering.
     */
    private void buildMechanicMap()
    {
        mechanicMap = new HashMap<>();

        for(Game g: games)
        {
            ArrayList<String> mechanics = g.getBgMechanics();
            for(String mech: mechanics)
            {
                HashSet<Game> set = mechanicMap.get(mech);
                if(set == null)
                {
                    set = new HashSet<>();
                    mechanicMap.put(mech,set);
                }
                set.add(g);
            }
        }
    }

    /**
     * Builds word maps for fast search
     */
    private void buildWordMap()
    {
        wordMap = new HashMap<>();

        for(Game g: games)
        {
            String[] words = g.getName().toLowerCase().split("\\s+");
            for(String word: words)
            {
                wordMap.computeIfAbsent(word, k-> new HashSet()).add(g);
            }
        }
    }
}
