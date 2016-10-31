package ca.qc.cegepsth.gep.tp2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Utilisateur on 30/10/2016.
 */
public class ImageLoader extends AsyncTask<URL, Void, Bitmap> {
    ImageView imageView;

    public ImageLoader(ImageView imageView) {
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(URL... urls) {
        URL url = urls[0];
        Bitmap bimage = null;
        try {
            InputStream in = url.openStream();
            bimage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}