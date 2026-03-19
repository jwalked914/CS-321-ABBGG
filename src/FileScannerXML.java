import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;



public class FileScannerXML
{

    private File xmlFile;

    public FileScannerXML(File xmlFile)
    {
        this.xmlFile = xmlFile;
    }

    public ArrayList<Game> parseXMLGame()
    {

        ArrayList<Game> games = new ArrayList<>();
        try
        {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList gameList = doc.getElementsByTagName("item");

            for (int i = 0; i < gameList.getLength(); i++) {
                Node node = gameList.item(i);

                if (node.getNodeType() != Node.ELEMENT_NODE) continue;

                Element game = (Element) node;

                if (!"boardgame".equals(game.getAttribute("type")) && !"boardgameexpansion".equals(game.getAttribute("type")))
                    continue;

                games.add(parseGameElement(game));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return games;
    }

    private Game parseGameElement(Element gameElement)
    {

        String id = gameElement.getAttribute("id");
        String name = getAttributeValue(gameElement, "name", "N/A");
        String desc = getTextContent(gameElement, "description", "Unknown");
        String pubYear = getAttributeValue(gameElement, "yearpublished", "N/A");
        String minPlayers = getAttributeValue(gameElement, "minplayers", "N/A");
        String maxPlayers = getAttributeValue(gameElement, "maxplayers", "N/A");

        return new Game(id, name, desc, pubYear, minPlayers, maxPlayers);
    }


    private String getAttributeValue(Element parent, String tag, String defaultValue)
    {

        Node node = parent.getElementsByTagName(tag).item(0);

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
        {
            return ((Element) node).getAttribute("value");
        }

        return defaultValue;
    }

    private String getTextContent(Element parent, String tag, String defaultValue)
    {

        Node node = parent.getElementsByTagName(tag).item(0);

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
        {
            return ((Element) node).getTextContent();
        }

        return defaultValue;
    }

}