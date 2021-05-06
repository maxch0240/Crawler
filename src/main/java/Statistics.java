import java.util.HashMap;
import java.util.Map;

public class Statistics {
    private int topNReports;
    private Map<String, Integer> allLinks = new HashMap<>();

    public Statistics(int topNReports) {
        this.topNReports = topNReports;
    }

    public Map<String, Integer> getAllLinks() {
        return allLinks;
    }

    public void setAllLinks(String key, int value) {
        allLinks.put(key, value);
    }

    public Map<String, Integer> getBestMatches() {
        return  null;
    }
}
