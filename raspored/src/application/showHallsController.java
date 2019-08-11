package application;

import java.net.URL;
import java.util.ResourceBundle;

import entiteti.Sala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showHallsController implements Initializable {
	
	@FXML 
	TableView<Sala> table;
	@FXML
	TableColumn<Sala,String> title;
	@FXML
	TableColumn<Sala,String> building;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Sala> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Sala)e);
		
		title.setCellValueFactory(new PropertyValueFactory<Sala, String>("nazivSale"));
		building.setCellValueFactory(new PropertyValueFactory<Sala, String>("nazivZgrade"));
		table.setItems(temp);
		
	}

}
