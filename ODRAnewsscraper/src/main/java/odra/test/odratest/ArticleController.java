package odra.test.odratest;


import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Spring Controller for implemented services
 * @author ddemmer
 *
 */
@RestController
public class ArticleController {

    @Autowired
    PikioScraper pikioScraper;

    @Autowired
    SpiegelScraper spiegelScraper;

    @Autowired
    PolitykaScraper politykaScraper;

    @Autowired
    FazScraper fazScraper;

    @GetMapping(value = "/pikio")
    public List<Article> index() throws IOException {

        List<Article> articleList = new ArrayList<>();

        for (String link : pikioScraper.getNewsUrlList()) {
            articleList.add(pikioScraper.scrape(link));
        }

        return articleList;

    }


    @GetMapping(value = "/spiegel")
    public List<Article> spiegel() throws IOException {

        List<Article> articleList = new ArrayList<>();

        for (String link : spiegelScraper.getNewsUrlList()) {
            articleList.add(spiegelScraper.scrape(link));
        }

        return articleList;

    }

    @GetMapping(value = "/polityka")
    public List<Article> polityka() throws IOException {

        List<Article> articleList = new ArrayList<>();

        for (String link : politykaScraper.getNewsUrlList()) {
            articleList.add(politykaScraper.scrape(link));
        }

        return articleList;

    }

    @GetMapping(value = "/faz")
    public List<Article> faz() throws IOException {

        List<Article> articleList = new ArrayList<>();

        for (String link : fazScraper.getNewsUrlList()) {
            articleList.add(fazScraper.scrape(link));
        }

        return articleList;

    }


    @GetMapping(value = "/")
    public String empty() throws IOException {


        return "<h1>No route found. Please go to /faz, /fakt, /spiegel or /pikio</h1>";

    }


}
