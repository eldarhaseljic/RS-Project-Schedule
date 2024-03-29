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

//Kontroler koji trenutno kreira pozor za dodavanje 
//nove zgrade ima i funkciju da brise zgradu ali ona nije jos razvijena
public class addBuildingController {

	@FXML
	private TextField buildtitle, addr;

	@FXML
	private Label errBuild;

	// Funkcija koja u konacnici dodaje novu zgradu
	// prilikom dodavanja se ispituje da li zgrada vec postoji
	// ukoliko postoji Information polje se postavi na odgovarajucu vrijednost
	// te se ista salje InfoControlleru koji pravi novi prozor na kojem se
	// navedena informacina ispisuje
	//
	// Info se moze koristit u bilo kojem nacinu ispisivanja neke poruke na ekran
	// ali
	// je potrebno da se odgovarjuca informacija proslijedi information varijabli
	// u ovom kontroleru ili da postoji ista u kontroleri iz kojeg se informacija
	// zeli prikazati
	public void addBuilding(ActionEvent event) throws Exception {

		if (buildtitle.getText().isBlank())
			errBuild.setText("You didn't enter the title for the building.");

		else {
			boolean exists = false;
			String naziv = buildtitle.getText();
			String adresa = addr.getText();

			String nazivBaza;
			String adresaBaza;

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT z FROM Zgrada z");
			@SuppressWarnings("unchecked")
			List<Zgrada> zgrade = q.getResultList();

			// int count = zgrade.size();

			for (Zgrada zgrada : zgrade) {
				/*
				 * if (count < 1) {
				 * 
				 * ProdekanController.Information = "Nema zapisa o zgradama u bazi!"; Stage
				 * primaryStage = new Stage(); Parent root =
				 * FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml")); Scene scene
				 * = new Scene(root); primaryStage = (Stage) ((Node)
				 * event.getSource()).getScene().getWindow(); primaryStage.setScene(scene);
				 * primaryStage.show(); }
				 */

				nazivBaza = zgrada.getNazivZg();
				adresaBaza = zgrada.getAdresaZg();

				if (nazivBaza.toLowerCase().equals(naziv.toLowerCase())
						&& adresaBaza.toLowerCase().equals(adresa.toLowerCase())) {
					exists = true;
					ProdekanController.Information = "The entity already exists in the database!";
					show(event);
					break;
				} else if (nazivBaza.toLowerCase().equals(naziv.toLowerCase())) {
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
