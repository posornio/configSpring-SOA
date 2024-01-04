package fr.insa.mas.BenevolManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BenevolManagerApplication {
	Connection connection = null;
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(BenevolManagerApplication.class, args);
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
 @GetMapping("benevoles/nbOf")
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
   @PostMapping("benevoles/add")
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

    @GetMapping("benevoles/getAll")
    public List<Benevole> getAllBenevole() {
        String sql = "SELECT * FROM Benevoles";
        List<Benevole> benevoles = new ArrayList<Benevole>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                Benevole benevole = new Benevole(id, nom, prenom);
                benevoles.add(benevole);
            }
            return benevoles;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("benevoles/{id}")
    public Benevole getBenevoleById(@PathVariable("id") int id) {
        String sql = "SELECT * FROM Benevoles WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                Benevole benevole = new Benevole(id, nom, prenom);
                return benevole;
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    


}
