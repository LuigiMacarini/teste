package br.com.luigi.model;

public class Data {
    private final int year;
    private final int quarter;

    public Data(int year, int quarter){
        this.year = year;
        this.quarter = quarter;
    }

    public int getYear(){
        return year;
    }

    public int getQuarter(){
        return quarter;
    }


    @Override
    public String toString(){
        return year + " --- " +quarter;
    }
}
