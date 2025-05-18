package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private final NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> seriesList = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            TimeSeries ts = ngm.countHistory(word, startYear, endYear);
            if (!ts.isEmpty()) {
                labels.add(word);
                seriesList.add(ts);
            }
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, seriesList);
        return Plotter.encodeChartAsString(chart);
    }
}