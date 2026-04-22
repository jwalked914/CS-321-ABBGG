import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainFrame extends JFrame
{
    private final GameDatabase gameLibrary;
    private final UserDatabase userDatabase;
    private final User currentUser;
    private final String reviewsXMLPath;

    //panel to switch screen
    private JPanel currentScreen;

    //all the main panels that are reused
    private final GameBrowserPanel searchPanel; //use to generate game browser panel for search and collection search
    private final CollectionBrowserPanel libraryBrowserPanel;
    private final GameDescriptionPanel gameDescriptionPanel;
    private final UserSettingsPanel userSettingsPanel;
    private final ManageUsersPanel manageUsersPanel;

    public MainFrame(GameDatabase gameLibrary, UserDatabase userDatabase,User currentUser, String reviewsXMLPath)
    {
        this.gameLibrary=gameLibrary;
        this.userDatabase=userDatabase;
        this.currentUser=currentUser;
        this.reviewsXMLPath=reviewsXMLPath;

        setTitle("ABG Board Games");
        setSize(1200,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //create the current screen (first to display will be the main screen (search))
        currentScreen=new JPanel(new BorderLayout());
        currentScreen.setBackground(GUIColors.CREAM);

        //build the persisting panels
        searchPanel=new GameBrowserPanel(gameLibrary,currentUser);
        searchPanel.setCardListener(this::showGameDescription); //navigate to gameDescription

        libraryBrowserPanel= new CollectionBrowserPanel(currentUser);
        libraryBrowserPanel.setCardListener(this::navigateCollection); //navigate to collection sreen

        gameDescriptionPanel=new GameDescriptionPanel(currentUser, reviewsXMLPath);
        this.userSettingsPanel= new UserSettingsPanel(currentUser);
        this.manageUsersPanel= new ManageUsersPanel(userDatabase);

        //add side panel
        add(new SidePanel(this, currentUser), BorderLayout.WEST);
        add(currentScreen, BorderLayout.CENTER);

        navigateHome();
    }

    //Side Panel Navigations
    public void navigateHome()
    {
        swapContent(searchPanel);
    }

    public void navigateLibrary()
    {
        swapContent(libraryBrowserPanel);
    }

    public void navigateSettings()
    {
        swapContent(userSettingsPanel);
    }

    public void navigateManageUsers() { swapContent(manageUsersPanel); }

    public void logout()
    {
        dispose();
        //bring users back to the login page
        new LoginScreen(userDatabase,gameLibrary,reviewsXMLPath);
    }

    //Navigate through other panels
    public void showGameDescription(Game game)
    {
        //load game's reviews in
        FileScannerXML reader = new FileScannerXML(new File(reviewsXMLPath), gameLibrary);
        ArrayList<Review> reviews = reader.getReviewForGame(reviewsXMLPath,game);
        gameDescriptionPanel.setDisplayedGame(game, reviews);
        swapContent(gameDescriptionPanel);
    }
    //NEED THIS HERE
    //create a updated panel per collection
    public void navigateCollection(UserCollection collection)
    {
        GameBrowserPanel collectionView = new GameBrowserPanel(collection,currentUser);
        collectionView.setCardListener(this::showGameDescription);
        swapContent(collectionView);
    }

    private void swapContent(JPanel panel)
    {
        currentScreen.removeAll();
        currentScreen.add(panel, BorderLayout.CENTER);
        currentScreen.revalidate();
        currentScreen.repaint();
    }
}
