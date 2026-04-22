package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Review
{
    private final int gameId;
    private final String username;
    private final int rating;
    private final String text;


    public Review(int gameRef, String user, int rating, String reviewText)
    {
      gameId = gameRef;
      username = user;
      this.rating = rating;
      text=reviewText;

    }

    public int getGameId()
    {
        return gameId;
    }

    public String getUsername()
    {
        return username;
    }

    public int getRating()
    {
        if (rating < 1 || rating > 10)
        {
            return 0;
        }

        return rating;
    }

    public String getText()
    {
        return text;
    }

    @Override
    public String toString()
    {
        return username + " (" + rating + "/5): " + text;
    }
    private void saveDocument(Document doc, String reviewXMLPath) throws Exception
    {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(reviewXMLPath));
        transformer.transform(source, result);
    }

    public void writeReviewsXML(String reviewXMLPath)
    {
        File reviewsFile = new File(reviewXMLPath);

        if (reviewsFile.exists())
        {
            try {
                DocumentBuilderFactory reviewFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder reviewBuilder = reviewFactory.newDocumentBuilder();
                Document reviewDoc = reviewBuilder.parse(reviewsFile);
                reviewDoc.getDocumentElement().normalize();

                String idEntry = String.valueOf(getGameId());

                NodeList reviewList = reviewDoc.getElementsByTagName("gameReview");

                boolean found = false;
                for (int i = 0; i < reviewList.getLength(); i++)
                {
                    Node reviewNode = reviewList.item(i);
                    Element reviewElement = (Element) reviewNode;
                    if (idEntry.equals(reviewElement.getAttribute("gameId")))
                    {
                        // check if user already reviewed this game
                        NodeList existingUserReviews = reviewElement.getElementsByTagName("userReview");
                        for (int j = 0; j < existingUserReviews.getLength(); j++)
                        {
                            Element existing = (Element) existingUserReviews.item(j);
                            if (existing.getAttribute("username").equals(getUsername()))
                            {
                                // user already reviewed — update instead of adding new
                                existing.setAttribute("rating", String.valueOf(getRating()));
                                existing.setAttribute("text", getText());
                                found = true;
                                saveDocument(reviewDoc, reviewXMLPath);
                                return;
                            }
                        }
                        Element newUserElement = reviewDoc.createElement("userReview");
                        newUserElement.setAttribute("username", getUsername());
                        newUserElement.setAttribute("rating", String.valueOf(getRating()));
                        newUserElement.setAttribute("text", getText());
                        reviewElement.appendChild(newUserElement);
                        found=true;
                    }
                }
                if(!found)
                {
                    Element root=reviewDoc.getDocumentElement();
                    Element newReview = reviewDoc.createElement("gameReview");
                    newReview.setAttribute("gameId", String.valueOf(getGameId()));
                    root.appendChild(newReview);
                    Element newUserElement = reviewDoc.createElement("userReview");
                    newUserElement.setAttribute("username", getUsername());
                    newUserElement.setAttribute("rating", String.valueOf(getRating()));
                    newUserElement.setAttribute("text", getText());
                    newReview.appendChild(newUserElement);
                }
                saveDocument(reviewDoc, reviewXMLPath);
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else
        {
            try
            {
                DocumentBuilderFactory reviewFileFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder reviewFileBuilder = reviewFileFactory.newDocumentBuilder();
                Document reviewDoc = reviewFileBuilder.newDocument();

                Element root = reviewDoc.createElement("reviews");
                reviewDoc.appendChild(root);

                Element reviewElement = reviewDoc.createElement("gameReview");
                reviewElement.setAttribute("gameId", String.valueOf(getGameId()));
                root.appendChild(reviewElement);
                //Nested usernames
                Element userElement = reviewDoc.createElement("userReview");
                userElement.setAttribute("username", getUsername());
                userElement.setAttribute("rating", String.valueOf(getRating()));
                userElement.setAttribute("text", getText());
                //Nest each user reviews under games
                reviewElement.appendChild(userElement);

                saveDocument(reviewDoc, reviewXMLPath);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Failed to write reviews XML");
            }
        }
    }
}
