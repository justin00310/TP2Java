package ca.qc.cegepsth.gep.tp2.rssparser;

import java.io.Serializable;

/**
 * Created by Stéphane Denis on 2016-09-25.
 *
 * Contient les données d'items multimédias de flux RSS
 *
 */

public class RSSMedia implements Serializable{
    private String type;
    private String url;

    public RSSMedia(String type, String url){
        this.type= type;
        this.url=url;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
