package odra.test.odratest;


import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Scraper for the SPIEGEL ONLINE website, realized as Spring service.
 * @author ddemmer
 *
 */
@Service
public class SpiegelScraper extends Scraper {

	 /**
     * Extracts information of a spiegel.de news article into a @{@link Article} object
     *
     * @param url The URL of the news article
     * @return An Article Object with the information from the HTML page according to the URL
     * @throws IOException
     */
	public Article scrape(String url) throws IOException {

        Document doc = openURL(url);

        Article article = new Article();

        String headline = doc.body().getElementsByClass("headline-intro").text() + " - " + doc.body().getElementsByClass("headline").text();
        if(headline.contains("Exklusiv für Abonnenten")){
            return null;
        }

        String textBody = doc.body().getElementsByTag("p").text();
        String author = doc.body().getElementsByClass("author").text();
        author = author.replaceAll("Von","").replaceAll(" und ",", ");
        String topic = "";
        try {
        	 doc.body().getElementsByClass("current-channel-name").get(0).text();
        }
        catch(IndexOutOfBoundsException e) {
        	System.out.println("No Title");
        }
        String creationDate = doc.body().getElementsByClass("article-function-date").text();

        article.setLink(url);
        article.setHeadline(headline);
        article.setSource("https://www.spiegel.de");
        article.setSourceName("spiegel");
        article.setTextBody(textBody);
        article.setCrawlDate(new Date());
        article.setCreationDate(creationDate);
        article.setAuthor(author);
        article.setLink(url);
        article.setTopic(topic);



        return article;
    }


    /* (non-Javadoc)
     * @see odra.test.odratest.Scraper#getNewsUrlList()
     */
    @Override
    public List<String> getNewsUrlList() {
        try {
            URL feedUrl = new URL("https://www.spiegel.de/schlagzeilen/tops/index.rss");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            List<SyndEntry> entries = feed.getEntries();

            List<String> links = new ArrayList<>();
            for (SyndEntry entry : entries) {
                links.add(entry.getLink());
            }
            return links;

        } catch (FeedException fe) {
            fe.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
