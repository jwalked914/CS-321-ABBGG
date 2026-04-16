/**
 * A browser panel implementation for displaying the full game database.
 * Extends BrowserPanel and provides game-specific implementations
 * of the Template Method primitive operations.
 *
 * Design Pattern: Template Method — implements the primitive operations
 * defined in BrowserPanel to display game cards instead of collections.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;

public class GameBrowserPanel extends BrowserPanel
{
    private GameCardListener cardListener;
    private final GameDatabase gameDB;
    private final User user;
    private ArrayList<GameCard> gameCards = new ArrayList<>();
    private ArrayList<Game> filteredGames;
    private String currentQuery = "";
    private HashSet<String> selectedCategories = new HashSet<>();
    private HashSet<String> selectedMechanics = new HashSet<>();

    /**
     * Constructs the game browser panel by invoking the parent layout algorithm.
     */
    public GameBrowserPanel(GameDatabase gameDB, User user)
    {
        super();
        this.gameDB = gameDB;
        this.user = user;
        filteredGames = gameDB.getAllGames();
        buildGameCards();
        updateTitle(getTitle());
        updateFilterButton();
        refresh();

        this.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e)
            {
                requestFocusInWindow();
            }
        });
    }
    /**
     * Returns the title for the games panel.
     *
     * @return "ABBGG Games Database" for Master Database or collection name if User Collection
     */
    public String getTitle()
    {
        if(gameDB instanceof UserCollection)
        {
            return ((UserCollection) gameDB).getName();
        }

        return "ABBGG Game Database";
    }
    /**
     * Returns the placeholder text for the games search bar.
     * @return "Search for Game"
     */
    public String getSearchHint()
    {
        return "Search for Game";
    }
    /**
     * Builds and returns a single game card for the given index.
     * @param index the position of the card in the grid
     * @return a RoundedPanel displaying the game label
     */
    public JPanel buildCard(int index)
    {
        Game game = filteredGames.get(index);
        for (GameCard card : gameCards)
        {
            if (card.getGame() == game)
                return card;
        }
        return null;
    }
    /**
     *  Builds and returns filter dropdown with the categories and mechanics
     *  checkboxes and the apply and clear buttons.
     *
     * @return  a JPopUpMenu containing the filter controls
     */
    @Override
    public JPopupMenu buildFilterPanel() {
        JPopupMenu dropdown = new JPopupMenu();
        dropdown.setBackground(GUIColors.CREAM);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(GUIColors.CREAM);
        wrapper.setBorder(new EmptyBorder(10, 15, 10, 15));

        JPanel catPanel = new JPanel();
        catPanel.setLayout(new BoxLayout(catPanel, BoxLayout.Y_AXIS));
        catPanel.setBackground(GUIColors.CREAM);

        ArrayList<String> categories = gameDB.getAllCategories();
        Collections.sort(categories);
        HashMap<String, JCheckBox> categoryCheckboxMap = new HashMap<>();

        for (String cat : categories) {
            JCheckBox box = new JCheckBox(cat);
            box.setBackground(GUIColors.CREAM);
            box.setForeground(GUIColors.DARK);
            box.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            box.setSelected(selectedCategories.contains(cat));
            categoryCheckboxMap.put(cat, box);
            catPanel.add(box);
        }

        JScrollPane catScroll = new JScrollPane(catPanel);
        catScroll.setPreferredSize(new Dimension(180, 150));
        catScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GUIColors.DARK, 1),
                "Categories",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                GUIColors.DARK
        ));
        catScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel mechPanel = new JPanel();
        mechPanel.setLayout(new BoxLayout(mechPanel, BoxLayout.Y_AXIS));
        mechPanel.setBackground(GUIColors.CREAM);

        ArrayList<String> mechanics = gameDB.getAllMechanics();
        Collections.sort(mechanics);
        HashMap<String, JCheckBox> mechanicCheckboxMap = new HashMap<>();

        for (String mech : mechanics) {
            JCheckBox box = new JCheckBox(mech);
            box.setBackground(GUIColors.CREAM);
            box.setForeground(GUIColors.DARK);
            box.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            box.setSelected(selectedMechanics.contains(mech));
            mechanicCheckboxMap.put(mech, box);
            mechPanel.add(box);
        }

        JScrollPane mechScroll = new JScrollPane(mechPanel);
        mechScroll.setPreferredSize(new Dimension(180, 150));
        mechScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GUIColors.DARK, 1),
                "Mechanics",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                GUIColors.DARK
        ));
        mechScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        catScroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GUIColors.DARK;
                this.trackColor = GUIColors.LIGHT;
            }
        });

        mechScroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GUIColors.DARK;
                this.trackColor = GUIColors.LIGHT;
            }
        });

        mechScroll.getHorizontalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = GUIColors.DARK;
                this.trackColor = GUIColors.LIGHT;
            }
        });

        JPanel scrollStack = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        scrollStack.setBackground(GUIColors.CREAM);
        scrollStack.add(catScroll);
        scrollStack.add(mechScroll);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonRow.setBackground(GUIColors.CREAM);

        RoundedButton clearButton = new RoundedButton("Clear", 100, 50);
        clearButton.setPreferredSize(new Dimension(120, 35));
        clearButton.addActionListener(e ->
        {
            selectedCategories.clear();
            selectedMechanics.clear();
            categoryCheckboxMap.values().forEach(box -> box.setSelected(false));
            mechanicCheckboxMap.values().forEach(box -> box.setSelected(false));
            applyFilters();
            dropdown.setVisible(false);
        });

        RoundedButton applyButton = new RoundedButton("Apply", 100, 50);
        applyButton.setPreferredSize(new Dimension(120, 35));
        applyButton.addActionListener(e ->
        {
            selectedCategories.clear();
            for (String cat : categoryCheckboxMap.keySet()) {
                if (categoryCheckboxMap.get(cat).isSelected())
                    selectedCategories.add(cat);
            }

            selectedMechanics.clear();
            for (String mech : mechanicCheckboxMap.keySet()) {
                if (mechanicCheckboxMap.get(mech).isSelected())
                    selectedMechanics.add(mech);
            }

            applyFilters();
            dropdown.setVisible(false);
        });

        buttonRow.add(clearButton);
        buttonRow.add(applyButton);

        wrapper.add(scrollStack, BorderLayout.CENTER);
        wrapper.add(buttonRow, BorderLayout.SOUTH);

        dropdown.add(wrapper);
        return dropdown;
    }
    /**
     *  Applies search query, category, and mechanic filters to rebuild filtered games list.
     */
    private void applyFilters()
    {
        ArrayList<Game> result = gameDB.filterByCategoryAndMechanic(selectedCategories,selectedMechanics);
        if (!currentQuery.isEmpty())
        {
            result.retainAll(gameDB.searchByName(currentQuery));
        }

        filteredGames = result;
        refresh();
    }
    public interface GameCardListener
    {
        void onGameSelected(Game game);
    }
    /**
     * Registers a listener to handle game card selection.
     *
     * @param listener the GameCardListener to notify when a card is clicked
     */
    public void setCardListener(GameCardListener listener)
    {
        this.cardListener = listener;
    }
    /**
     *  Updates the current search query and applies all active filters.
     *
     * @param query the current search string, empty if clear
     */
    @Override
    public void onSearch(String query)
    {
        currentQuery = query;
        applyFilters();
    }
    /**
     * Returns the number of games in the filtered list.
     *
     * @return the filtered game count
     */
    public int getCardCount()
    {
        return filteredGames.size() ;
    }

    /**
     * Builds and caches all GameCard instances from the current filtered games list.
     * Each card is initialized with its remove and click callbacks.
     */
    private void buildGameCards()
    {
        gameCards.clear();
        for (int i = 0; i < filteredGames.size(); i++)
        {
            Game game = filteredGames.get(i);
            gameCards.add(new GameCard(
                    game,
                    gameDB instanceof UserCollection,
                    user,
                    gameDB,
                    () -> {
                        filteredGames.remove(game);
                        gameCards.removeIf(c -> c.getGame() == game);
                        refresh();
                    },
                    () -> { if (cardListener != null) cardListener.onGameSelected(game); }
            ));
        }
    }

}