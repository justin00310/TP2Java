package ca.qc.cegepsth.gep.tp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

/**
 * Created by Utilisateur on 29/10/2016.
 */
public class RSSItemAdapter extends ArrayAdapter<RSSItem> {
    int layoutResource;
    public RSSItemAdapter(Context context, int resource, List<RSSItem> items) {
        super(context, resource, items);
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
        RSSItem item = getItem(index);
        //créer les composantes visuelles
        TextView text = (TextView) v.findViewById((R.id.feed_list_item_txt));
        text.setText(item.getDescription());
        return v;
    }

}