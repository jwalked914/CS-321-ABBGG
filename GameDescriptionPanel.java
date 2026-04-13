import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * GameDescriptionPanel displays detailed information about a selected board game.
 * It shows the game image, game info (name, year, players, description, categories, mechanics),
 * a list of user reviews, and a form to submit a new review.
 * Users can also add the game to one of their collections via a dialog.
 */
public class GameDescriptionPanel extends JPanel
{
    /** Displays the game's cover image. */
    private JLabel imageLabel;
    /** Panel holding all game info labels on the right side. */
    private JPanel infoPanel;
    /** Displays the game name. */
    private JLabel nameLabel;
    /** Displays the player count range. */
    private JLabel playerLabel;
    /** Displays the year the game was published. */
    private JLabel yearLabel;
    /** Displays the game description. */
    private JTextArea description;
    /** Displays the game's categories. */
    private JLabel categoriesLabel;
    /** Displays the game's mechanics. */
    private JLabel mechanicsLabel;
    /** Displays the list of reviews for the current game. */
    private final JList<Review> reviewList;
    /** Button to switch to the review form card. */
    private RoundedButton addReviewButton;
    /** Button to cancel and return to the reviews list card. */
    private RoundedButton cancelReviewButton;
    /** Button to submit the review form and save to XML. */
    private RoundedButton saveReviewButton;
    /** Button to open the add-to-collection dialog. */
    private RoundedButton addGameButton;
    /** Stores the 5 rating radio buttons so they can be looped over on save. */
    private JRadioButton [] ratingButtons;
/** The game currently being displayed. Updated via setDisplayedGame. */
    private Game currentGame;
    /** File path to the reviews XML file. */
    private String reviewsXMLPath;
    /** Placeholder for GameDatabase ( used for file scanner)*/
    private GameDatabase holderGameDB=null;
    /** The logged-in user interacting with this panel. */
    private final User currentUser;

    /**
     * Constructs a GameDescriptionPanel for the given user.
     * Builds the full layout including image area, game info scroll panel,
     * reviews card, and review form card with a CardLayout switcher.
     *
     * @param currentUser     the logged-in user
     * @param reviewsXMLPath  file path to the reviews XML file
     */
    public GameDescriptionPanel(User currentUser, String reviewsXMLPath)
    {
        this.reviewsXMLPath = reviewsXMLPath;
        this.currentUser = currentUser;
        this.ratingButtons= new JRadioButton[5];

        // components for Review Form
        JLabel userField= new JLabel(currentUser.getUsername());
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userField.setForeground(GUIColors.DARK);
        userField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel textLabel= new JLabel("Review");
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textLabel.setForeground(GUIColors.LIGHT);
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea textField= new JTextArea();
        textField.setBackground(GUIColors.CREAM);
        textField.setForeground(GUIColors.DARK);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setRows(4); //set starting height for text field, will expand as user types if needed

        // ratingRow holds the radio buttons visually
        // ratingButtons[] holds references for reading selection
        JLabel ratingLabel= new JLabel("Rating (1-5)");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ratingLabel.setForeground(GUIColors.LIGHT);
        ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel ratingRow = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
        ratingRow.setBackground(GUIColors.DARK);
        ratingRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        ButtonGroup ratingGroup = new ButtonGroup();
        for (int i = 1; i <= 5; i++) {
            JRadioButton btn = new JRadioButton(String.valueOf(i));
            btn.setForeground(GUIColors.LIGHT);
            btn.setOpaque(false);
            ratingGroup.add(btn);
            ratingButtons[i-1] = btn; // store in array
            ratingRow.add(btn);       // add to panel
        }
        setLayout(new BorderLayout(20,0));
        setBackground(GUIColors.MID);

        //wrap each box into JPanel for lighter color background
        //username displayed in a lighter box
        JPanel userLabelBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        userLabelBox.setBackground(GUIColors.CREAM);
        userLabelBox.add(userField);// lighter than DARK
        userLabelBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        userLabelBox.setMaximumSize(new Dimension(userLabelBox.getPreferredSize().width, userLabelBox.getPreferredSize().height));

        // wrap the text area directly, expands with content
        JPanel reviewBox = new JPanel(new BorderLayout());
        reviewBox.setBackground(GUIColors.CREAM);
        reviewBox.add(textField, BorderLayout.CENTER); // text where ers will add review
        reviewBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        //MAIN LAYOUT

        JPanel mainPanel= new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JScrollPane mainScrollPane= new JScrollPane(mainPanel);
        //set left side of screen (image and reviews)
        JPanel leftPanel = new JPanel(new BorderLayout(0,8));
        leftPanel.setBackground(GUIColors.MID);
        //splits into left ( image + review ) and right (info scrolling)
        JPanel topPanel = new JPanel(new BorderLayout(10,0));
        topPanel.setBackground(GUIColors.MID);

        //card layout for review (switches between the list and adding review form)
        JPanel reviewsCard=new JPanel(new BorderLayout());
        JPanel formCard= new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(GUIColors.DARK);
        formCard.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel formFields = new JPanel();
        formFields.setLayout(new BoxLayout(formFields, BoxLayout.Y_AXIS));
        formFields.setBackground(GUIColors.DARK);

        //panel for reviews
        CardLayout bottomLayout= new CardLayout();
        JPanel bottomPanel = new JPanel(bottomLayout);
        bottomPanel.setPreferredSize(new Dimension(0,350));

        //create image label
        imageLabel=new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //review list
        reviewList=new JList<>();
        reviewList.setBackground(GUIColors.DARK);
        reviewList.setForeground(GUIColors.LIGHT);
        reviewList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        reviewList.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane reviewScroll = new JScrollPane(reviewList);
        reviewScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        reviewScroll.setBorder(null);

        //BUTTONS
        addReviewButton = new RoundedButton("Add Review", 50, 45);
        addReviewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        //set action listener for add review
        addReviewButton.addActionListener(e->{
            bottomLayout.show(bottomPanel, "form");
        });

        cancelReviewButton = new RoundedButton("Cancel",35,20);
        cancelReviewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        //set action listener to return from review panel
        cancelReviewButton.addActionListener(e->{
            bottomLayout.show(bottomPanel,"Reviews");
        });

        saveReviewButton = new RoundedButton("Save", 35,20);
        saveReviewButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        //set action listener to return from review panel
        saveReviewButton.addActionListener(e->{
            String reviewText= textField.getText().trim();
            //determine which button is selected
            int rating = -1;
            for (JRadioButton btn : ratingButtons) {
                if (btn.isSelected()) {
                    rating = Integer.parseInt(btn.getText());
                    break;
                }
            }
            if (reviewText.isEmpty() || rating == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a review and select a rating.",
                        "Incomplete Review",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Review newReview = new Review(currentGame.getID(), currentUser.getUsername(), rating, reviewText);
            newReview.writeReviewsXML(reviewsXMLPath);
            //refresh list to show new review by rereadign XML
            FileScannerXML reader = new FileScannerXML(new File(reviewsXMLPath), holderGameDB);
            ArrayList<Review> updated = reader.getReviewForGame(reviewsXMLPath, currentGame);
            reviewList.setListData(updated.toArray(new Review[0]));

            bottomLayout.show(bottomPanel,"Reviews");
        });

        //GAME INFO PANEL
        //set right hand side of screen (game info)
        infoPanel = new JPanel();
        infoPanel.setBackground(GUIColors.DARK);
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        //wrap infoPanel in a scroll to fit description
        JPanel infoWrapper = new JPanel(new BorderLayout());
        infoWrapper.setBackground(GUIColors.DARK);
        infoWrapper.add(infoPanel, BorderLayout.NORTH); //put north to take preferred height inside scroll

        JScrollPane infoScroll = new JScrollPane(infoWrapper);
        infoScroll.getViewport().setBackground(GUIColors.DARK);
        infoScroll.setPreferredSize(new Dimension(450, 0));
        infoScroll.setBorder(null);
        infoScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        infoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        nameLabel = new JLabel("Game");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD,22));
        nameLabel.setForeground(GUIColors.LIGHT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        yearLabel = new JLabel("Published: ?");
        yearLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        yearLabel.setForeground(GUIColors.LIGHT);
        yearLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        playerLabel = new JLabel("Players: ? - ?");
        playerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        playerLabel.setForeground(GUIColors.LIGHT);
        playerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //description box
        description= new JTextArea("Description");
        description.setFont(new Font("Segoe UI", Font.PLAIN,13));
        description.setForeground(GUIColors.LIGHT);
        description.setBackground(GUIColors.DARK);
        //auto line size of description area depending on text
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setAlignmentX(Component.LEFT_ALIGNMENT);

        //set categories and mechanics on page
        categoriesLabel = new JLabel("Categories");
        categoriesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        categoriesLabel.setForeground(GUIColors.LIGHT);
        categoriesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mechanicsLabel = new JLabel("Mechanics");
        mechanicsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        mechanicsLabel.setForeground(GUIColors.LIGHT);
        mechanicsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //button to add game to collection+ dialog
        addGameButton = new RoundedButton("Add Game", 200, 45);
        addGameButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addGameButton.addActionListener(e->
        {
            ArrayList<UserCollection> collections = currentUser.getCollections();
            JDialog addDialog = new JDialog();
            addDialog.setTitle("Add to collection");
            addDialog.setModal(true); // block user input to all other windows til dialog closed
            addDialog.setSize(300,400);
            addDialog.setLocationRelativeTo(this);
            addDialog.setUndecorated(true);

            JPanel content = new JPanel(new BorderLayout(0,10));
            content.setBackground(GUIColors.DARK);
            content.setBorder(new EmptyBorder(20,20,20,20));

            //title
            JLabel title = new JLabel("Add to Collection");
            title.setFont(new Font("Segoe UI", Font.BOLD, 16));
            title.setForeground(GUIColors.LIGHT);

            //list of collections
            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
            listPanel.setBackground(GUIColors.DARK);

            for (UserCollection collection : collections)
            {
                RoundedButton button = new RoundedButton(collection.getName(),50,35);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

                // if game in collection disable button
                if (collection.containsGame(currentGame))
                {
                    button.setEnabled(false); // grays it out and blocks clicking
                }
                else
                {
                    button.addActionListener(ev ->
                    {
                        collection.addGame(currentGame);
                        addDialog.dispose();
                        JOptionPane.showMessageDialog(this, currentGame.getName() + " added to " + collection.getName());
                    });
                }
                listPanel.add(button);
                listPanel.add(Box.createVerticalStrut(6));
            }

            JScrollPane listScroll = new JScrollPane(listPanel);
            listScroll.setBorder(null);
            listScroll.getViewport().setBackground(GUIColors.DARK);

            // bottom buttons
            JPanel bottomRPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            bottomRPanel.setBackground(GUIColors.DARK);

            //create new name for collection
            RoundedButton newCollectionButton = new RoundedButton("+ Add", 90, 60);
            newCollectionButton.addActionListener(ev ->
            {
                addDialog.dispose();
                //dialog to name new collection
                JDialog nameDialog = new JDialog();
                nameDialog.setModal(true);
                nameDialog.setUndecorated(true);
                nameDialog.setSize(280, 160);
                nameDialog.setLocationRelativeTo(this);

                JPanel nameContent = new JPanel();
                nameContent.setLayout(new BoxLayout(nameContent, BoxLayout.Y_AXIS));
                nameContent.setBackground(GUIColors.DARK);
                nameContent.setBorder(new EmptyBorder(20, 20, 20, 20));

                JLabel nameLabel = new JLabel("Collection Name");
                nameLabel.setForeground(GUIColors.LIGHT);
                nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JTextField nameField = new JTextField();
                nameField.setBackground(GUIColors.MID);
                nameField.setForeground(GUIColors.LIGHT);
                nameField.setCaretColor(GUIColors.LIGHT);
                nameField.setBorder(new EmptyBorder(5, 8, 5, 8));
                nameField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
                nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

                JPanel nameButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
                nameButtons.setBackground(GUIColors.DARK);

                RoundedButton confirmButton = new RoundedButton("Create", 100, 30);
                RoundedButton cancelButton = new RoundedButton("Cancel", 100, 30);

                confirmButton.addActionListener(ev2 ->
                {
                    String newName = nameField.getText().trim();
                    if (!newName.isEmpty()) {
                        currentUser.createCollection(newName);
                        // get new created collection
                        UserCollection newCol = currentUser.getCollections().get(currentUser.getCollections().size() - 1);
                        newCol.addGame(currentGame);
                        JOptionPane.showMessageDialog(this, currentGame.getName() + " added to " + newName);
                    }
                    nameDialog.dispose();
                });
                cancelButton.addActionListener(ev2 -> nameDialog.dispose());
                nameButtons.add(cancelButton);
                nameButtons.add(confirmButton);

                nameContent.add(nameLabel);
                nameContent.add(Box.createVerticalStrut(8));
                nameContent.add(nameField);
                nameContent.add(Box.createVerticalStrut(10));
                nameContent.add(nameButtons);

                nameDialog.add(nameContent);
                nameDialog.setVisible(true);
            });
            RoundedButton cancelButton = new RoundedButton("Cancel", 90, 35);
            cancelButton.addActionListener(ev -> addDialog.dispose());

            bottomRPanel.add(newCollectionButton);
            bottomRPanel.add(cancelButton);

            content.add(title, BorderLayout.NORTH);
            content.add(listScroll, BorderLayout.CENTER);
            content.add(bottomRPanel, BorderLayout.SOUTH);

            addDialog.add(content);
            addDialog.setVisible(true);

        });

        // put every piece together
        bottomPanel.add(reviewsCard, "Reviews");
        bottomPanel.add(formCard, "form");

        leftPanel.add(imageLabel, BorderLayout.CENTER);
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);

        topPanel.add(leftPanel, BorderLayout.CENTER);
        topPanel.add(infoScroll, BorderLayout.EAST);

        reviewsCard.add(reviewScroll, BorderLayout.CENTER);
        reviewsCard.add(addReviewButton, BorderLayout.NORTH);

        mainPanel.add(topPanel);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(playerLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(yearLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(description);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(categoriesLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(mechanicsLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(addGameButton);

        formFields.add(userLabelBox);
        formFields.add(Box.createVerticalStrut(4));
        formFields.add(textLabel);
        formFields.add(reviewBox);
        formFields.add(Box.createVerticalStrut(4));
        formFields.add(ratingLabel);
        formFields.add(ratingRow);
        formFields.add(Box.createVerticalStrut(4));
        formFields.add(cancelReviewButton);
        formFields.add(Box.createVerticalStrut(4));
        formFields.add(saveReviewButton);

        formCard.add(formFields);

        add(mainScrollPane);

    }

    /**
     * Updates the panel to display the given game and its reviews.
     * Loads the game image asynchronously via SwingWorker to avoid blocking the UI thread.
     *
     * @param game    the game to display
     * @param reviews the list of reviews for the game
     */
    public void setDisplayedGame(Game game, ArrayList<Review> reviews)
    {
        this.currentGame=game;

        nameLabel.setText(game.getName());
        yearLabel.setText("Published: " + game.getYearPublished());
        playerLabel.setText("Players: " + game.getMinPlayer() + " - " + game.getMaxPlayer());
        description.setText(game.getDescription());
        categoriesLabel.setText("Categories: " + String.join(", ", game.getBgCategories()));
        mechanicsLabel.setText("Mechanics: " + String.join(", ", game.getBgMechanics()));
        reviewList.setListData(reviews.toArray(new Review[0])); // needed to populate reviews in JList
        // image loading from GameBrowserPanel
        imageLabel.setText("Loading...");
        imageLabel.setIcon(null);
        // load image on a background thread so the UI stays responsive
        new SwingWorker<ImageIcon, Void>()
        {
            protected ImageIcon doInBackground() throws Exception
            {
                URL url = new URL(game.getImageURL());
                BufferedImage img = ImageIO.read(url);
                Image scaled = img.getScaledInstance(600, 600, Image.SCALE_DEFAULT);
                return new ImageIcon(scaled);
            }
            protected void done()
            {
                try
                {
                    imageLabel.setText("");
                    imageLabel.setIcon(get());
                }
                catch (Exception e)
                {
                    imageLabel.setText("No Image");
                }
            }
        }.execute();
    }
}


