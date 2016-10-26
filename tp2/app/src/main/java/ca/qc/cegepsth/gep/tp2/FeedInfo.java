package ca.qc.cegepsth.gep.tp2;

import android.net.Uri;

import java.net.URL;

/**
 * Created by Utilisateur on 26/10/2016.
 */
public class FeedInfo {
    private URL url;
    private Uri imageUri;
    private String title;

    public FeedInfo(URL FeedUrl, Uri image, String titleText){
        url = FeedUrl;
        imageUri = image;
        title = titleText;
    }
    public URL getUrl(){
        return url;
    }
    public Uri getImageUri(){
        return imageUri;
    }
    public String getTitle(){
        return title;
    }
}
