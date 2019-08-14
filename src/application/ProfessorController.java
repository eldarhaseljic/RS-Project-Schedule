package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Cas;
import entiteti.Nastavnik;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ProfessorController implements Initializable {

	// Sluzi za prosljedjivanje informacije
	public static String Information;

	@FXML
	private Label usr;

	@FXML
	private Label email;

	@FXML
	private Label titula;
	public static List<?> temp_list;
	
	private void show(Event event, String resurs, String title) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(resurs));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle(title);
		primaryStage.show();
	}

	public void close(ActionEvent event) throws Exception {
		System.exit(0);
	}
	
	public void makeReport(ActionEvent event) throws Exception {
		
		String ime = MainController.trenutniKor.getIme() + " " + MainController.trenutniKor.getPrezime();
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT c FROM Cas c WHERE CONCAT(c.grupa.nastavnik.imeNast, ' ', c.grupa.nastavnik.prezNast) = :in", Cas.class);
		q.setParameter("in", ime);
		temp_list = q.getResultList();

		if (temp_list.size() < 1) {
			ProdekanController.Information = "There are no periods in the schedule for you!";
			show(event, "/fxml_files/Info.fxml", "Info");
		}
		
		else
			show(event, "/fxml_files/Report.fxml", "Report");
	}

	// Sluzi za inicijalizaciju teksta na pocetnom ekranu
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		usr.setText(MainController.trenutniKor.getIme() + " " + MainController.trenutniKor.getPrezime());
		email.setText(MainController.trenutniKor.getEmail());

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT n FROM Nastavnik n");
		@SuppressWarnings("unchecked")
		List<Nastavnik> nastavnici = q.getResultList();
		for (Nastavnik n : nastavnici) {
			if (MainController.trenutniKor.getIme().equals(n.getImeNast())
					&& MainController.trenutniKor.getPrezime().equals(n.getPrezNast()))
				titula.setText(n.getTitula());
		}
		em.close();
		emf.close();
	}

}