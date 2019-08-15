package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Cas;
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

public class deletePeriodController implements Initializable {

	@FXML
	private ComboBox<Cas> listbox;

	@FXML
	private Label errBuild;

	public void deletePeriod(ActionEvent event) throws Exception {
		if (listbox.getSelectionModel().isEmpty())
			errBuild.setText("You didn't choose the period.");
		else {
			Cas naziv = listbox.getValue();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q1 = em.createQuery("SELECT s FROM Cas s WHERE s.IdCasa = :n", Cas.class);
			q1.setParameter("n", naziv.getId());
			@SuppressWarnings("unchecked")
			List<Cas> casovi = q1.getResultList();

			for (Cas cas : casovi) {
				em.getTransaction().begin();
				em.remove(cas);
				em.getTransaction().commit();

				ProdekanController.Information = "You deleted period successfully.";
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

		List<Cas> temp = new ArrayList<>();
		for (Object e : ProdekanController.temp_list)
			temp.add(((Cas) e));
		listbox.setItems(FXCollections.observableList(temp).sorted());
	}

}