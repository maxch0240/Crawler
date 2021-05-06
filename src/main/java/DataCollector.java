import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCollector {
    private final String[] terms;
    private final Statistics statistics;

    public DataCollector(String[] terms, int topNReports) {
        this.terms = terms;
        this.statistics = new Statistics(topNReports);
    }

    public String collect(Document doc, String url) {
        String page = doc.body().text();
        StringBuilder strBuilder = new StringBuilder();

        int totalAmount = 0;

        for (String term : terms) {
            Pattern pattern = Pattern.compile(term);
            Matcher matcher = pattern.matcher(page);

            int count = 0;
            while (matcher.find()) {
                count++;
                totalAmount++;
            }
            strBuilder.append(" ").append(count);
        }

        strBuilder.append("\ntotal amount ").append(totalAmount);
        statistics.setAllLinks(url, totalAmount);
        return strBuilder.toString();
    }

    public List<Map.Entry<String, Integer>> getBestMatches() {
        return statistics.getBestMatches();
    }
}

