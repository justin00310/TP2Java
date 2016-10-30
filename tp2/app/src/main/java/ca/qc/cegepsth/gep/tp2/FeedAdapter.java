package ca.qc.cegepsth.gep.tp2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import ca.qc.cegepsth.gep.tp2.registre.FluxSuivis;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSFeed;

/**
 * Created by Utilisateur on 26/10/2016.
 */
public class FeedAdapter extends ArrayAdapter<RSSFeed> {
    Context appContext;
    FluxSuivis fluxs;
    int layoutResource;
    public FeedAdapter(Context context, int resource, List<RSSFeed> feeds) {
        super(context, resource, feeds);
        appContext = context;
        fluxs = FluxSuivis.getInstance();
        layoutResource = resource;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(layoutResource, null);
        }
        //aller chercher les données
        RSSFeed f = getItem(index);
        final URL url = f.getUrl();
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent i = new Intent(c, FeedView.class);
                i.putExtra("url", url.toString());
                c.startActivity(i);
            }
        });
        //aller chercher les références aux vues du feed courant
        ImageView image = (ImageView) v.findViewById(R.id.imageView);
        TextView title = (TextView) v.findViewById(R.id.textView);
        Button button = (Button) v.findViewById(R.id.button);
        //affecter les vues des bonnes valeurs
        title.setText(f.getTitre());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fluxs.remove(url);
            }
        });
        return v;
    }

}