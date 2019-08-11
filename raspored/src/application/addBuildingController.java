package application;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Zgrada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addBuildingController {

	@FXML
	private TextField buildtitle;

	@FXML
	private TextField addr;

	@FXML
	private Label errBuild;

	public void addBuilding(ActionEvent event) throws Exception {

		if (buildtitle.getText().isBlank())
			errBuild.setText("You didn't enter the title for the building.");

		else {
			boolean exists = false;
			String naziv = buildtitle.getText().toUpperCase();
			String adresa = addr.getText().toLowerCase();

			String nazivBaza;
			String adresaBaza;

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT z FROM Zgrada z");
			@SuppressWarnings("unchecked")
			List<Zgrada> zgrade = q.getResultList();

			for (Zgrada zgrada : zgrade) {

				nazivBaza = zgrada.getNazivZg();
				adresaBaza = zgrada.getAdresaZg();

				if (nazivBaza.equals(naziv) && adresaBaza.equals(adresa)) {
					exists = true;
					ProdekanController.Information = "The entity already exists in the database!";
					show(event);
					break;
				} else if (nazivBaza.equals(naziv)) {
					exists = true;
					zgrada.setAdresaZg(adresa);
					em.getTransaction().begin();
					em.persist(zgrada);
					em.getTransaction().commit();

					ProdekanController.Information = "The entity already exists, but its address was changed!";
					show(event);
					break;
				}

			}

			if (!exists) {
				Zgrada z = new Zgrada();
				z.setNazivZg(naziv);
				z.setAdresaZg(adresa);

				em.getTransaction().begin();
				em.persist(z);
				em.getTransaction().commit();

				ProdekanController.Information = "The entity is successfully added to the database.";
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