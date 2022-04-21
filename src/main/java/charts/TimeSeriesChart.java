package charts;

import db.StatisticsDAO;
import model.StatisticsFlatAvito;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Day;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import java.sql.Date;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeSeriesChart extends ApplicationFrame {

    public TimeSeriesChart(final String title ) {
        super( title );
        StatisticsDAO statisticsDAO = new StatisticsDAO();
        XYDataset dataset = StatisticsFlatAvito.createTimeSeriesCollection(statisticsDAO.getAllDataList());
        final JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 600 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );
    }

    private JFreeChart createChart( final XYDataset dataset ) {
        return ChartFactory.createTimeSeriesChart(
                "Computing Test",
                "Seconds",
                "Value",
                dataset,
                false,
                false,
                false);
    }

    public static void main( final String[ ] args ) {
        final String title = "Time Series Management";
        final TimeSeriesChart demo = new TimeSeriesChart( title );
        demo.pack( );
        demo.setVisible( true );
    }
}