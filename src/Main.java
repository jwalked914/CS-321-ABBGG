import data.FileScannerXML;
import data.GameDatabase;
import data.UserDatabase;
import model.Game;
import model.User;
import viewAndControl.LoginScreen;

import javax.swing.*;

void main()
{
    SwingUtilities.invokeLater(() ->
    {
        File gamesFile = new File("resources/bgg90Games.xml");
        File usersFile = new File("resources/usersInfo.xml");
        String reviewsPath = "resources/reviews.xml";

        //Load in model.Game Database
        FileScannerXML gameScanner = new FileScannerXML(gamesFile, null);
        ArrayList<Game> gameList = gameScanner.parseGamesFromXML();
        GameDatabase gameDatabase = new GameDatabase(gameList);

        //Load in model.User Database
        FileScannerXML userScanner = new FileScannerXML(usersFile, gameDatabase);
        ArrayList<User> userList = userScanner.parseUsersFromXML();
        UserDatabase userDatabase = new UserDatabase(usersFile, gameDatabase);

        new LoginScreen(userDatabase, gameDatabase, reviewsPath);
    });
}
