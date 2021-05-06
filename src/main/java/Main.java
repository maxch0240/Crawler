import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String seedUrl = "https://en.wikipedia.org/wiki/Elon_Musk";
        //String[] terms = {"Tesla", "Musk", "Gigafactory", "Elon Musk"};
        String[] terms = {"I", "not", "it", "is"};
        int topNReports = 30;
        int maxDepth = 2, pageLimit = 1000;

        Crawler crawler = new Crawler(maxDepth, pageLimit, terms, topNReports);
        crawler.crawl(1, seedUrl, new ArrayList<String>());

        crawler.getBestMatches();
    }
}
