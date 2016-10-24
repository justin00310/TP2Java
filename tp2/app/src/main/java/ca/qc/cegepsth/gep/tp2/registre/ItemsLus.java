package ca.qc.cegepsth.gep.tp2.registre;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

/**
 * Exemple trivial de registre des items RSS lus
 *
 * Created by Stéphane Denis on 2016-09-25.
 */

public class ItemsLus extends Observable implements Serializable {
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

        boolean exist=guids.contains(item.getGuid());
        if(!exist){
            guids.add(item.getGuid());
            setChanged();
            notifyObservers();
        }

        return exist;
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
        boolean exist=guids.contains(item.getGuid());
        if(exist){
        guids.remove(item);
            setChanged();
            notifyObservers();
        }
        return exist;
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
        return guids.contains(item.getGuid());
    }
}
