package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jfoenix.controls.JFXTextField;

import entiteti.Predmet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class deleteSubjectController implements Initializable {

	@FXML
	private TableView<Predmet> table;
	@FXML
	private TableColumn<Predmet, String> subject;
	@FXML
	private JFXTextField searchField;
	@FXML
	private Label errBuild;

	public void deleteSubject(ActionEvent event) throws Exception {
		if (table.getSelectionModel().isEmpty())
			errBuild.setText("You didn't choose the subject.");

		else {
			ObservableList<Predmet> temp = table.getSelectionModel().getSelectedItems();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			for (Predmet predmet : temp) {

				Predmet p = em.find(Predmet.class, predmet.getId());
				em.getTransaction().begin();
				em.remove(p);
				em.getTransaction().commit();

				ProdekanController.Information = "You deleted subject: " + p.getImePred() + " successfully.";
				show(event);

			}

			em.close();
			emf.close();
		}

	}

	private void show(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		ObservableList<Predmet> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add(((Predmet) e));

		subject.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imePred"));

		FilteredList<Predmet> predmeti = new FilteredList<Predmet>(temp, p -> true);

		table.setItems(predmeti.sorted());

		searchField.setOnKeyReleased(keyEvent -> {
			predmeti.setPredicate(p -> p.toString().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
		});
	}

}