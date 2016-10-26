package ca.qc.cegepsth.gep.tp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;

public class FeedView extends AppCompatActivity {
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_view);

        //afficher l'URL du feed
        Intent intent = getIntent();
        String urlString =  intent.getStringExtra("url");
        url = getUrl(urlString);
        TextView t = (TextView)findViewById(R.id.txtUrl);
        t.setText(url.toString());
        //activité qui affiche tous les items d'un feed en particulier

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
