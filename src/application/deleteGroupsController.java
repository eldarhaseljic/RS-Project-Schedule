package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.jfoenix.controls.JFXTextField;

import entiteti.Grupa;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class deleteGroupsController implements Initializable {

	@FXML
	private TableView<Grupa> table;
	@FXML
	private TableColumn<Grupa, String> teacher;
	@FXML
	private TableColumn<Grupa, String> subject;
	@FXML
	private TableColumn<Grupa, String> type;
	@FXML
	private TableColumn<Grupa, String> students;
	@FXML
	private ChoiceBox<String> choices;
	@FXML
	private JFXTextField searchField;
	@FXML
	private Label errGroup;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Grupa> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Grupa) e);
		
		FilteredList<Grupa> grupe = new FilteredList<Grupa>(temp,p->true);
		
		type.setCellValueFactory(new PropertyValueFactory<Grupa, String>("tipgrupe"));
		teacher.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imeNastavnika"));
		students.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imenaStudenata"));
		subject.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imePredmeta"));
		
		table.setItems(grupe);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		choices.getItems().addAll("Type","Subject","Teacher","Students");
		
		searchField.setOnKeyReleased(keyEvent ->
		{
			switch(choices.getValue())
			{
			case "Students":
				grupe.setPredicate(p -> p.getImenaStudenata().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Subject":
				grupe.setPredicate(p -> p.getImePredmeta().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Teacher":
				grupe.setPredicate(p -> p.getImeNastavnika().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Type":
				grupe.setPredicate(p -> p.getTipgrupe().toLowerCase().contains(searchField.getText().trim()));
				break;
			}
		});
	}

	public void deleteGroup(ActionEvent event) throws Exception {
		if (table.getSelectionModel().isEmpty())
			errGroup.setText("You didn't choose any group.");
		else {
			ObservableList<Grupa> temp = table.getSelectionModel().getSelectedItems();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			for (Grupa e : temp) {
				Grupa temp_gr = em.find(Grupa.class, e.getId());
				em.getTransaction().begin();
				em.remove(temp_gr);
				em.getTransaction().commit();
			}
			String x;
			if (temp.size() == 1)
				x = " group.";
			else
				x = " groups.";

			ProdekanController.Information = "You deleted " + temp.size() + x;
			show(event);
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
