package application;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Korisnik;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController {
	public static Korisnik trenutniKor;

	@FXML
	private Label lblStatus;

	@FXML
	private TextField txtUsername, txtPassword;

	@FXML
	CheckBox checkStudent;

	public void testStudent(MouseEvent event) throws IOException {
		if (checkStudent.isSelected()) {
			txtPassword.setDisable(true);
		} else {
			txtPassword.setDisable(false);
		}
	}

	public void LOGIN(ActionEvent event) throws Exception {

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT k FROM Korisnik k WHERE k.username = :a", Korisnik.class);
		q.setParameter("a", txtUsername.getText());
		@SuppressWarnings("unchecked")
		List<Korisnik> korisnici = q.getResultList();

		if (korisnici.isEmpty()) {
			txtUsername.clear();
			txtPassword.clear();
			lblStatus.setText("Wrong username, please try again");
		} else {
			trenutniKor = korisnici.get(0);
			Stage primaryStage = new Stage();

			if (checkStudent.isSelected()) {
				if (korisnici.get(0).isNastavnik() == false && korisnici.get(0).isProdekan() == false) {
					Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/StudentScreen.fxml"));
					Scene scene = new Scene(root);
					primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					primaryStage.setWidth(602);
					primaryStage.setHeight(395);
					primaryStage.setTitle("Welcome Student");
					primaryStage.show();
				} else {
					txtPassword.clear();
					if (korisnici.get(0).isProdekan() == true)
						lblStatus.setText("You are a Vice Dean , uncheck the checkbox");
					else
						lblStatus.setText("You are a teacher, uncheck the checkbox");
				}
			} else {
				if (korisnici.get(0).isNastavnik() == false && korisnici.get(0).isProdekan() == false) {
					txtPassword.clear();
					lblStatus.setText("You are a student, check in the checkbox");
				} else {
					if (korisnici.get(0).getPassword().equals(txtPassword.getText())) {
						if (korisnici.get(0).isNastavnik()) {
							Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/ProfessorScreen.fxml"));
							Scene scene = new Scene(root);
							primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
							primaryStage.setScene(scene);
							primaryStage.setWidth(602);
							primaryStage.setHeight(395);
							primaryStage.setTitle("Welcome Professor");
							primaryStage.show();
						} else {
							Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/ProdekanScreen.fxml"));
							Scene scene = new Scene(root);
							primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
							primaryStage.setScene(scene);
							primaryStage.setWidth(602);
							primaryStage.setHeight(395);
							primaryStage.setTitle("Welcome Vice Dean");
							primaryStage.show();
						}
					} else {
						txtPassword.clear();
						lblStatus.setText("Wrong password, please try again");
					}
				}
			}
		}
		em.close();
		emf.close();
	}
}