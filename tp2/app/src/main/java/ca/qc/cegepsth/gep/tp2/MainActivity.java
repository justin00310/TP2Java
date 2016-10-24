package ca.qc.cegepsth.gep.tp2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSFeed;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

/**
 * @Auteur : Justin Leblanc et Chris
 * @Date : 16 octobre 2016
 * @Description : Activité qui gère les contenus RSS de différents liens
 * ouverts par l'utilisateur ayant du contenu RSS.
 */
public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<RSSItem> lstItems;
    URL url;
    RSSFeed feed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstItems = new ArrayList<RSSItem>();
        lv = (ListView) findViewById(R.id.lstItems);

        /**Va chercher l'intent déclenché par
        l'ouvertur d'un lien RSS **/
        Intent intent = getIntent();
        //Définit l'url du flux RSS à convertir
        Uri uri = intent.getData();

        if (uri != null) {
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        Button buttonAdd = (Button) findViewById(R.id.btnAjouter);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
