package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Nastavnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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

	public void close(ActionEvent event) throws Exception {
		System.exit(0);
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