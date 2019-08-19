package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Student;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudentController implements Initializable {

	@FXML
	private Label usr, email, brIndex, orientation;

	public static List<?> temp_list;
	public static Student student;

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

	public void openSched(ActionEvent event) throws Exception {
		show(event, "/fxml_files/Schedule.fxml", "Schedule");
	}

	public void logout(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/WelcometoFET.fxml"));
		Scene scene = new Scene(root);
		((Node) (event.getSource())).getScene().getWindow().hide();
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("Login Screen");
		primaryStage.show();
	}

	// Funkcija za pokretanje bilo kojeg gui prozora
	private void show(Event event, String resurs, String title) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(resurs));
		Scene scene = new Scene(root);
		Stage primaryStage = new Stage();

		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.setTitle(title);
		primaryStage.show();
	}
}