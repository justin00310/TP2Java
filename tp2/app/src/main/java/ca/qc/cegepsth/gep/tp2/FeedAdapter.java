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

/**
 * Created by Utilisateur on 26/10/2016.
 */
public class FeedAdapter extends ArrayAdapter<FeedInfo> {
    Context appContext;
    FluxSuivis fluxs;
    public FeedAdapter(Context context, int resource, List<FeedInfo> feeds) {
        super(context, resource, feeds);
        appContext = context;
        fluxs = FluxSuivis.getInstance();
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.feed_list_list_item, null);
        }
        //aller chercher les données
        FeedInfo f = getItem(index);
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

        //créer les composantes visuelles
        if (f != null) {
            //aller chercher les références aux vues du feed courant
            ImageView image = (ImageView) v.findViewById(R.id.imageView);
            TextView title = (TextView) v.findViewById(R.id.textView);
            Button button = (Button) v.findViewById(R.id.button);
            //affecter les vues des bonnes valeurs
            title.setText(f.getTitle());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fluxs.remove(url);
                }
            });
        }

        return v;
    }

}