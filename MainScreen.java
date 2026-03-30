/**
 * The main application window for the ABBG Board Games application.
 * Displays two side-by-side browser panels: one for browsing all games
 * and one for browsing the logged-in user's collections.
*
 */
import javax.swing.*;

public class MainScreen extends JFrame
{
    /**
     * Entry point for standalone GUI testing.
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        new MainScreen();
    }
    /**
     * Constructs the main screen and initializes the layout.
     */
    public MainScreen()
    {
        initLayout();
    }
    /**
     * Initializes the main layout by creating and placing the game browser
     * and collection browser panels side by side in a split pane.
     */
    public void initLayout()
    {
        BrowserPanel gamesBrowser = new GameBrowserPanel();
        BrowserPanel libraryBrowser=new CollectionBrowserPanel();

        JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gamesBrowser, libraryBrowser);
        splitPane.setBorder(null);
        splitPane.setDividerLocation(725);
        splitPane.setDividerSize(4);

        this.add(splitPane);
        this.setSize(1450,900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
