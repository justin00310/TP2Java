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
public class testFlux {

    Exception error;
    boolean ready;

    public RSSFeed testRSS(String url) throws Exception {
        error = null;
        ready = false;

        RSSFeed feed = new RSSFeed(new URL(url));
        feed.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                error = (Exception)arg;
                ready = true;
            }
        });

        synchronized (feed) {
            while (!ready) feed.wait(10);
            assertNull(error);
            assertTrue(feed.getItems().size() > 0);
        }
        return feed;
    }

    @Test
    public void lesAnneesLimiereAudio() throws Exception {
        // À partir de http://ici.radio-canada.ca/emissions/les_annees_lumiere/2016-2017/
        // modifier maniellement adresse rss ipod pour adresse http
        RSSFeed feed = testRSS("http://rss.radio-canada.ca/balado/radio/lumiere.xml");
        assertNotNull(feed.getImage());
        RSSItem item = feed.getItems().listIterator().next();
        assertNotNull(item.getMedias());
    }

    @Test
    public void lesAnneesLimierePhoto() throws Exception {
        // À partir de http://ici.radio-canada.ca/emissions/les_annees_lumiere/2016-2017/
        RSSFeed feed = testRSS("http://rss.radio-canada.ca/fils/radio/lesanneeslumiere.xml");
        assertNotNull(feed.getImage());
        RSSItem item = feed.getItems().listIterator().next();
        assertNotNull(item.getMedias());
    }

    @Test
    public void securityNowAudio() throws Exception {
        //À partir de http://twit.tv/sn
        RSSFeed feed = testRSS("http://feeds.twit.tv/sn.xml");
        assertNotNull(feed.getImage());
        RSSItem item = feed.getItems().listIterator().next();
        assertNotNull(item.getMedias());
    }

    @Test
    public void securityNowVideo() throws Exception {
        //À partir de http://twit.tv/sn
        RSSFeed feed = testRSS("http://feeds.twit.tv/sn_video_hd.xml");
        assertNotNull(feed.getImage());
        RSSItem item = feed.getItems().listIterator().next();
        assertNotNull(item.getMedias());
    }

    @Test
    public void hanselminutes() throws Exception {
        // À partir de http://www.hanselminutes.com
        RSSFeed feed = testRSS("http://feeds.podtrac.com/9dPm65vdpLL1");
        assertNotNull(feed.getImage());
        RSSItem item = feed.getItems().listIterator().next();
        assertNotNull(item.getMedias());
    }

    @Test
    public void visualStudioTalkShow() throws Exception {
        // À partir de http://visualstudiotalkshow.libsyn.com/
        RSSFeed feed = testRSS("http://visualstudiotalkshow.libsyn.com/rss");
        assertNotNull(feed.getImage());
        RSSItem item = feed.getItems().listIterator().next();
        assertNotNull(item.getMedias());
    }

}