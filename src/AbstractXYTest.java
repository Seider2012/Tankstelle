import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.XYDataset;

/** @see http://stackoverflow.com/a/12481509/230513 */
public class AbstractXYTest {

    private static XYDataset createDataset() {
        return new AbstractXYDataset() {

            private static final int N = 16;
            private final long t = new Date().getTime();

            @Override
            public int getSeriesCount() {
                return 1;
            }

            @Override
            public Comparable getSeriesKey(int series) {
                return "Data";
            }

            @Override
            public int getItemCount(int series) {
                return N;
            }

            @Override
            public Number getX(int series, int item) {
                return Double.valueOf(t + item * 1000 * 3600 * 24);
            }

            @Override
            public Number getY(int series, int item) {
                return Math.pow(item, 1.61);
            }
        };
    }

    private static JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Ablauf vom Tagespreis", "Day", "Preis", dataset, true, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
       
        DateAxis domain = (DateAxis) plot.getDomainAxis();
        domain.setDateFormatOverride(DateFormat.getDateInstance());
        return chart;
    }
    
    public static void showChart(ArrayList<ArrayList<Eintrag>> t) throws IOException{
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
 	   //XYSeries serie;
 	   TimeSeries serie;
 	   //final XYSeriesCollection dataset = new XYSeriesCollection( );
 	   TimeSeriesCollection dataset = new TimeSeriesCollection( );
 	   for(int i=0;i<t.size();i++){
 		 
 		  //  Date date = new Date();
 		   serie = new TimeSeries( "Tankstelle"+t.get(i).get(0).getTankenr()  );
 		   for(int a=0;a<(t.get(i).size());a++){
 			   //serie.add( t.get(i).get(a).datum , t.get(i).get(a).value );
 			   serie.add( new Day(t.get(i).get(a).getDate()), t.get(i).get(a).value );
 		   }
 		   dataset.addSeries( serie );
 		   
 	   }
        
        
       // XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 300);
            }
        };
	      File XYChart = new File( "TankstellenProTag.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( XYChart, chart, 800, 300);
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        

    }
    
    public static void showChartHour(ArrayList<ArrayList<Eintrag>> t) throws IOException{
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
        
 	   //XYSeries serie;
 	   TimeSeries serie;
 	   //final XYSeriesCollection dataset = new XYSeriesCollection( );
 	   TimeSeriesCollection dataset = new TimeSeriesCollection( );
 	   for(int i=0;i<t.size();i++){
 		 
 		  //  Date date = new Date();
 		   serie = new TimeSeries( "Tankstelle"+t.get(i).get(0).getTankenr()  );
 		   for(int a=0;a<(t.get(i).size());a++){
 			   //serie.add( t.get(i).get(a).datum , t.get(i).get(a).value );
 			   serie.add( new Hour(t.get(i).get(a).getDate()), t.get(i).get(a).value );
 		   }
 		   dataset.addSeries( serie );
 		   
 	   }
        
 	   
 	   
 	   
 	
 	        JFreeChart chart = ChartFactory.createTimeSeriesChart(
 	            "Tagespreis", "Uhrzeit", "Preis", dataset, true, false, false);
 	        XYPlot plot = (XYPlot) chart.getPlot();
 	       
 	        DateAxis domain = (DateAxis) plot.getDomainAxis();
 	      //  domain.setDateFormatOverride(DateFormat.getDateInstance());
 	       domain.setDateFormatOverride(DateFormat.getTimeInstance());

 	    
 	   
 	
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 300);
            }
        };
	      File XYChart = new File( "Tankstellen24h.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( XYChart, chart, 800, 300);
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        

    }

    public static void main(String[] args) {

        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 300);
            }
        };
        f.add(chartPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}