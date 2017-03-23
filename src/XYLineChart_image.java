import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtilities; 

public class XYLineChart_image
{
   public static void main( String[ ] args )throws Exception
   {
	      final XYSeries firefox = new XYSeries( "Firefox" );
	      firefox.add( 1.0 , 1.0 );
	      firefox.add( 2.0 , 4.0 );
	      firefox.add( 3.0 , 3.0 );
	      final XYSeries chrome = new XYSeries( "Chrome" );
	      chrome.add( 1.0 , 4.0 );
	      chrome.add( 2.0 , 5.0 );
	      chrome.add( 3.0 , 6.0 );
	      final XYSeries iexplorer = new XYSeries( "InternetExplorer" );
	      iexplorer.add( 3.0 , 4.0 );
	      iexplorer.add( 4.0 , 5.0 );
	      iexplorer.add( 5.0 , 4.0 );
	      final XYSeriesCollection dataset = new XYSeriesCollection( );
	      dataset.addSeries( firefox );
	      dataset.addSeries( chrome );
	      dataset.addSeries( iexplorer );

	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         "Browser usage statastics", 
	         "Category",
	         "Score", 
	         dataset,
	         PlotOrientation.VERTICAL, 
	         true, true, false);
	      
	      int width = 640; /* Width of the image */
	      int height = 480; /* Height of the image */ 
	      File XYChart = new File( "XYLineChart.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
   public void makeChart(ArrayList<ArrayList<Eintrag>> t) throws IOException{
	   
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


	     

	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         "Tankstellen Preisdurchschnitt", 
	         "Datum",
	         "Preis", 
	         dataset,
	         PlotOrientation.VERTICAL, 
	         true, true, false);
	      
	      int width = 640; /* Width of the image */
	      int height = 480; /* Height of the image */ 
	      File XYChart = new File( "XYLineChart.jpeg" ); 
	      ChartUtilities.saveChartAsJPEG( XYChart, xylineChart, width, height);
   }
   


}