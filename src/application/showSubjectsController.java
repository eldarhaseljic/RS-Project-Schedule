package application;

import java.net.URL;
import java.util.ResourceBundle;

import entiteti.Predmet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showSubjectsController implements Initializable {

	@FXML
	TableView<Predmet> table;
	@FXML
	TableColumn<Predmet, String> title;
	@FXML
	TableColumn<Predmet, String> orientation;
	@FXML
	TableColumn<Predmet, String> teachers;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Predmet> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Predmet) e);

		title.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imePred"));
		orientation.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imeUsmjerenja"));
		teachers.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imenaProfesora"));
		table.setItems(temp);
	}

}
