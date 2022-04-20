package model;

import lombok.Data;

import java.sql.Date;

@Data
public class StatisticsFlatAvito {
    private int dollar;
    private long averagePriceMeter;
    private long medianPriceMeter;
    private long averagePriceMeter1;
    private long medianPriceMeter1;
    private long averagePriceMeter2;
    private long medianPriceMeter2;
    private long averagePriceMeter3;
    private long medianPriceMeter3;
    private String city;
    private long date;

    public StatisticsFlatAvito(int dollar, long averagePriceMeter, long medianPriceMeter, long averagePriceMeter1, long medianPriceMeter1, long averagePriceMeter2, long medianPriceMeter2, long averagePriceMeter3, long medianPriceMeter3, String city) {
        this.dollar = dollar;
        this.averagePriceMeter = averagePriceMeter;
        this.medianPriceMeter = medianPriceMeter;
        this.averagePriceMeter1 = averagePriceMeter1;
        this.medianPriceMeter1 = medianPriceMeter1;
        this.averagePriceMeter2 = averagePriceMeter2;
        this.medianPriceMeter2 = medianPriceMeter2;
        this.averagePriceMeter3 = averagePriceMeter3;
        this.medianPriceMeter3 = medianPriceMeter3;
        this.city = city;
        this.date = System.currentTimeMillis();
    }



}


