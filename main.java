import java.util.ArrayList;
import java.io.File;

public class main {
    public static void main (String []args) {
        File gamesFile = new File("bgg90Games.xml");
        FileScannerXML scanner = new FileScannerXML(gamesFile,null);

        ArrayList<Game> gameList = scanner.parseGamesFromXML();
        GameDatabase gameDB = new GameDatabase(gameList);
        UserDatabase userDB=new UserDatabase(gameDB);

        //gameDB.printAllGames();

        // test addUser
        userDB.addUser("testUser", "pass123", false);
        System.out.println("Added testUser");

        // test isUsernameTaken
        System.out.println("Is testUser taken? " + userDB.isUsernameTaken("testUser")); // true
        System.out.println("Is fakeUser taken? " + userDB.isUsernameTaken("fakeUser")); // false

        // test getUserByName
        User found = userDB.getUserByName("testUser");
        System.out.println("Found user: " + found.getUsername());

        // test validateCredentials
        User valid = userDB.validateCredentials("testUser", "pass123");
        System.out.println("Valid login: " + (valid != null)); // true

        User invalid = userDB.validateCredentials("testUser", "wrongpass");
        System.out.println("Invalid login: " + (invalid != null)); // false

        UserCollection col = new UserCollection("Favorites");
        Game game=gameDB.getGameById(374173);
        if (game!=null)
        {
            col.addGame(game);
        }

        //add collection to user and save
        userDB.addCollectionToUser("testUser",col);
        // verify
        User user = userDB.getUserByName("testUser");
        for (UserCollection c : user.getCollections())
        {
            System.out.println("Collection: " + c.getName());
            for (Game g : c.getAllGames())
            {
                System.out.println("  Game: " + g.getName());
            }
        }
//        // test deleteUser
//        userDB.deleteUser("testUser");
//        System.out.println("Deleted testUser");
//        System.out.println("Is testUser still there? " + userDB.isUsernameTaken("testUser")); // false
    }
}