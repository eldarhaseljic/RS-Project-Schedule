package application;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Usmjerenje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addOrientationController {
	@FXML
	private TextField orientation;

	@FXML
	private Label errBuild;

	public void addOrientation(ActionEvent event) throws Exception {

		if (orientation.getText().isBlank())
			errBuild.setText("You didn't enter name for the orientation!");

		else {
			boolean exists = false;
			String naziv = orientation.getText().toUpperCase();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT u FROM Usmjerenje u");
			@SuppressWarnings("unchecked")
			List<Usmjerenje> usmjerenja = q.getResultList();

			for (Usmjerenje usmjerenje : usmjerenja) {
				if (usmjerenje.getImeUsmjerenja().equals(naziv)) {
					exists = true;
					ProdekanController.Information = "Orientation already exists!";
					show(event);
					break;
				}
			}

			if (!exists) {
				Usmjerenje usm = new Usmjerenje();
				usm.setImeUsmjerenja(naziv);

				em.getTransaction().begin();
				em.persist(usm);
				em.getTransaction().commit();

				ProdekanController.Information = "Orientation successfuly added!";
				show(event);
			}

			em.close();
			emf.close();
		}
	}

	public void show(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
