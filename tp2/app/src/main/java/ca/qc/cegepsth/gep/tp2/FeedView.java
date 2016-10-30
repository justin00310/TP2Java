package ca.qc.cegepsth.gep.tp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSFeed;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

public class FeedView extends AppCompatActivity implements Observer {
    URL url;
    RSSFeed feed;
    ListView lv;
    ArrayList<RSSItem> items = new ArrayList<RSSItem>();
    RSSItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_view);

        adapter = new RSSItemAdapter(this, R.layout.feed_list_item , items);
        TextView t = (TextView)findViewById(R.id.feed_view_txt_Url);
        lv = (ListView)findViewById(R.id.feed_view_lstv_RSSItems);
        //aller cherche l'URL du feed
        Intent intent = getIntent();
        String urlString =  intent.getStringExtra("url");
        //convertir le string en url
        url = getUrl(urlString);
        //afficher l'url
        t.setText(url.toString());
        //activité qui affiche tous les items d'un feed en particulier
        lv.setAdapter(adapter);
        feed = new RSSFeed(url);
        //S'abonner au feed pour savoir quand il va avoir fini d<aller chercher les RSSItems
        feed.addObserver(this);

    }
    //Méthode appelée quand un des feeds termine son exécution
    @Override
    public void update(Observable b, Object o){
        items.addAll(feed.getItems());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
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
