package ca.qc.cegepsth.gep.tp2.registre;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Set;

/**
 * Registre des flux RSS suivis par l'application
 *
 * Singleton sérialisable
 * Seuls les URLs des flux et les GUID des items lus sont sérialisés
 *
 * Created by Stéphane Denis on 2016-09-25.
 */
public class FluxSuivis extends Observable implements Serializable {
    private static FluxSuivis instance; // Singleton

    private transient ArrayList<URL> liste;    // Pour faciliter le TP :-)

    private HashMap<URL,ItemsLus> suivi; // Registre des GUID des items lus par flux

    private FluxSuivis(){
        // constructeur privé, Singleton
    }

    /**
     * Accès au singleton
     *
     * @return l'instance de FluxSuivis
     */
    public static FluxSuivis getInstance(){
        if(instance==null){
            instance = new FluxSuivis();
        }
        return instance;
    }

    /**
     * Ajoute un flux au registre de suivi
     *
     * @param url
     */
    public void add(URL url){
        if(suivi == null){
            suivi = new HashMap<>();
            liste = new ArrayList<>();
        }

        if (!suivi.containsKey(url)) {
            suivi.put(url, new ItemsLus());
            liste.add(url);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Enlève le flux du registre de suivi
     *
     * @param url
     */
    public void remove(URL url){
        if(suivi != null){
            suivi.remove(url);
            liste.remove(url);
            setChanged();
            notifyObservers();
        }
    }

    /**
     *
     * @return nombre d'items suivis
     */
    public int getCount(){
        return suivi.size();
    }

    /**
     * Permet de récupérer l'ensembles des adresses de flux suivis
     * @return urls des flux
     */
    public Set<URL> getAllFeeds(){
        return suivi.keySet();
    }

    public ItemsLus getItemsLus(URL flux){
        if(suivi != null){
            return suivi.get(flux);
        }
        return null;
    }

    /**
     * Utilitaire pour le TP2 :-)
     * Pour simplifier l'utlisation avec un ArrayAdapter<URL>
     *
     * Comme les RSSFeed se chargent de façon asynchrone
     * il est plus simple de prendre en charge leur instanciation
     * à même les items de la liste.
     *
     * La sérialisation n'est pas prise en charge pour l'instant
     * Pour ce faire il faudrait regénérer un ArrayList à partir du keySet
     *
     * @return liste d'URL suivis
     */
    public ArrayList<URL> getListe() {
        return liste;
    }

}
