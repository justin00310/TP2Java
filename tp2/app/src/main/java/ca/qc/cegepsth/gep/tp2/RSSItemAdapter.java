package ca.qc.cegepsth.gep.tp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.qc.cegepsth.gep.tp2.registre.FluxSuivis;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

/**
 * Created by Utilisateur on 29/10/2016.
 */
public class RSSItemAdapter extends ArrayAdapter<RSSItem> {
    int layoutResource;
    FluxSuivis fluxs;
    public RSSItemAdapter(Context context, int resource, List<RSSItem> items) {
        super(context, resource, items);
        layoutResource = resource;
        fluxs = FluxSuivis.getInstance();
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
        final RSSItem item = getItem(index);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //quand on clique sur le rssitem, on l<ajoute 'a la liste d<elements lus.
                //TODO
                //partir une 3ième activité -> vue d<un seul rssItem
                Context c = v.getContext();
                Intent i = new Intent(c, RSSItemViewActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item", item);
                i.putExtras(b);
                c.startActivity(i);
            }
        });
        //créer les composantes visuelles
        TextView title = (TextView) v.findViewById((R.id.feed_list_item_title));
        TextView author = (TextView) v.findViewById((R.id.feed_list_item_author));
        TextView date = (TextView) v.findViewById((R.id.feed_list_item_date));
        title.setText(item.getTitre());
        author.setText(item.getAuteur());
        if(null != item.getDate()) {
            date.setText(item.getDate().toString());
        }
        return v;
    }

}