package com.lapi.lottoapi.lottoguess;

import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoNumberCrawler {

    String linkToCrawlFrom;
    private String combinationsHistoryFileName = "winningNumbers.txt";
    private Map<String, Combination> winningNumbers;
    private FileHandler fh = new FileHandler();


    public LottoNumberCrawler(String linkToCrawlFrom) {
        this.linkToCrawlFrom = linkToCrawlFrom;
        if (fh.readFileContent(combinationsHistoryFileName) != null) {
            winningNumbers = (Map<String, Combination>) fh.readFileContent(combinationsHistoryFileName);
        } else {
            winningNumbers = new HashMap<>();
            fh.createFile(combinationsHistoryFileName);
            getWinningNumbers();
            crawlNumbers();
        }
    }

    public void crawlNumbers() {
        List<String> dates = getDates();
        if (!dates.isEmpty()) {
            dates.stream().forEach((date) -> {
                try {
                    saveCombination(date);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        String json = new Gson().toJson(winningNumbers);
        fh.writeToFile(combinationsHistoryFileName, json);
    }

    private void saveCombination(String date) throws IOException {
        Document drawPage = crawlPage(linkToCrawlFrom, date);
        int[] numbers = getNumbers(drawPage);
        int[] stars = getStars(drawPage);

        Combination combination = new Combination(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4], stars[0], stars[1]);
        winningNumbers.put(date, combination);
    }

    private Document crawlPage(String link, String date) throws IOException {
        Map<String, String> params = getParamsMap(date);
        Connection connection = Jsoup.connect(link);
        connection.userAgent("Mozilla").timeout(1500).data(params);
        Document domContent = connection.post();
        return domContent;
    }

    private Map<String, String> getParamsMap(String date) {
        Map<String, String> params = new HashMap();
        params.put("formattedFilterDate", date);
        params.put("filterDate", date);
        params.put("currentDate",new Date().toString());
        return params;
    }

    private int[] getNumbers(Document domContent) {
        int[] numbers = new int[5];
        int counter = 0;
        Elements childrenSpan = domContent.select(".quotes__game > .euro-millions-logo +" +
                " .actual-numbers___body > ul.actual-numbers__numbers > " +
                ".actual-numbers__number___normal > span.transform__center");
        for (Element numberSpan : childrenSpan) {
            numbers[counter] = Integer.parseInt(numberSpan.text().trim());
            counter++;
        }
        return numbers;
    }

    private int[] getStars(Document domContent) {
        int[]  stars = new int[2];
        int counter = 0;
        Elements childrenSpan = domContent.select(".quotes__game > .euro-millions-logo +" +
                " .actual-numbers___body > ul.actual-numbers__numbers > " +
                ".actual-numbers__number___superstar > span.transform__center");
        for (Element numberSpan : childrenSpan) {
            stars[counter] = Integer.parseInt(numberSpan.text().trim());
            counter++;
        }
        return stars;
    }

    private List<String> getDates() {
        List<String> dates = (List<String>) fh.readFileContent(DrawDates.fileName);
        Map<String,Combination> combinationsMap = (Map<String, Combination>) fh.readFileContent(combinationsHistoryFileName);
        if (combinationsMap == null) {
            return dates;
        }
        for (Map.Entry<String, Combination> entry : combinationsMap.entrySet()) {
            String itemDate = entry.getKey();
            if (dates.contains(itemDate)) {
                dates.remove(itemDate);
            }
        }
        return dates;
    }

    public Map<String, Combination> getWinningNumbers() {
        DrawDates dd = new DrawDates();
        String lastDraw = dd.getLastFridayOrTuesday(new DateTime().minusDays(1));
        if (!winningNumbers.containsKey(lastDraw)) {
            dd.populateDateOfDraws();
            crawlNumbers();
        }
        return winningNumbers;
    }
}
