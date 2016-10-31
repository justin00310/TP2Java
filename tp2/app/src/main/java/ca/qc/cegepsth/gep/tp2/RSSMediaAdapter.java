package ca.qc.cegepsth.gep.tp2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSMedia;

/**
 * Created by Utilisateur on 30/10/2016.
 */
public class RSSMediaAdapter extends ArrayAdapter<RSSMedia> {
    int layoutResource;
    public RSSMediaAdapter(Context context, int resource, List<RSSMedia> medias) {
        super(context, resource, medias);
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
        final RSSMedia media = getItem(index);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                Intent i = new Intent();
                c.startActivity(i);
            }
        });
        //créer les composantes visuelles
        TextView type = (TextView) v.findViewById(R.id.RSSItem_list_media_type);
        Button button = (Button) v.findViewById(R.id.RSSItem_list_media_button);
        //affecter les valeurs
        type.setText(media.getType());
        if(null != media.getUrl()) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context c = v.getContext();
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(media.getUrl().toString()));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(i);
                }
            });
        }
        return v;
    }
}
