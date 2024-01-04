package fr.insa.mas.Database;
import java.sql.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/benevole")
public class DatabaseManager {
	Connection connection = null;
	

	 public static void main(String[] args) {
		 DatabaseManager db = new DatabaseManager();
        db.dbinit();
        db.ajouterBenevol("test", "test");
	
	    }
	 public void dbinit() {
		 
	        String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_064";
	        String user = "projet_gei_064";
	        String password = "Aepahzu1";
	        
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            return;
	        }

	        // Establish a connection
	        try  {
	        	this.connection = DriverManager.getConnection(url, user, password);
	            System.out.println("Connected to the database!");
	            // Perform database operations here
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }
	 public int getNumberBenevol() {
			String sql = "SELECT COUNT(*) FROM Benevoles";
			try (Statement stmt = connection.createStatement()) {
				ResultSet rs = stmt.executeQuery(sql);
               int count = rs.getInt(1);
               return count;
           } catch (SQLException e) {
               System.out.println(e.getMessage());
               return 0;
			}
	 }
	   @GetMapping("/ajouter")
	   public void ajouterBenevol( String nom,String prenom) {
		   int id = this.getNumberBenevol() +1 ;
			String sql = "INSERT INTO Benevoles VALUES(?,?,?)";
			//(IDUSERS,LOGIN)
			try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setInt(1, id);
				pstmt.setString(2, nom);
				pstmt.setString(3, prenom);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	
	  
        }

