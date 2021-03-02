package com.lapi.lottoapi.lottoguess;

import java.util.*;

public class NumbersCombinationGenerator {

    public List<Integer> generateCombinations(int maxNumber, int count) {
        Set<Integer> combination = new HashSet<Integer>();
        do {
            int randomNumber = (int) (Math.random() * maxNumber) +1;
            combination.add(randomNumber);
        } while(combination.size() < count);
        List<Integer> intList = new ArrayList<Integer>(combination);
        return sortList(intList);
    }

    private List<Integer> sortList(List<Integer> listOfIntegers) {
        Collections.sort(listOfIntegers, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        return listOfIntegers;
    }
}
