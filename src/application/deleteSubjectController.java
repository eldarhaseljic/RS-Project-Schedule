package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Predmet;
import entiteti.Semestar;
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

public class deleteSubjectController implements Initializable {

	@FXML
	private ComboBox<Predmet> listbox;

	@FXML
	private Label errBuild;

	public void deleteSubject(ActionEvent event) throws Exception {
		if (listbox.getSelectionModel().isEmpty())
			errBuild.setText("You didn't choose the subject.");
		else {
			Predmet naziv = listbox.getValue();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q1 = em.createQuery("SELECT s FROM Predmet s WHERE s.IdPredmeta = :n", Predmet.class);
			q1.setParameter("n", naziv.getId());
			@SuppressWarnings("unchecked")
			List<Predmet> predmeti = q1.getResultList();

			for (Predmet predmet : predmeti) {
					em.getTransaction().begin();
					em.remove(predmet);
					em.getTransaction().commit();

					ProdekanController.Information = "You deleted subject: " + naziv.getImePred() + " successfully.";
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
					Scene scene = new Scene(root);
					primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					primaryStage.show();
					
					
				}
			em.close();
			emf.close();
			}

		
		}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		List<Predmet> temp = new ArrayList<>();
		for (Object e : ProdekanController.temp_list)
			temp.add(((Predmet) e));
		listbox.setItems(FXCollections.observableList(temp).sorted());
	}

}
