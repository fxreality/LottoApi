package com.lapi.lottoapi.lottoguess;

import com.google.gson.Gson;
import java.util.*;

public class CombinationApproval {

    private List<Integer> combination;
    private int count;
    private String linkToCrawlFrom = "https://www.swisslos.ch/en/euromillions/information/winning-numbers/winning-numbers.html";
    private Map<String, Combination> winningHistoryMap;

    public CombinationApproval(List<Integer> combination) {
        this.combination = combination;
        this.count = combination.size();
        LottoNumberCrawler lnc = new LottoNumberCrawler(linkToCrawlFrom);
        winningHistoryMap = lnc.getWinningNumbers();
    }

    public void setCombination(List<Integer> combination) {
        this.combination = combination;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int averageCheck(int maxNumber) {
        int sum = 0;
        for (int number : this.combination) {
            sum += number;
        }
        return (sum/this.count)-(maxNumber/2);
    }

    public int historyCheck() {
        int level = 0;
        Gson gson = new Gson();
        for (Map.Entry<String, Combination> entry : winningHistoryMap.entrySet()) {
            Combination combination = gson.fromJson(String.valueOf(entry.getValue()), Combination.class);
            List<Integer> integerList = commonIntegers(combination);
            level = integerList.size()>level?integerList.size():level;
        }
        return level;
    }

    private List<Integer> commonIntegers(Combination item) {
        int[] itemCombination = {item.getNumber1(), item.getNumber2(), item.getNumber3(), item.getNumber4(), item.getNumber5()};
        List<Integer> integerList = new ArrayList<>();
        for(int s : itemCombination) integerList.add(s);
        integerList.retainAll(this.combination);
        return integerList;
    }
}
