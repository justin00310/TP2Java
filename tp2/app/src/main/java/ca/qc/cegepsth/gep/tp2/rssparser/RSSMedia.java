package ca.qc.cegepsth.gep.tp2.rssparser;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by Stéphane Denis on 2016-09-25.
 *
 * Contient les données d'items multimédias de flux RSS
 *
 */

public class RSSMedia implements Serializable{
    private String type;
    private URL url;

    public RSSMedia(String type, URL url){
        this.type= type;
        this.url=url;
    }

    public String getType() {
        return type;
    }

    public URL getUrl() {
        return url;
    }
}
