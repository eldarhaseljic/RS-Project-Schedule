package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Zgrada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProdekanController implements Initializable{
	
	@FXML
	private Label usr;
	
	@FXML
	private Label email;
	
	@FXML
	private Label titula ;
	
	public void init(ActionEvent event) throws Exception {
	}

	public void close(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	//Sluzi za inicijalizaciju teksta na pocetnom ekranu
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		usr.setText(MainController.trenutniKor.getIme()+" "+MainController.trenutniKor.getPrezime());
		email.setText(MainController.trenutniKor.getEmail());
		titula.setText("Prodekan");
	}
	
	//Sluzi za pozivanja prozora za dodavanje nove zgrade
	public void addBuilding(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("addBuildingScreen.fxml"));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("New Building");
		primaryStage.show();
	}
	
	//Sluzi za pokretanje prozora za brisanje zgrade ali se 
	//prvo provjeri da li postoji u bazizgrada
	public void deleteBuilding(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT z FROM Zgrada z");
		@SuppressWarnings("unchecked")
		List<Zgrada> zgrade = q.getResultList();
		
		if(zgrade.size() < 1)
			{
				BuildingController.Information = "Nema zapisa o zgradama u bazi!";
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("Info.fxml"));
				Scene scene = new Scene(root);
				//primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		else 
		{
			Parent root = FXMLLoader.load(getClass().getResource("deleteBuildingScreen.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Delete a Building");
			primaryStage.show();
		}
	}
	
}