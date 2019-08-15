package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Nastavnik;
import entiteti.Predmet;
import entiteti.Rezervacija;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class deleteReservationController implements Initializable {

	@FXML
	private ComboBox<Rezervacija> listbox;

	@FXML
	private Label errBuild;

	public void deleteReservation(ActionEvent event) throws Exception {
		if (listbox.getSelectionModel().isEmpty())
			errBuild.setText("You didn't choose the reservation.");
		else {
			

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();
			Query q1 = em.createQuery("SELECT s FROM Rezervacija s WHERE s = :n", Rezervacija.class);
			q1.setParameter("n", listbox.getValue());
			@SuppressWarnings("unchecked")
			List<Rezervacija> rs = q1.getResultList();

			for (Rezervacija r : rs) {
				em.getTransaction().begin();
				em.remove(r);
				em.getTransaction().commit();

				ProdekanController.Information = "You deleted rezervacija: " + r + " successfully.";
				show(event);

			}
		
			em.close();
			emf.close();
		}

	}

	private void show(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT r FROM Rezervacija r where r.nastavnik = :n").setParameter("n",ProfessorController.nastavnik);
		@SuppressWarnings("unchecked")
		List<Rezervacija> nastavnici = q.getResultList();
		
		listbox.setItems(FXCollections.observableList(nastavnici).sorted());
		
		em.close();
		emf.close();
	}

}
