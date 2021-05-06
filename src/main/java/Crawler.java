import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Crawler {
    private static int pageAmount = 0;
    private int maxDepth = 8, pageLimit = 10_000;
    private final DataCollector collector;


    public Crawler(int maxDepth, int pageLimit, String[] terms, int topNReports) {
        this.maxDepth = maxDepth;
        this.pageLimit = pageLimit;
        this.collector = new DataCollector(terms, topNReports);
    }

    public void crawl(int depth, String url, ArrayList<String> visited) {
        if(depth <= maxDepth) {
            Document doc = request(url, visited);
            if(doc != null) {
                Crawler.pageAmount++;
                if(Crawler.pageAmount > pageLimit) {
                    System.out.println("Finished");
                    return;
                }
                for(Element link: doc.select("a[href]")) {
                    String next_link = link.absUrl("href");

                    if (next_link == null || next_link.length() == 0)
                        continue;

                    if(!visited.contains(next_link)) {
                        crawl(depth++, next_link, visited);
                    }
                }
            }
        }
    }

    public Document request(String url, ArrayList<String> v) {
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if(con.response().statusCode() == 200) {
                String data = collector.collect(doc, url);
                System.out.println("Link: " + url + "\t" + data);
                v.add(url);
                return doc;
            }
            return  null;
        } catch (IOException e) {
            return  null;
        }
    }

    public Map<String, Integer> getBestMatches() {
        return  collector.getBestMatches();
    }
}
