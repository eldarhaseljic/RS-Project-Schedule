package application;

import java.util.List;

import javax.persistence.Query;

import entiteti.Korisnik;
import entiteti.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	private Label lblStatus;
	
	@FXML
	private TextField txtUsername;
	
	@FXML
	private TextField txtPassword;
	
	public void Login(ActionEvent event) throws Exception {
		
		boolean exists = false;
		
		String usernameField = txtUsername.getText();
		String passwordField = txtPassword.getText();
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT k FROM Korisnik k");
		List<Korisnik> korisnici = q.getResultList();
		
		for(Korisnik k : korisnici)
		{
			String username = k.getUsername();
			String password = k.getPassword();
			
			if(usernameField.equals(username) && passwordField.equals(password))
			{
				exists = true;
				if(!k.isNastavnik() && !k.isProdekan())
				{
					Parent root = FXMLLoader.load(getClass().getResource("ToDo.fxml"));
					Scene scene = new Scene(root);
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					//primaryStage.setResizable(true);
					primaryStage.show();
				}
				
				else if(k.isNastavnik())
				{
					Parent root = FXMLLoader.load(getClass().getResource("NastavnikScreen.fxml"));
					Scene scene = new Scene(root);
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					//primaryStage.setResizable(true);
					primaryStage.show();
				}
				
				else
				{
					Parent root = FXMLLoader.load(getClass().getResource("ProdekanScreen.fxml"));
					Scene scene = new Scene(root);
					Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					//primaryStage.setResizable(true);
					primaryStage.show();
				}
	
			}
			
		}
		
		if(!exists)
		{
			txtUsername.clear();
			txtPassword.clear();
			lblStatus.setText("Login failed, please try again");
		}
		
	}
	
}