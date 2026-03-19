/*
Game Class needs for GameDB
Game Model (Game class)

-Must store all standard attributes: id, name, description, bgCategory, bgMechanic, publishYear, minPlayers, maxPlayers.

-publishYear, minPlayers, and maxPlayers must be stored as int.

-Fields should be private final with public getters.

-Include a clean toString() for debugging/printing.

-Constructor should accept Strings from XML and convert numeric fields to int safely.

-Handle missing or malformed numeric values with sensible defaults (e.g., 1 for players, 0 for year).

-Javadoc Comments
 */

public class Game {

    private final String id;
    private final String name;
    private final String desc;
    private final int pubYear;
    private final int minPlayer;
    private final int maxPlayer;

    Game(String id, String name, String desc, String pubYear, String minPlayer, String maxPlayer){

        this.id = id;
        this.name = name;
        this.desc = desc;
        this.pubYear = stringToInt(pubYear);
        this.minPlayer = stringToInt(minPlayer);
        this.maxPlayer = stringToInt(maxPlayer);

    }


    private int stringToInt(String num)
    {
        try
        {
            return Integer.parseInt(num);
        }
        catch (NumberFormatException e)
        {
            return 1;
        }
    }

    @Override
    public String toString(){
        return "GameID: " + id + '\n' + "Name: " + name + '\n' + "Year: " + pubYear + '\n' +
                "Description: " + desc + '\n' + "Minimum Players: " + minPlayer + '\n' + "Maximum Players: " + maxPlayer +
                '\n' + "-------------------------------";
    }
}
