package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private final NGramMap ngramMap;

    public HistoryTextHandler(NGramMap map) {
        this.ngramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Received query: " + q.words() + ", " + q.startYear() + " to " + q.endYear());
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();


        Map<String, TimeSeries> result = new LinkedHashMap<>();

        for (String word : words) {
            TimeSeries ts = ngramMap.countHistory(word, startYear, endYear);
            result.put(word, ts);
        }


        StringBuilder sb = new StringBuilder();
        for (String word : result.keySet()) {
            sb.append(word).append(": ").append(result.get(word).toString()).append("\n");
        }

        return sb.toString().strip();
    }
}