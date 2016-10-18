package ca.qc.cegepsth.gep.tp2.registre;

import java.io.Serializable;
import java.util.HashSet;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

/**
 * Exemple trivial de registre des items RSS lus
 *
 * Created by Stéphane Denis on 2016-09-25.
 */

public class ItemsLus implements Serializable {
    private HashSet<String> guids;

    /**
     * Ajoute un item RSS aux items lus
     *
     * @param item à ajouter
     * @return vrai si l'item n'était pas déjà dans la liste
     */
    public boolean add(RSSItem item){
        if(guids==null){
            guids = new HashSet<String>();
        }
        return guids.add(item.getGuid());
    }

    /**
     * Retire un item RSS des items lus
     *
     * @param item à retirer
     * @return vrai si l'item était dans les items lus
     */
    public boolean remove(RSSItem item){
        if(guids==null){
            return false;
        }

        return guids.remove(item);
    }

    /**
     * Permet de savoir si un item a été lu
     *
     * @param item à rechercher
     * @return vrai si l'item est dans les items lus
     */
    public boolean estLu(RSSItem item){
        if(guids==null){
            return false;
        }
        return guids.contains(item);
    }
}
