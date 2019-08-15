package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StudentController implements Initializable {

	@FXML
	private Label usr;

	@FXML
	private Label email;

	@FXML
	private Label brIndex;
	@FXML
	private Label orientation;

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

		Query q = em.createQuery("SELECT n FROM Student n");
		@SuppressWarnings("unchecked")
		List<Student> studenti = q.getResultList();
		// int i = 0;
		for (Student n : studenti) {
			/*
			 * if(i==0) { System.out.println(MainController.trenutniKor.getIme());
			 * System.out.println(n.getIme());
			 * System.out.println(MainController.trenutniKor.getPrezime());
			 * System.out.println(n.getPrezStud()); ++i; }
			 */
			if (MainController.trenutniKor.getIme().equals(n.getImeStud())
					&& MainController.trenutniKor.getPrezime().equals(n.getPrezStud())) {
				// ako cemo index
				int index = n.getIDStud();
				brIndex.setText("" + index + "");

				// ako cemo smijer
				orientation.setText(n.getUsmjerenje());
			}
		}
		em.close();
		emf.close();
	}

}