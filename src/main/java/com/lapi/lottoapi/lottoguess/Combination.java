package com.lapi.lottoapi.lottoguess;

public class Combination {
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;

    private int star1;
    private int star2;

    public Combination(int number1, int number2, int number3, int number4, int number5, int star1, int star2) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
        this.number5 = number5;
        this.star1 = star1;
        this.star2 = star2;
    }

    public int getNumber1() {
        return number1;
    }

    public int getNumber2() {
        return number2;
    }

    public int getNumber3() {
        return number3;
    }

    public int getNumber4() {
        return number4;
    }

    public int getNumber5() {
        return number5;
    }

    public int getStar1() {
        return star1;
    }

    public int getStar2() {
        return star2;
    }
}
