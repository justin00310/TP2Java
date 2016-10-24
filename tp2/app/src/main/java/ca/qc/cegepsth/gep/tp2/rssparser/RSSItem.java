package ca.qc.cegepsth.gep.tp2.rssparser;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contient un élément de flux RSS
 * 
 * Stephane Denis
 * @version 2016-09-18
 */
public class RSSItem implements Serializable{

	String titre;
	String description;
	Date date;
    URL imageUrl;
    String auteur;
    URL url;
    String guid; // permet d'identifier l'épisode de façon théoriquement unique
    private List<RSSMedia> medias;

    void addMedia(String type, URL url){
        if(medias == null)
            medias = new ArrayList<RSSMedia>();

        medias.add(new RSSMedia(type, url));
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the image
     */
    public URL getImageUrl() {
        return imageUrl;
    }

    /**
     * @return the auteur
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * @return the URL
     */
    public URL getURL() {
        return url;
    }

    public String getGuid() {
        return guid;
    }

    public List<RSSMedia> getMedias() {
        return medias;
    }
}
