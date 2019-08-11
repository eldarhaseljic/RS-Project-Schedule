package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

public class deleteSemesterController implements Initializable {

	@FXML
	private ComboBox<String> listbox;

	@FXML
	private Label errBuild;

	public void deleteSemester(ActionEvent event) throws Exception {
		if (listbox.getSelectionModel().isEmpty())
			errBuild.setText("You didn't choose the semester.");
		else {
			String naziv = listbox.getValue();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q1 = em.createQuery("SELECT s FROM Semestar s WHERE s.oznakaSemestra = :n", Semestar.class);
			q1.setParameter("n", naziv);
			@SuppressWarnings("unchecked")
			List<Semestar> semestri = q1.getResultList();

			for (Semestar semestar : semestri) {
				int id = semestar.getIDSemestra();
				Semestar us = em.find(Semestar.class, id);
				if (us != null) {
					em.getTransaction().begin();
					em.remove(us);
					em.getTransaction().commit();

					ProdekanController.Information = "You deleted semester: " + naziv + " successfully.";
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
					Scene scene = new Scene(root);
					primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			}

			em.close();
			emf.close();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		List<String> temp = new ArrayList<String>();
		for (Object e : ProdekanController.temp_list)
			temp.add(((Semestar) e).getOznakaSemestra());
		listbox.setItems(FXCollections.observableList(temp));
	}

}