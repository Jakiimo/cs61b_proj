package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: Add any necessary static/instance variables.
    private Map<String, TimeSeries> wordCountHistory;
    private TimeSeries totalWordCount;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordCountHistory = new HashMap<>();
        totalWordCount = new TimeSeries();

        //读取count
        In countsFile = new In(countsFilename);
        int countLines = 0;
        while (countsFile.hasNextLine()) {
            String line = countsFile.readLine();
            String[] parts = line.split(",");
            int year = Integer.parseInt(parts[0]);
            double count = Double.parseDouble(parts[1]);
            totalWordCount.put(year, count);
            countLines++;
        }

        System.out.println("Total years loaded from counts file: " + countLines);

        //读取words
        In wordsFile = new In(wordsFilename);
        int wordLines = 0;
        while (wordsFile.hasNextLine()) {
            String line = wordsFile.readLine();
            String[] parts = line.split("\t");
            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double count = Double.parseDouble(parts[2]);

            wordCountHistory.computeIfAbsent(word, k -> new TimeSeries())
                    .put(year, count);

            /*if (!wordCountHistory.containsKey(word)) {
                wordCountHistory.put(word, new TimeSeries());
            }*/

            if (word.equals("dog") || word.equals("cat")) {
                System.out.println("LOADED: " + word + " " + year + " → " + count);
            }
            wordLines++;
            System.out.println("Total lines loaded from words file: " + wordLines);
            System.out.println("Total unique words loaded: " + wordCountHistory.size());
            //wordCountHistory.get(word).put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!wordCountHistory.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries original = wordCountHistory.get(word);
        return new TimeSeries(original, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if (!wordCountHistory.containsKey(word)) return new TimeSeries();
        return new TimeSeries(wordCountHistory.get(word), MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        if (totalWordCount.isEmpty()) {
            return new TimeSeries();
        }
        return new TimeSeries(totalWordCount, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries wordCounts = countHistory(word, startYear, endYear);
        if (wordCounts.isEmpty()) {
            return new TimeSeries();
        }
        TimeSeries totalCounts = new TimeSeries(totalWordCount, startYear, endYear);
        return wordCounts.dividedBy(totalCounts);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        //在countHistory内部已经新new了一个数组，这里不需要再new一个直接引用就可以了
        TimeSeries wordCounts = countHistory(word);

        if (wordCounts.isEmpty()) {
            return new TimeSeries();
        }

        return wordCounts.dividedBy(totalCountHistory());
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();

        for (String word : words) {
            TimeSeries wordWeights = weightHistory(word, startYear, endYear);

            if (!wordWeights.isEmpty()) {
                result = result.plus(wordWeights);
            }
        }

        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries result = new TimeSeries();

        for (String word : words) {
            TimeSeries wordWeights = weightHistory(word);  // 整个时间范围的相对频率

            if (!wordWeights.isEmpty()) {
                result = result.plus(wordWeights);
            }
            // 如果为空，说明该词没有数据，忽略
        }

        return result;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
