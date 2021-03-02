package com.lapi.lottoapi;

import java.util.List;

public class Greeting {
    private final long id;
    private final String number1;
    private final String number2;
    private final String number3;
    private final String number4;
    private final String number5;

    private final String star1;
    private final String star2;

    public Greeting(long id, List numbers, List stars) {
        this.id = id;
        this.number1=numbers.get(0).toString();
        this.number2=numbers.get(1).toString();
        this.number3=numbers.get(2).toString();
        this.number4=numbers.get(3).toString();
        this.number5=numbers.get(4).toString();

        this.star1=stars.get(0).toString();
        this.star2=stars.get(1).toString();
    }

    public long getId() {
        return id;
    }

    public String getNumber1() {
        return number1;
    }

    public String getNumber2() {
        return number2;
    }

    public String getNumber3() {
        return number3;
    }

    public String getNumber4() {
        return number4;
    }

    public String getNumber5() {
        return number5;
    }

    public String getStar1() {
        return star1;
    }

    public String getStar2() {
        return star2;
    }
}