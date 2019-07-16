package ru.geekbrains.android.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Records {

    private TreeMap<Integer, String> results = new TreeMap<Integer, String>(Collections.reverseOrder());

    public void addNewUser(String name, Integer points) {
        if (results.size() > 5) {
            results.pollLastEntry();
            results.put(points, name);
        } else {
            results.put(points, name);
        }
    }

    public int getMinPoint() {
        return results.firstKey();
    }

    public Map getAllResults() {
        return results;
    }

    public List<String> getAllStringResults() {
        List<String> res = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, String> entry : results.entrySet()) {
            builder.setLength(0);
            String name = entry.getValue();
            String points = entry.getKey().toString();
            builder.append(name).append(" - ").append(points);
            res.add(builder.toString());
        }
        return res;
    }
}
