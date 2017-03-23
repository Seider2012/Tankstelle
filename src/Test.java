import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Test {
	static DBReader reader = new DBReader();

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		// TODO Auto-generated method stub
		//System.out.println(java.util.Date.getTime());
		XYLineChart_image chart = new XYLineChart_image();
		reader.getTankstellen();
		AbstractXYTest f = new AbstractXYTest();
		

		ArrayList<ArrayList<Eintrag>> dataset = new ArrayList<ArrayList<Eintrag>>();
		for(int z=1;z<6;z++){
		dataset.add(interpoliere365(reader.readDatabase(z),convertToDate(1485957044)));
		}
		
		
		
		
//		for(int z=0;z<reader.getTankstellen().size();z++){
//		dataset.add(interpoliere365(reader.readDatabase(reader.getTankstellen().get(z)),convertToDate(1485957044)));
//		}
		
		//AbstractXY f = new AbstractXY();

		f.showChart(dataset);
		csvWriter(dataset,"interpolierteDaten365days");
		
		dataset = new ArrayList<ArrayList<Eintrag>>();
		for(int z=1;z<6;z++){
		dataset.add(interpoliere24h(reader.readDatabase(z),convertToDate(1485957044)));
		}
		f.showChartHour(dataset);
		
		csvWriter(dataset,"interpolierteDaten24h");
		//chart.makeChart(dataset);
		
		//86400
		//reader.readDatabase(1);
		System.out.println(convertToDate(1485957044));
		System.out.println(reduceToDay(convertToDate(1485957044)));
		System.out.println(convertToTimeStamp(reduceToDay(convertToDate(1485957044))));
		
	}
	
	public static ArrayList<Eintrag> interpoliere365(ArrayList<Eintrag> data, java.util.Date d){
		
		ArrayList<Eintrag> iData = new ArrayList<Eintrag>();
		
		 LinearInterpolator li = new LinearInterpolator();
		   PolynomialSplineFunction psf = li.interpolate(getAllDatum(data),getAllvalue(data));
		   
		   double aktDatum = convertToTimeStamp(reduceToDay(d))-365*86400;
		   
		   for(int i=0; i<365;i++){
			   
			   iData.add(new Eintrag(aktDatum,data.get(0).getTankenr(),psf.value(aktDatum)));
			   System.out.println(convertToDate((int)aktDatum)+"\n  "+data.get(0).getTankenr()+" | "+psf.value(aktDatum));
			   aktDatum +=86400;
		   }
		 //  double yi = psf.value(xi);
		   return iData;
	}
	public static ArrayList<Eintrag> interpoliere24h(ArrayList<Eintrag> data, java.util.Date d){
		
		ArrayList<Eintrag> iData = new ArrayList<Eintrag>();
		
		 LinearInterpolator li = new LinearInterpolator();
		   PolynomialSplineFunction psf = li.interpolate(getAllDatum(data),getAllvalue(data));
		   
		   double aktDatum = convertToTimeStamp(reduceToDay(d))-86400/2-86400/24;
		   
		   for(int i=0; i<24;i++){
			   
			   iData.add(new Eintrag(aktDatum,data.get(0).getTankenr(),psf.value(aktDatum)));
			   System.out.println(convertToDate((int)aktDatum)+"\n  "+data.get(0).getTankenr()+" | "+psf.value(aktDatum));
			   aktDatum +=86400/24;
		   }
		 //  double yi = psf.value(xi);
		   return iData;
	}
	
	public static double[] getAllDatum(ArrayList<Eintrag> e){
		double[] datum= new double[e.size()];
		for(int i =0; i<e.size();i++){
			datum[i]= e.get(i).getDatum();
		}
		return datum;
	}
	public static double[] getAllvalue(ArrayList<Eintrag> e){
		double[] value= new double[e.size()];
		for(int i =0; i<e.size();i++){
			value[i]= e.get(i).getValue();
		}
		return value;
	}
	public static java.util.Date convertToDate(int timeStamp){
		java.util.Date time=new java.util.Date(timeStamp*1000L);
		return time;
	}
	
	public static java.util.Date reduceToDay(java.util.Date time){
		time.setHours(1);
		time.setMinutes(0);
		time.setSeconds(0);
		return time;
	}
	public static double convertToTimeStamp(java.util.Date time){
		double unixTime = (double) time.getTime()/1000;
		return unixTime;
	}
	
	public static void csvWriter(ArrayList<ArrayList<Eintrag>> dataset,String name) throws FileNotFoundException{
	   
	        PrintWriter pw = new PrintWriter(new File(name+".csv"));
	        StringBuilder sb = new StringBuilder();
	        sb.append("datum");
	        sb.append(',');
	        sb.append("tankenr");
	        sb.append(',');
	        sb.append("value");
	        sb.append('\n');
	        
			for(int z=0;z<dataset.size();z++){
				for(int y=0;y<dataset.get(z).size();y++){
			        sb.append(dataset.get(z).get(y).datum);
			        sb.append(',');
			        sb.append(dataset.get(z).get(y).tankenr);
			        sb.append(',');
			        sb.append(dataset.get(z).get(y).value);
			        sb.append('\n');
				}
				}



	        pw.write(sb.toString());
	        pw.close();
	        System.out.println("done!");
	    
	}
	
	

}
