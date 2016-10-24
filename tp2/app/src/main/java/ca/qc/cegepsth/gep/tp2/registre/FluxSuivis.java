package ca.qc.cegepsth.gep.tp2.registre;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Registre des flux RSS suivis par l'application
 *
 * Singleton sérialisable
 * Seuls les URLs des flux et les GUID des items lus sont sérialisés
 *
 * Created by Stéphane Denis on 2016-09-25.
 */

public class FluxSuivis extends Observable implements Serializable, Observer {
    private static FluxSuivis instance; // Singleton
    private transient ArrayList<URL> liste;

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
     * Permet de fixer une instance obtenue par désérialisation
     * @param i nouvelle instance
     */
    public static void setInstance(FluxSuivis i){
        instance=i;
        if(i.suivi != null) for (ItemsLus il : i.suivi.values()) {
            il.addObserver(i);
        }
    }

    /**
     * Ajoute un flux au registre de suivi
     *
     * @param url
     */
    public void add(URL url){
        if(liste!=null) {
            liste.add(url);
        }
        if(suivi == null){
            suivi = new HashMap<>();

        }

        if (!suivi.containsKey(url)) {
            ItemsLus il = new ItemsLus();
            il.addObserver(this);
            suivi.put(url, il);
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
        if(liste!=null) {
            liste.remove(url);
        }
        if(suivi != null){
            suivi.remove(url);
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
     * @return liste d'URL suivis
     */
    public ArrayList<URL> getListe() {

       if(liste==null) {
           liste = new ArrayList<>();

           // support de désérialisation
           if (suivi != null) for (URL url : suivi.keySet()
                   ) {
               liste.add(url);
           }
       }
        return liste;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
