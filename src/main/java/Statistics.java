import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Statistics {
    private final Integer topNReports;
    private final Map<String, Integer> allLinks = new HashMap<>();

    public Statistics(int topNReports) {
        this.topNReports = topNReports;
    }

    public void setAllLinks(String key, int value) {
        allLinks.put(key, value);
    }

    public List<Map.Entry<String, Integer>>
    getBestMatches()
    {
        Comparator<? super Map.Entry<String, Integer>> comparator =
                (Comparator<Entry<String, Integer>>) (e0, e1) -> {
                    Integer v0 = e0.getValue();
                    Integer v1 = e1.getValue();
                    return v0.compareTo(v1);
                };
        PriorityQueue<Entry<String, Integer>> highest =
                new PriorityQueue<>(topNReports, comparator);
        for (Entry<String, Integer> entry : allLinks.entrySet())
        {
            highest.offer(entry);
            while (highest.size() > topNReports)
            {
                highest.poll();
            }
        }

        List<Map.Entry<String, Integer>> result = new ArrayList<>();
        while (highest.size() > 0)
        {
            result.add(highest.poll());
        }
        return result;
    }
}
