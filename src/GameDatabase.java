import java.util.ArrayList;

public class GameDatabase
{
    private ArrayList<Game> games;

    public GameDatabase(ArrayList<Game> gameDB)
    {
        this.games = gameDB;
    }

    public void printAllGames()
    {
        for(Game g : games){
            System.out.println(g);
        }
    }

    public ArrayList<Game> getGames()
    {
        return games;
    }

}
