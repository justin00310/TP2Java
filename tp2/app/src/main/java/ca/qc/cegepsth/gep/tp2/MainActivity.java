package ca.qc.cegepsth.gep.tp2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.qc.cegepsth.gep.tp2.registre.FluxSuivis;
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
    ArrayList<RSSItem> lstItems;
    ArrayAdapter<String> adapter;
    RSSFeed feed;
    URL url;
    FluxSuivis fluxs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //aller chercher l'instance des fluxs suivis
        fluxs = FluxSuivis.getInstance();
        //observer les fluxs suivis
        fluxs.addObserver(this);

        lstItems = new ArrayList<RSSItem>();
        lv = (ListView) findViewById(R.id.lstItems);
        final EditText editUrl = (EditText) findViewById(R.id.edtUrl);

        //Va chercher l'intent déclenchée par l'ouvertur d'un lien RSS
        Intent intent = getIntent();
        Uri uri = intent.getData();
        //Si ouvert a partir d'une page web, url apparait en haut
        url = null;
        if (uri != null) {
            try {
                url = new URL(uri.toString());
                editUrl.setText(url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //afficher une erreur?
            }
        }

        Button buttonAdd = (Button) findViewById(R.id.btnAjouter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //ajouter l'url 'a la liste de feed
                fluxs.add(getUrl(editUrl.getText().toString()));
                editUrl.setText("http://");
            }
        });

        //Instanciation et exécution pour la conversion RSS
        feed = new RSSFeed(url);

        //adapter = new ArrayAdapter<String>(MainActivity.this,
                //android.R.layout.simple_list_item_1, itemList);
        //lv.setAdapter(adapter);
    }

    @Override
    public void update(Observable b, Object o){
        refreshList();
    }
    private void refreshList(){
        ArrayList<URL> urls = fluxs.getListe();
    }

    //Retourne l'url à partir de l'uri
    private URL getUrl(Uri uri){
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
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
