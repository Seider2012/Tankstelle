import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBReader {
	
	private Connection conn = null;
	private boolean isConnected = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		
	}


	
	public  ArrayList<Eintrag> readDatabase(int tankenr) throws SQLException, ClassNotFoundException{
				connect();
		 		Statement stat = conn.createStatement();
		 		ArrayList<Eintrag> data = new ArrayList<Eintrag>();
		 		
		       // stat.executeUpdate("drop table if exists people;");
		      //  stat.executeUpdate("create table people (name, occupation);");
		 		int counter=0;

		        ResultSet rs = stat.executeQuery("select * from sprit_data WHERE tankenr = "+tankenr+";");
		        while (rs.next()) {
		        	counter++;
		        	Eintrag e = new Eintrag(rs.getInt("datum"),rs.getInt("tankenr"),rs.getDouble("value"));
		           // System.out.println("datum = " + rs.getString("datum"));
		           // System.out.println("tankenr = " + rs.getString("tankenr"));
		            //System.out.println("value = " + rs.getDouble("value"));
		            data.add(e);
		           // System.out.println(counter);
		        }
		        rs.close();
		        disconnect();
		        
		        return data;
		  
	}
	private void disconnect() throws SQLException{
		if(isConnected){
		conn.close();
		 isConnected = false;
		} else{
			System.out.println("Die Verbindung ist bereits beendet");
		}
	}
	private void connect() throws SQLException, ClassNotFoundException{
		
		if(!isConnected){
			Class.forName("org.sqlite.JDBC");
			 conn = DriverManager.getConnection("jdbc:sqlite://home/seider/WorkstationNeon/TankstellenBeispiel/Database/sprit.db");
			 System.out.println("Die Verbindung wurde aufgebaut");
			 isConnected = true;
		} else{
			System.out.println("Die Verbindung steht bereits");
		}
	       
	}
	public  ArrayList<Integer> getTankstellen() throws SQLException, ClassNotFoundException{
		connect();
 		Statement stat = conn.createStatement();
 		
 		
       // stat.executeUpdate("drop table if exists people;");
      //  stat.executeUpdate("create table people (name, occupation);");
 		ArrayList<Integer> count = new ArrayList<Integer>();

        ResultSet rs = stat.executeQuery("select tankenr from sprit_data;");
        while (rs.next()) {
        	
        	if(!count.contains(rs.getInt("tankenr"))){
        	count.add(rs.getInt("tankenr"));
        	}
           // System.out.println("datum = " + rs.getString("datum"));
           // System.out.println("tankenr = " + rs.getString("tankenr"));
            //System.out.println("value = " + rs.getDouble("value"));
           // System.out.println(counter);
        }
        rs.close();
        disconnect();
        System.out.println(count);
        
        return count;
  
}
}
