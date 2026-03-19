/**
 *
 *
 */

public class Game {

    private String id;
    private String name;
    private String desc;
    private String pubYear;
    private String minPlayer;
    private String maxPlayer;

    Game(String id, String name, String desc, String pubYear, String minPlayer, String maxPlayer){

        this.id = id;
        this.name = name;
        this.desc = desc;
        this.pubYear = pubYear;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;

    }



    @Override
    public String toString(){
        return "GameID: " + id + '\n' + "Name: " + name + '\n' + "Year: " + pubYear + '\n' +
                "Description: " + desc + '\n' + "Minimum Players: " + minPlayer + '\n' + "Maximum Players: " + maxPlayer +
                '\n' + "-------------------------------";
    }
}
