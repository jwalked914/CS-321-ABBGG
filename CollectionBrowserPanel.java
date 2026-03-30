/**
 * A browser panel implementation for displaying a user's game collections.
 * Extends BrowserPanel and provides collection-specific implementations
 * of the Template Method primitive operations.
 *
 * Design Pattern: Template Method — implements the primitive operations
 * defined in BrowserPanel to display collection cards instead of games.
 */
import javax.swing.*;
import java.awt.*;

public class CollectionBrowserPanel extends BrowserPanel
{
    /**
     * Constructs the collection browser panel by invoking the parent layout algorithm.
     */
    public CollectionBrowserPanel()
    {
        super();
    }
    /**
     * Returns the title for the collections panel.
     * @return "My Collections"
     */
    public String getTitle()
    {
        return "My Collections";
    }
    /**
     * Returns the placeholder text for the collections search bar.
     * @return "Search Collections"
     */
    public String getSearchHint()
    {
        return "Search Collections";
    }
    /**
     * Builds and returns a single collection card for the given index.
     * @param index the position of the card in the grid
     * @return a RoundedPanel displaying the collection label
     */
    public JPanel buildCard(int index)
    {
        RoundedPanel card= new RoundedPanel(10, GUIColors.CREAM);
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(160, 160));

        JLabel nameLabel= new JLabel("Collection" + index, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        nameLabel.setForeground(GUIColors.DARK);

        card.add(nameLabel, BorderLayout.SOUTH);

        return card;
    }
}