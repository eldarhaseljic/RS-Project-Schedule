package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entiteti.Predmet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showSubjectsController implements Initializable {

	@FXML
	TableView<Predmet> table;
	@FXML
	TableColumn<Predmet, String> title, orientation, teachers;
	@FXML
	ChoiceBox<String> choices;
	@FXML
	JFXTextField searchField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Predmet> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Predmet) e);

		title.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imePred"));
		orientation.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imeUsmjerenja"));
		teachers.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imenaProfesora"));

		FilteredList<Predmet> predmeti = new FilteredList<Predmet>(temp.sorted(), p -> true);

		table.setItems(predmeti.sorted());

		choices.getItems().addAll("Title", "Orientation", "Teacher");
		choices.setValue("Title");

		searchField.setOnKeyReleased(keyEvent -> {
			switch (choices.getValue()) {
			case "Title":
				predmeti.setPredicate(
						p -> p.toString().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
				break;
			case "Teacher":
				predmeti.setPredicate(
						p -> p.getImenaProfesora().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
				break;
			case "Orientation":
				predmeti.setPredicate(
						p -> p.getImeUsmjerenja().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
				break;
			}
		});

		choices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				searchField.setText("");
				predmeti.setPredicate(null);
			}
		});
	}
}
