import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CollectionBrowserPanel extends BrowserPanel
{
    private final User user;
    private ArrayList<UserCollection> filteredCollections;
    private CollectionCardListener cardListener;

    public CollectionBrowserPanel(User user)
    {
        super();
        this.user = user;
        filteredCollections = user.getCollections();
        updateTitle(getTitle());

        this.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e)
            {
                requestFocusInWindow();
            }
        });
    }

    @Override
    public String getTitle()
    {
        return "My Collections";
    }

    @Override
    public String getSearchHint()
    {
        return "Search Collections";
    }

    @Override
    public boolean showFilterButton()
    {
        return false;
    }

    @Override
    public JPopupMenu buildFilterPanel()
    {
        return new JPopupMenu();
    }

    @Override
    public int getCardCount()
    {
        return filteredCollections.size();
    }

    @Override
    public void onSearch(String query)
    {
        if(query.isEmpty())
        {
            filteredCollections = user.getCollections();
        }
        else
        {
            filteredCollections = new ArrayList<>();
            for(UserCollection collection : user.getCollections())
            {
                if(collection.getName().toLowerCase().contains(query.toLowerCase()))
                    filteredCollections.add(collection);
            }
        }
        refresh();
    }

    @Override
    public JPanel buildCard(int index)
    {
        RoundedPanel card = new RoundedPanel(10, GUIColors.CREAM);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(175,210));
        card.setMinimumSize(new Dimension(175,210));
        card.setPreferredSize(new Dimension(175,210));

        UserCollection collection = filteredCollections.get(index);

        JLabel imageLabel = new JLabel("📁", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(truncate(collection.getName()), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI",Font.BOLD,12));
        nameLabel.setForeground(GUIColors.DARK);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel countLabel = new JLabel(collection.getAllGames().size()+ " games", SwingConstants.CENTER);
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN,11));
        countLabel.setForeground(GUIColors.DARK);
        countLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e)
            {
                if (cardListener != null)
                {
                    cardListener.onCollectionSelected(collection);
                }
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e)
            {
                card.setBackground(GUIColors.LIGHT);
                card.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e)
            {
                card.setBackground(GUIColors.CREAM);
                card.repaint();
            }
        });

        card.add(Box.createVerticalStrut(10));
        card.add(imageLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(countLabel);
        card.add(Box.createVerticalStrut(5));

        return card;
    }

    public interface CollectionCardListener
    {
        void onCollectionSelected(UserCollection collection);
    }


    public void setCardListener(CollectionCardListener listener)
    {
        this.cardListener = listener;
    }

}
