import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;



public class FileScannerXML
{
    private final File xmlFile;

    /**
     *
     * @param xmlFile
     */
    public FileScannerXML(File xmlFile)
    {
        this.xmlFile = xmlFile;
    }

    public ArrayList<Game> parseGamesFromXML()
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

    /**
     *  Parses a single XML element representing a game and constructs {link @Game}
     *  object.
     *
     * @param gameElement an XML parent node
     * @return a {link @Game} object with attributes populated by the XML
     */
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

    /**
     * Gets the "value" of a child node by tag name.
     *
     * @param parent the parent XML node
     * @param tag the child element's tag name
     * @param defaultValue default return value if tag value is missing
     * @return the attribute value, or defaultValue if missing
     */
    private String getAttributeValue(Element parent, String tag, String defaultValue)
    {

        Node node = parent.getElementsByTagName(tag).item(0);

        if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
        {
            return ((Element) node).getAttribute("value");
        }

        return defaultValue;
    }

    /**
     * Gets the text content of a child node by tag name.
     *
     * @param parent the parent XML node
     * @param tag the child element's text content
     * @param defaultValue default return value if text content is missing
     * @return the child element's textContent, or defaultValue if missing
     */
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