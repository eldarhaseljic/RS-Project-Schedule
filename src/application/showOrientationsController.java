package application;

import java.net.URL;
import java.util.ResourceBundle;

import entiteti.Usmjerenje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showOrientationsController implements Initializable {
	
	@FXML 
	TableView<Usmjerenje> table;
	@FXML
	TableColumn<Usmjerenje,String> title;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Usmjerenje> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Usmjerenje)e);
		
		title.setCellValueFactory(new PropertyValueFactory<Usmjerenje, String>("imeUsmjerenja"));
		table.setItems(temp);
	}

}
