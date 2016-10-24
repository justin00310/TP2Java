package ca.qc.cegepsth.gep.tp2;

import org.junit.Test;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import ca.qc.cegepsth.gep.tp2.rssparser.RSSFeed;
import ca.qc.cegepsth.gep.tp2.rssparser.RSSItem;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class testFlux implements Observer {

    Exception error;
    boolean ready;

    @Override
    public void update(Observable o, Object arg) {
        error = (Exception) arg;
        ready = true;
    }

    public RSSFeed testRSS(String url) throws Exception {
        error = null;
        ready = false;

        RSSFeed feed = new RSSFeed(new URL(url));
        feed.addObserver(this);

        synchronized(this) {
            while (!ready) wait(10);
        }
        assertNull(error);
        assertTrue(feed.getItems().size() > 0);
        return feed;
    }

    @Test(timeout=5000)
    public void lesAnneesLimiereAudio() throws Exception {
        // À partir de http://ici.radio-canada.ca/emissions/les_annees_lumiere/2016-2017/
        // modifier maniellement adresse rss ipod pour adresse http
        RSSFeed feed = testRSS("http://rss.radio-canada.ca/balado/radio/lumiere.xml");
        assertNotNull(feed.getImageUrl());
        RSSItem item = feed.getItems().listIterator().next();
        // normal, pas d'image pour ce site...  assertNotNull(item.getImageUrl());
        assertTrue(item.getMedias().size()>0);
    }

    @Test(timeout=5000)
    public void lesAnneesLimierePhoto() throws Exception {
        // À partir de http://ici.radio-canada.ca/emissions/les_annees_lumiere/2016-2017/
        RSSFeed feed = testRSS("http://rss.radio-canada.ca/fils/radio/lesanneeslumiere.xml");
        assertNotNull(feed.getImageUrl());
        RSSItem item = feed.getItems().listIterator().next();
        // normal, pas d'image pour ce site...  assertNotNull(item.getImageUrl());
        assertTrue(item.getMedias().size()>0);
    }

    @Test(timeout=5000)
    public void securityNowAudio() throws Exception {
        //À partir de http://twit.tv/sn
        RSSFeed feed = testRSS("http://feeds.twit.tv/sn.xml");
        assertNotNull(feed.getImageUrl());
        RSSItem item = feed.getItems().listIterator().next();
        //normal, pas d'image pour ce site...  assertNotNull(item.getImageUrl());
        assertTrue(item.getMedias().size()>0);
    }

    @Test(timeout=5000)
    public void securityNowVideo() throws Exception {
        //À partir de http://twit.tv/sn
        RSSFeed feed = testRSS("http://feeds.twit.tv/sn_video_hd.xml");
        assertNotNull(feed.getImageUrl());
        RSSItem item = feed.getItems().listIterator().next();
        // normal assertNotNull(item.getImageUrl());
        assertTrue(item.getMedias().size()>0);
    }

    @Test(timeout=5000)
    public void hanselminutes() throws Exception {
        // À partir de http://www.hanselminutes.com
        RSSFeed feed = testRSS("http://feeds.podtrac.com/9dPm65vdpLL1");
        assertNotNull(feed.getImageUrl());
        RSSItem item = feed.getItems().listIterator().next();
        // normal, pas d'image pour ce site... assertNotNull(item.getImageUrl());
        assertTrue(item.getMedias().size()>0);
    }

    @Test(timeout=5000)
    public void visualStudioTalkShow() throws Exception {
        // À partir de http://visualstudiotalkshow.libsyn.com/
        RSSFeed feed = testRSS("http://visualstudiotalkshow.libsyn.com/rss");
        assertNotNull(feed.getImageUrl());
        RSSItem item = feed.getItems().listIterator().next();
        assertNotNull(item.getImageUrl());
        assertTrue(item.getMedias().size()>0);
    }


}