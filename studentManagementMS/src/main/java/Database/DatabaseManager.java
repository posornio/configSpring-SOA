package Database;
import java.sql.*;

public class DatabaseManager {
	static final String url = "jdbc:sqlite:src/test.db";
	private Connection conn = null;
	
	  public void dbinit () {
		   try{		
		    	// create a connection to the database
		    	  Class.forName("org.sqlite.JDBC");
		          this.conn = DriverManager.getConnection(url);
		          
		          System.out.println("Connection to SQLite \"users\" has been established.");
		          
		          
		      } catch (SQLException e) {
		          System.out.println(e.getMessage());
		      } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //DBEAVER;;  FAIRE TABLES ET DATABASE MANAGER 
	   }
	      
}
