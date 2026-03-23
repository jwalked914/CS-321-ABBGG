import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Parses a board game XML file and converts its contents into
 * {@link Game} objects.
 *
 * <p>This class is responsible only for reading and interpreting XML data.
 * It does not store or manage games after parsing. The resulting list
 * is intended to be passed to a {@code GameDatabase}.</p>
 */
public class FileScannerXML
{
    private final File xmlFile;

    /**
     * Constructs a FileScannerXML with the given XML file.
     *
     * @param xmlFile the XML file to parse
     */
    public FileScannerXML(File xmlFile)
    {
        this.xmlFile = xmlFile;
    }


    /**
     * Parses the XML file and returns a list of Game objects.
     *
     * @return an ArrayList of Game objects extracted from the XML file
     * @throws RuntimeException if there is an error reading or parsing the file
     */
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

                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element game = (Element) node;

                if (!"boardgame".equals(game.getAttribute("type")) && !"boardgameexpansion".equals(game.getAttribute("type")))
                    continue;

                games.add(parseGameElement(game));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML", e);
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
        String thumbnailURL = getTextContent(gameElement, "thumbnail", "N/A");
        String imageURL = getTextContent(gameElement, "image", "N/A");
        String desc = getTextContent(gameElement, "description", "Unknown");
        String pubYear = getAttributeValue(gameElement, "yearpublished", "N/A");
        String minPlayers = getAttributeValue(gameElement, "minplayers", "N/A");
        String maxPlayers = getAttributeValue(gameElement, "maxplayers", "N/A");
        String playingTime = getAttributeValue(gameElement, "playingTime", "N/A");
        ArrayList<String> categories = getTagList(gameElement,"link", "boardgamecategory");
        ArrayList<String> mechanics = getTagList(gameElement,"link", "boardgamemechanic");

        return new Game(id, name, thumbnailURL, imageURL, desc, pubYear, minPlayers, maxPlayers, playingTime, categories, mechanics);
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

    /**
     * Gets a tag list of a child node by tag and type.
     *
     * @param parent the parent XML node
     * @param tag a link of child node
     * @param filterType type filter for link
     * @return list of string attributes based on filter
     */
    private ArrayList<String> getTagList(Element parent, String tag, String filterType)
    {
        ArrayList<String> stringList = new ArrayList<>();

        NodeList nodeList = parent.getElementsByTagName(tag);

        for(int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);

            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element element = (Element) node;

            if (filterType.equals(element.getAttribute("type")))
            {
                stringList.add(element.getAttribute("value"));
            }
        }
        return stringList;
    }

}