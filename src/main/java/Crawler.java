import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Crawler {
    private static int pageAmount = 0;
    private final int maxDepth;
    private final int pageLimit;
    private final DataCollector collector;
    FileWriter writerAll = new FileWriter("allSites.csv");
    FileWriter writerTOP = new FileWriter("TOPSites.csv");
    private final ArrayList<String> sitesWData = new ArrayList<>();
    private final ArrayList<String> topSites = new ArrayList<>();


    public Crawler(int maxDepth, int pageLimit, String[] terms, int topNReports) throws IOException {
        this.maxDepth = maxDepth;
        this.pageLimit = pageLimit;
        this.collector = new DataCollector(terms, topNReports);
    }

    public void crawl(int depth, String url, ArrayList<String> visited) {
        if (depth <= maxDepth) {
            Document doc = request(url, visited);
            if (doc != null) {
                Crawler.pageAmount++;

                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");

                    if (next_link == null || next_link.length() == 0)
                        continue;

                    if (!visited.contains(next_link)) {
                        if (Crawler.pageAmount >= pageLimit) {

                            return;
                        }
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

            if (con.response().statusCode() == 200) {
                String data = collector.collect(doc, url);
                System.out.println("Link: " + url + "\t" + data);
                sitesWData.add(url + "\t" + data);
                v.add(url);
                return doc;
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<String> getBestMatches() {
        for (Map.Entry<String, Integer> entry : collector.getBestMatches()) {
            topSites.add(entry.getKey() + "\t" + entry.getValue());
        }
        return topSites;
    }

    public void CSVWriter() throws IOException {
        String[] entries = sitesWData.toArray(new String[0]);
        String[] topEntries = topSites.toArray(new String[0]);

        for (String str : entries) writerAll.write(str + "\n");
        writerAll.close();

        for (String str : topEntries) writerTOP.write(str + "\n");
        writerTOP.close();
    }
}
