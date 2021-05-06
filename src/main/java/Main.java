import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String seedUrl = "https://en.wikipedia.org/wiki/Elon_Musk";
        String[] terms = {"Tesla"};
        int maxDepth = 8, pageLimit = 10_000;
        Crawler crawler = new Crawler(maxDepth, pageLimit, terms);
        crawler.crawl(1, seedUrl, new ArrayList<String>());
    }
}
