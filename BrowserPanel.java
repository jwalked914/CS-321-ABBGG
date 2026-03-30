/**
 * Abstract base panel for browsing either games or collections.
 * Defines the overall layout algorithm: header, grid, and filter dropdown.
 *
 * Design Pattern: Template Method — this class defines the skeleton of the
 * browser UI in buildHeader() and buildGrid(). Subclasses implement the
 * primitive operations getTitle(), getSearchHint(), and buildCard() to
 * provide type-specific behavior without changing the overall structure.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BrowserPanel extends JPanel
{
    /**
     * Constructs the browser panel by initializing layout,
     * then building the header and grid in order.
     */
    private JPanel gridPanel;
    private JScrollPane scrollPane;

    public BrowserPanel()
    {
        this.setLayout(new BorderLayout());
        this.setBackground(GUIColors.MID);
        buildHeader();
        buildGrid();
    }

    //operations subclasses must define
    /**
     * Returns the title displayed in the panel header.
     * @return the panel title string
     */
    public abstract String getTitle();
    /**
     * Returns the placeholder text displayed in the search field.
     * @return the search hint string
     */
    public abstract String getSearchHint();
    /**
     * Builds and returns a single card panel for the given index.
     * @param index the position of the card in the grid
     * @return a JPanel representing one game or collection card
     */
    public abstract JPanel buildCard(int index);
    /**
     * Builds the header panel containing the title, search bar, and filter button.
     */
    public void buildHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(GUIColors.DARK);
        headerPanel.setPreferredSize(new Dimension(50, 80)); // taller for two rows
        headerPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        //top row: title on left hand side and search on right hand side
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(GUIColors.DARK);

        //build title
        JLabel titleLabel = new JLabel(getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(GUIColors.LIGHT);

        //build search field
        //NEED TO ADD DOCUMENT LISTNER FOR FILTERING ON SEARCH BAR WHEN CONNECTING DATA*****
        JTextField searchField = new JTextField(getSearchHint());
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBackground(GUIColors.CREAM);
        searchField.setForeground(GUIColors.DARK);
        searchField.setPreferredSize(new Dimension(220, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GUIColors.DARK, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));

        topRow.add(titleLabel, BorderLayout.WEST);
        topRow.add(searchField, BorderLayout.EAST);

        //bottom row: filter by button on the left hand side
        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomRow.setBackground(GUIColors.DARK);

        //build filter button
        JButton filterButton = new JButton("Filter");
        filterButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterButton.setBackground(GUIColors.CREAM);
        filterButton.setForeground(GUIColors.DARK);
        filterButton.setPreferredSize(new Dimension(110, 28));
        filterButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GUIColors.DARK, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));
        filterButton.setFocusPainted(false);

        JPopupMenu filterPanel = buildFilterPanel();
        filterButton.addActionListener(e ->
        {
            filterPanel.show(filterButton, 0, filterButton.getHeight());
        });

        bottomRow.add(filterButton);

        headerPanel.add(topRow, BorderLayout.NORTH);
        headerPanel.add(bottomRow, BorderLayout.SOUTH);

        this.add(headerPanel, BorderLayout.NORTH);
    }
    /**
     * Builds the scrollable grid of cards and adds it to the center of the panel.
     */
    public void buildGrid()
    {
        gridPanel=new JPanel(new GridLayout(0,4,10,10));
        gridPanel.setBackground(GUIColors.MID);
        gridPanel.setBorder(new EmptyBorder(10,10,10,10));

        //SAMPLE GAMES PUT GAME LOGIC EVENTUALLY******************
        //TESTING SCROLL WORKS
        int totalGames=20;
        for (int i=1;i<=totalGames;i++)
        {
            gridPanel.add(buildCard(i));
        }

        // wrapper stops the grid from stretching vertically
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        wrapper.setBackground(GUIColors.MID);
        wrapper.add(gridPanel);

        scrollPane=new JScrollPane(wrapper);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI()
        {
            @Override
            protected void configureScrollBarColors()
            {
                this.thumbColor = GUIColors.DARK;
                this.trackColor = GUIColors.LIGHT;
            }
        });
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollPane,BorderLayout.CENTER);
    }
    /**
     * Builds and returns the filter dropdown panel with placeholder filter options.
     * @return a JPopupMenu containing filter checkboxes and an apply button
     */
    public JPopupMenu buildFilterPanel()
    {
        JPopupMenu dropdown = new JPopupMenu();
        dropdown.setBackground(GUIColors.CREAM);

        JPanel content=new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(GUIColors.CREAM);
        content.setBorder(new EmptyBorder(10, 15, 10, 15));

        //PLACEHOLDER FILTERS ADD WHAT YOU NEED ************
        JLabel playerLabel = new JLabel("Player Count");
        playerLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        playerLabel.setForeground(GUIColors.DARK);

        JCheckBox two = new JCheckBox("2 Players");
        JCheckBox four = new JCheckBox("4 Players");
        JCheckBox six = new JCheckBox("6+ Players");

        JLabel timeLabel = new JLabel("Play Time");
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        timeLabel.setForeground(GUIColors.DARK);

        JCheckBox quick = new JCheckBox("Under 30 min");
        JCheckBox medium = new JCheckBox("30-60 min");
        JCheckBox long_ = new JCheckBox("60+ min");

        // style the checkboxes
        for (JCheckBox box : new JCheckBox[]{two, four, six, quick, medium, long_})
        {
            box.setBackground(GUIColors.CREAM);
            box.setForeground(GUIColors.DARK);
            box.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        }

        content.add(playerLabel);
        content.add(Box.createVerticalStrut(5));
        content.add(two);
        content.add(four);
        content.add(six);
        content.add(Box.createVerticalStrut(10));
        content.add(timeLabel);
        content.add(Box.createVerticalStrut(5));
        content.add(quick);
        content.add(medium);
        content.add(long_);

        //filter by apply button
        content.add(Box.createVerticalStrut(10));
        RoundedButton applyButton=new RoundedButton("Apply");
        applyButton.setPreferredSize(new Dimension(120, 35));
        applyButton.addActionListener(e ->
        {
            dropdown.setVisible(false);
        });

        content.add(applyButton);
        dropdown.add(content);

        return dropdown;
    }
}
