package model;

import lombok.Data;

import java.sql.Date;

@Data
public class StatisticsFlatAvito {
    private int dollar;
    private long averagePriceMeter;
    private long medianPriceMeter;
    private long averagePrice1;
    private long medianPrice1;
    private long averagePrice2;
    private long medianPrice2;
    private long averagePrice3;
    private long medianPrice3;
    private String city;
    private long date;

    public StatisticsFlatAvito(int dollar, long averagePriceMeter, long medianPriceMeter, long averagePrice1, long medianPrice1, long averagePrice2, long medianPrice2, long averagePrice3, long medianPrice3, String city) {
        this.dollar = dollar;
        this.averagePriceMeter = averagePriceMeter;
        this.medianPriceMeter = medianPriceMeter;
        this.averagePrice1 = averagePrice1;
        this.medianPrice1 = medianPrice1;
        this.averagePrice2 = averagePrice2;
        this.medianPrice2 = medianPrice2;
        this.averagePrice3 = averagePrice3;
        this.medianPrice3 = medianPrice3;
        this.city = city;
        this.date = System.currentTimeMillis();
    }
}


