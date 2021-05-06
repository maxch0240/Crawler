import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String seedUrl = "https://en.wikipedia.org/wiki/Elon_Musk";
        //String[] terms = {"Tesla", "Musk", "Gigafactory", "Elon Musk"};
        String[] terms = {"I", "not", "it", "is"};
        int topNReports = 30;
        int maxDepth = 2, pageLimit = 300;

        Crawler crawler = new Crawler(maxDepth, pageLimit, terms, topNReports);
        crawler.crawl(1, seedUrl, new ArrayList<>());

        System.out.println("\nBest matches\n");

        for (Map.Entry<String, Integer> entry : crawler.getBestMatches())
        {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }
}
