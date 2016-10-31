package ca.qc.cegepsth.gep.tp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.qc.cegepsth.gep.tp2.registre.FluxSuivis;
import ca.qc.cegepsth.gep.tp2.registre.ItemsLus;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSFeed;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

/**
 * @Auteur : Justin Leblanc et Chris
 * @Date : 16 octobre 2016
 * @Description : Activité qui gère les contenus RSS de différents liens
 * ouverts par l'utilisateur ayant du contenu RSS.
 */
public class MainActivity extends AppCompatActivity implements Observer {

    ListView lv;
    FluxSuivis fluxs;
    ArrayList<RSSFeed> feeds;
    FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation
        feeds = new ArrayList<RSSFeed>();
        adapter = new FeedAdapter(this, R.layout.feed_list_list_item, feeds);
        fluxs = FluxSuivis.getInstance();

        //vérifier si ouvert à partir d'un lien valide
        openedFromLink(getIntent());

        //aller chercher les ids
        lv = (ListView) findViewById(R.id.lstItems);
        final EditText editUrl = (EditText) findViewById(R.id.edtUrl);
        Button buttonAdd = (Button) findViewById(R.id.btnAjouter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addFlux(getUrl(editUrl.getText().toString()));
                Toast.makeText(getApplicationContext(), editUrl.getText().toString() + " a été ajouté.", Toast.LENGTH_LONG).show();
                editUrl.setText("http://");
            }
        });
    }

    @Override
    protected  void onResume() {
        super.onResume();
        try {
            FileInputStream fis = getApplicationContext().openFileInput("FluxSuivis");
            ObjectInputStream is = new ObjectInputStream(fis);
            if(is != null)
                fluxs = (FluxSuivis) is.readObject();
            is.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        createList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput("FluxSuivis", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(fluxs);
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //créer la liste de feeds à partir des urls suivis
    private void createList(){
        for(URL u : fluxs.getListe()){
            RSSFeed f = new RSSFeed(u);
            f.addObserver(this);
            feeds.add(f);
        }
        lv.setAdapter(adapter);
    }
    //mettre 'a jour le ui
    private void refresh(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    //Méthode appelée quand un des feeds termine son exécution
    @Override
    public void update(Observable b, Object o){
        refresh();
    }
    //vérifie si ouvert à partir d'un lien
    private void openedFromLink(Intent i){
        EditText editUrl = (EditText)findViewById(R.id.edtUrl);
        //Va chercher l'intent déclenchée par l'ouverture d'un lien RSS
        Uri uri = i.getData();
        //Si ouvert a partir d'une page web, url apparait en haut
        URL url = null;
        if (uri != null) {
            try {
                url = new URL(uri.toString());
                editUrl.setText(url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
    //méthode pour ajouter des fluxs et créer le feed associé
    private void addFlux(URL u){
        if(!(fluxs.getListe().contains(u))){
            fluxs.add(u);
            RSSFeed f = new RSSFeed(u);
            f.addObserver(this);
            feeds.add(f);
        }
        else{
            Toast.makeText(getApplicationContext(), "Vous suivez déjà ce flux.", Toast.LENGTH_LONG).show();
        }
    }

    //Retourne l'url à partir d'une string
    private URL getUrl(String s){
        URL url = null;
        try {
            url = new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
