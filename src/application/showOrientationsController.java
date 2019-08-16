package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entiteti.Usmjerenje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showOrientationsController implements Initializable {

	@FXML
	TableView<Usmjerenje> table;
	@FXML
	TableColumn<Usmjerenje, String> title;
	@FXML
	JFXTextField searchField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Usmjerenje> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Usmjerenje) e);

		title.setCellValueFactory(new PropertyValueFactory<Usmjerenje, String>("imeUsmjerenja"));
		
		FilteredList<Usmjerenje> usmjerenja = new FilteredList<Usmjerenje>(temp,p->true);
		
		table.setItems(usmjerenja);
		
		searchField.setOnKeyReleased(keyEvent ->
		{
			usmjerenja.setPredicate(p -> p.toString().toLowerCase().contains(searchField.getText().trim()));
		});
	}

}