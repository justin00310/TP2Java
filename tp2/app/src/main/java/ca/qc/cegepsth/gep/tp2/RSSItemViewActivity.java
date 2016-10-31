package ca.qc.cegepsth.gep.tp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSMedia;

public class RSSItemViewActivity extends AppCompatActivity {
    RSSItem item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssitem_view);

        Bundle b = this.getIntent().getExtras();
        item = (RSSItem)b.getSerializable("item") ;
        //aller chercher les vues
        TextView title = (TextView) findViewById(R.id.RSSItem_view_activity_title);
        TextView author = (TextView) findViewById(R.id.RSSItem_view_activity_author);
        TextView date = (TextView) findViewById(R.id.RSSItem_view_activity_date);
        TextView description = (TextView) findViewById(R.id.RSSItem_view_activity_description);
        ImageView image = (ImageView) findViewById(R.id.RSSItem_view_activity_image);
        ListView mediaList = (ListView) findViewById(R.id.RSSItem_view_activity_media);

        //affecter les vues des bonnes valeurs
        title.setText(item.getTitre());
        author.setText(item.getAuteur());
        if(null != item.getDate()) {
            date.setText(item.getDate().toString());
        }
        description.setText(item.getDescription());
        if(null != item.getImageUrl()) {
            new ImageLoader(image).execute(item.getImageUrl());
        }
        ArrayList<RSSMedia> medias = new ArrayList<RSSMedia>();
        if(null != item.getMedias()) {
            for (RSSMedia m : item.getMedias()) {
                medias.add(m);
            }
        }
        RSSMediaAdapter adapter = new RSSMediaAdapter(this, R.layout.rssitem_list_media, medias);
        mediaList.setAdapter(adapter);
    }
}
