package com.lapi.lottoapi;

import com.lapi.lottoapi.lottoguess.CombinationApproval;
import com.lapi.lottoapi.lottoguess.NumbersCombinationGenerator;
import com.lapi.lottoapi.lottoguess.Statistics;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/getCombination")
    public Greeting getCombination() throws JSONException {
        NumbersCombinationGenerator ncg = new NumbersCombinationGenerator();
        List numbers = ncg.generateCombinations(50,5);
        CombinationApproval ca = new CombinationApproval(numbers);

        while (ca.historyCheck() > 1 && ca.averageCheck(50) > -5 && ca.averageCheck(50) < 5) {
            numbers = ncg.generateCombinations(50,5);
            ca.setCombination(numbers);
            ca.setCount(numbers.size());
        }
        List stars = ncg.generateCombinations(12,2);
        JSONObject response = new JSONObject();
        response.put("numbers", numbers);
        response.put("stars", stars);

        return new Greeting(counter.incrementAndGet(), numbers,stars);
    }

    @GetMapping("/checkCombination")
    public Statistics checkCombination(@RequestParam String number1, String number2, String number3, String number4, String number5) throws JSONException {
        List<Integer> combination = new ArrayList<Integer>(new HashSet<Integer>());

        combination.add(Integer.valueOf(number1));
        combination.add(Integer.valueOf(number2));
        combination.add(Integer.valueOf(number3));
        combination.add(Integer.valueOf(number4));
        combination.add(Integer.valueOf(number5));

        JSONObject response = new JSONObject();
        Collections.sort(combination);
        if (combination.stream().distinct().count() != 5 ||
                combination.get(combination.size() -1) > 50 ||
                combination.get(0) < 1){
            return new Statistics(counter.incrementAndGet(), 1, 1);
        }
        CombinationApproval ca = new CombinationApproval(combination);

        response.put("history", ca.historyCheck());
        response.put("average", ca.averageCheck(50));

        return new Statistics(counter.incrementAndGet(), ca.historyCheck() , ca.averageCheck(50));
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public void handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " param missing");
    }
}
