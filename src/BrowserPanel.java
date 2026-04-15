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
import java.awt.event.MouseEvent;

public abstract class BrowserPanel extends JPanel
{

    private javax.swing.Timer resizeTimer;
    private JPanel gridPanel;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    public abstract void onSearch(String query);
    public abstract JPopupMenu buildFilterPanel();


    /**
     * Constructs the browser panel by initializing layout,
     * then building the header and grid in order.
     */
    public BrowserPanel()
    {
        this.setLayout(new BorderLayout());
        this.setBackground(GUIColors.MID);
        buildHeader();

        // Prevents screen updating for every resize adjustment
        this.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e)
            {
                if (resizeTimer != null) resizeTimer.stop();
                resizeTimer = new javax.swing.Timer(200, evt ->
                {
                    refresh();
                    resizeTimer.stop();
                });
                resizeTimer.start();
            }
        });

    }
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
    protected void buildHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(GUIColors.DARK);
        headerPanel.setPreferredSize(new Dimension(50, 80)); // taller for two rows
        headerPanel.setBorder(new EmptyBorder(8, 10, 8, 10));

        //top row: title on left hand side and search on right hand side
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(GUIColors.DARK);

        titleLabel = new JLabel(getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD,22));
        titleLabel.setForeground(GUIColors.LIGHT);


        //build search field
        JTextField searchField = new JTextField(getSearchHint());
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBackground(GUIColors.CREAM);
        searchField.setForeground(GUIColors.DARK);
        searchField.setPreferredSize(new Dimension(220, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GUIColors.DARK, 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));

        searchField.addFocusListener(new java.awt.event.FocusAdapter()
        {
            @Override
            public void focusGained(java.awt.event.FocusEvent e)
            {
                if (searchField.getText().equals(getSearchHint()))
                {
                    searchField.setText("");
                    searchField.setForeground(GUIColors.DARK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e)
            {
                if(searchField.getText().isEmpty())
                {
                    searchField.setText(getSearchHint());
                    searchField.setForeground(Color.BLACK);
                }
            }
        });

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener()
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { handleSearch(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { handleSearch(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { handleSearch(); }

            private void handleSearch()
            {
                String text = searchField.getText();
                if(!text.equals(getSearchHint()))
                {
                    onSearch(text);
                }
                else
                {
                    onSearch("");
                }
            }
        });

        topRow.add(titleLabel, BorderLayout.NORTH);
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

        filterButton.addActionListener(e ->
        {
            buildFilterPanel().show(filterButton, 0 , filterButton.getHeight());
        });

        if(showFilterButton())
        {
            bottomRow.add(filterButton);
        }


        headerPanel.add(topRow, BorderLayout.NORTH);
        headerPanel.add(bottomRow, BorderLayout.SOUTH);

        this.add(headerPanel, BorderLayout.NORTH);

        headerPanel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                headerPanel.requestFocusInWindow();
            }
        });
    }
    /**
     * Builds the scrollable grid of cards and adds it to the center of the panel.
     */
    protected void buildGrid()
    {
        gridPanel = new JPanel(new GridLayout(0,getColumnCount(),10,10));
        gridPanel.setBackground(GUIColors.MID);
        gridPanel.setBorder(new EmptyBorder(10,10,10,10));

        int totalGames = getCardCount();

        for (int i=0; i < totalGames; i++)
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

    protected String truncate(String text)
    {
        if(text.length() > 18)
        {
            return text.substring(0,18) + "...";
        }
        return text;
    }

    /**
     * Returns the total number of cards for grid to display.
     *
     * @return total card count
     */
    public abstract int getCardCount();

    /**
     * Refreshes grid whenever screen is resized and updates data to reflect changes.
     */
    public void refresh()
    {
        if(scrollPane != null) this.remove(scrollPane);
        buildGrid();
        revalidate();
        repaint();
    }

    public void updateTitle(String title)
    {
        titleLabel.setText(title);
    }

    /**
     * Returns the number of columns based on panel width.
     *
     * @return column count, minimum of 1
     */
    private int getColumnCount()
    {
        int width = getWidth();
        if (width == 0) return 4;
        return Math.max(1,width / 180);
    }

    protected boolean showFilterButton()
    {
        return true;
    }


}
