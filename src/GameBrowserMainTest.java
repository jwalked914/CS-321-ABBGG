import javax.swing.*;

void main() {
    File file = new File("bgg90Games.xml");
    FileScannerXML scanner = new FileScannerXML(file, null);

    ArrayList<Game> masterdb = scanner.parseGamesFromXML();
    GameDatabase db = new GameDatabase(masterdb);
    UserCollection collection = new UserCollection("MY BOX OF GOODS");
    for (int i = 0; i < 37; i++)
    {
        collection.addGame(masterdb.get(i));
    }

    SwingUtilities.invokeLater(() ->
    {
        JFrame frame = new JFrame("ABBGG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);

        GameBrowserPanel panel = new GameBrowserPanel(db, new User("james", "pass123", false, null));
        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocusInWindow();
    });

}
