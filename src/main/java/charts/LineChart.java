package charts;

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

import java.util.Date;

public class LineChart extends ApplicationFrame {

    public LineChart(final String title ) {
        super( title );
        final XYDataset dataset = createDataset( );
        final JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 370 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( ) {
        final TimeSeries series = new TimeSeries( "Random Data" );
        Second current = new Second( );
        Day day = new Day(new Date());
        double value = 100.0;

        for (int i = 0; i < 4000; i++) {

            try {
                value = value + Math.random( ) - 0.5;
                series.add(day, new Double( value ) );
                day = (Day) day.next( );
            } catch ( SeriesException e ) {
                System.err.println("Error adding to series");
            }
        }

        return new TimeSeriesCollection(series);
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
        final LineChart demo = new LineChart( title );
        demo.pack( );
        demo.setVisible( true );
    }
}