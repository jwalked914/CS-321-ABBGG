/**
 * A browser panel implementation for displaying the full game database.
 * Extends BrowserPanel and provides game-specific implementations
 * of the Template Method primitive operations.
 *
 * Design Pattern: Template Method — implements the primitive operations
 * defined in BrowserPanel to display game cards instead of collections.
 */
import javax.swing.*;
import java.awt.*;

public class GameBrowserPanel extends BrowserPanel
{
    /**
     * Constructs the game browser panel by invoking the parent layout algorithm.
     */
    public GameBrowserPanel()
    {
        super();
    }
    /**
     * Returns the title for the games panel.
     * @return "ABBG Games"
     */
    public String getTitle()
    {
        return "ABBG Games";
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
        RoundedPanel card= new RoundedPanel(10, GUIColors.CREAM);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 160));

        JLabel nameLabel= new JLabel("Game " + index, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(GUIColors.DARK);

        card.add(nameLabel, BorderLayout.SOUTH);

        return card;
    }
}