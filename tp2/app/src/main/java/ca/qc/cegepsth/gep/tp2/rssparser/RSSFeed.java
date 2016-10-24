package ca.qc.cegepsth.gep.tp2.rssparser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Récupère et extrait le contenu d'un flux RSS à partir d'une adresse web.
 * Le traitement est asynchrone
 *
 * @author Stephane Denis
 * @version 2016-09-18
 */
public class RSSFeed extends Observable implements Runnable, Serializable {

    private URL url;
    private URL imageUrl;
    private String titre;

    private ArrayList<RSSItem> items;
    public ArrayList<RSSItem> getItems() {
        return items;
    }



    private transient Exception e;      // Exception qui sera passé au client

    public RSSFeed(URL url) {
        this.url = url;
        items = new ArrayList<RSSItem>();
        new Thread(this).start();
    }

    @Override
    public synchronized void run() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            // Récupère le titre du flux
            NodeList titleNode = doc.getElementsByTagName("title");
            if (titleNode != null) {
                titre = titleNode.item(0).getTextContent();
            }

            // Récupère l'url de l'image du flux
            NodeList imageNode = doc.getElementsByTagName("image");
            if (imageNode != null && imageNode.getLength() > 0) {
                NodeList imageNodeUrl = imageNode.item(0).getChildNodes();
                if (imageNodeUrl != null) {
                    for (int i = 0; i < imageNodeUrl.getLength(); i++) {
                        if ("url".equals(imageNodeUrl.item(i).getNodeName())) {
                            imageUrl = new URL(imageNodeUrl.item(i).getTextContent());
                        }
                    }
                }

            }

            // Get all <item> tags.
            NodeList list = doc.getElementsByTagName("item");
            int length = list.getLength();

            // For all the items in the feed
            for (int i = 0; i < length; i++) {
                // Create a new node of the first item
                Node currentNode = list.item(i);
                // Create a new RSS item
                RSSItem item = new RSSItem();

                // Get the child nodes of the first item
                NodeList nodeChild = currentNode.getChildNodes();
                // Get size of the child list
                int cLength = nodeChild.getLength();

                // For all the children of a node
                for (int j = 1; j < cLength; j = j + 2) {
                    // Get the name of the child
                    String nodeName = nodeChild.item(j).getNodeName(), nodeString = null;
                    // If there is at least one child element
                    if (nodeChild.item(j).getFirstChild() != null) {
                        // Set the string to be the value of the node
                        nodeString = nodeChild.item(j).getFirstChild().getNodeValue();
                    }
                    // If the string isn't null
                    if (null != nodeName)
                        switch (nodeName) {
                            case "title":
                                item.titre = nodeString;
                                break;
                            case "content:encoded":
                            case "description":
                                item.description = nodeString;
                                break;
                            case "pubDate":

                                try {
                                    SimpleDateFormat format =
                                            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
                                    item.date = format.parse(nodeString);
                                }
                                catch(ParseException pe) {
                                    item.date = null;
                                }
                                break;
                            case "author":
                            case "dc:creator":
                                item.auteur = nodeString;
                                break;
                            case "link":
                                item.url = new URL(nodeString);
                                break;
                            case "itunes:image":
                                item.imageUrl= new URL(nodeChild.item(j).getAttributes().getNamedItem("href").getNodeValue());
                                break;
                            case "thumbnail":
                                item.imageUrl= new URL(nodeString);
                                break;
                            case "guid":
                                item.guid = nodeString;
                                break;
                            case "enclosure":
                            case "media:content":
                                String urlString = nodeChild.item(j).getAttributes().getNamedItem("url").getNodeValue();
                                URL url = new URL(urlString);
                                String type = nodeChild.item(j).getAttributes().getNamedItem("type").getNodeValue();
                                if("image/jpeg".equals(type)){
                                    item.imageUrl= url;
                                }
                                else item.addMedia(type, url);
                                break;
                            default:
                                break;
                        }

                }
                items.add(item);
            }
        } catch (Exception e) { // throws SAXException, IOException, ParserConfigurationException
            this.e = e;
        }
        setChanged();

        // Annonce la fin du traitement
        synchronized (this) {
            notifyObservers(e);
        }
    }

    /**
     * Permet de connaitre l'origine du flux RSS
     *
     * @return l'url du flux
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Image associée au flux
     *
     * @return URL de l'image si existe
     */
    public URL getImageUrl() {
        return imageUrl;
    }

    public String getTitre() {
        return titre;
    }

    /**
     * Permet de connaitre l'état de la récupération du flux
     *
     * @return exception l'erreur, ou null si succès
     */
    public Exception getError() {
        return e;
    }
}

