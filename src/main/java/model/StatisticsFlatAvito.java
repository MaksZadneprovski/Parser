package model;

import lombok.Data;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Data
public class StatisticsFlatAvito {
    private int dollar;
    private long averagePriceMeter;
    private long medianPriceMeter;
    private long averagePrice;
    private long medianPrice;
    private long averagePrice1;
    private long medianPrice1;
    private long averagePrice2;
    private long medianPrice2;
    private long averagePrice3;
    private long medianPrice3;
    private String city;
    private long date;

    public StatisticsFlatAvito(int dollar, long averagePriceMeter, long medianPriceMeter,long averagePrice, long medianPrice, long averagePrice1, long medianPrice1, long averagePrice2, long medianPrice2, long averagePrice3, long medianPrice3, String city, long date) {
        this.dollar = dollar;
        this.averagePriceMeter = averagePriceMeter;
        this.medianPriceMeter = medianPriceMeter;
        this.averagePrice = averagePrice;
        this.medianPrice = medianPrice;
        this.averagePrice1 = averagePrice1;
        this.medianPrice1 = medianPrice1;
        this.averagePrice2 = averagePrice2;
        this.medianPrice2 = medianPrice2;
        this.averagePrice3 = averagePrice3;
        this.medianPrice3 = medianPrice3;
        this.city = city;
        this.date = date;
    }

    public static XYDataset createTimeSeriesCollection(List<StatisticsFlatAvito> list) {

        final TimeSeriesCollection dataset = new TimeSeriesCollection( );

        for (String s:FlatAvito.link.keySet()) {
            List<StatisticsFlatAvito> sFAlist  = list.stream().filter(sFA -> sFA.getCity().equals(s)).collect(Collectors.toList());
            TimeSeries series = new TimeSeries(s);
            for (StatisticsFlatAvito f : sFAlist) {
                Hour hour = new Hour(new Date(f.getDate()));

                series.addOrUpdate(hour, f.getAveragePrice());
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

}


