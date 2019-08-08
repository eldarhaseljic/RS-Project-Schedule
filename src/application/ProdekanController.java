package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProdekanController implements Initializable {

	// Sluzi za prosljedjivanje informacije
	public static String Information;

	@FXML
	private Label usr;

	@FXML
	private Label email;

	@FXML
	private Label titula;
	public static List<?> temp_list;

	public void close(ActionEvent event) throws Exception {
		System.exit(0);
	}

	// Sluzi za inicijalizaciju teksta na pocetnom ekranu
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		usr.setText(MainController.trenutniKor.getIme() + " " + MainController.trenutniKor.getPrezime());
		email.setText(MainController.trenutniKor.getEmail());
		titula.setText("Prodekan");
	}

	// Sluz // IRMA de ovdje query napravi da odabere sve sale ciji naziv zgrade
	// odaberemoi za pozivanja prozora za dodavanje nove zgrade
	public void addBuilding(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/addBuildingScreen.fxml"));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("New Building");
		primaryStage.show();
	}

	// Sluzi za pokretanje prozora za brisanje zgrade ali se
	// prvo provjeri da li postoji u bazizgrada
	public void deleteBuilding(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT z FROM Zgrada z");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "Nema zapisa o zgradama u bazi!";
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
			Scene scene = new Scene(root);
			// primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Info");
			primaryStage.show();
		} else {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/deleteBuildingScreen.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Delete a Building");
			primaryStage.show();
		}
	}

	// Sluzi za pozivanja prozora za dodavanje nove zgrade
	public void addHall(ActionEvent event) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/addHallScreen.fxml"));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("New Hall");
		primaryStage.show();
	}

	public void deleteHall(ActionEvent event) throws Exception {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT z FROM Zgrada z");
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "Nema zapisa o zgradama u bazi,nema ni sala!";
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
			Scene scene = new Scene(root);
			// primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Info");
			primaryStage.show();
		} else {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/deleteHallScreen.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Delete a Hall");
			primaryStage.show();
		}
	}
}