package application;

import java.net.URL;
import java.util.ResourceBundle;

import entiteti.Zgrada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showBuildingsController implements Initializable {
	
	@FXML 
	TableView<Zgrada> table;
	@FXML
	TableColumn<Zgrada,String> title;
	@FXML
	TableColumn<Zgrada,String> address;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Zgrada> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Zgrada)e);
		
		title.setCellValueFactory(new PropertyValueFactory<Zgrada, String>("nazivZg"));
		address.setCellValueFactory(new PropertyValueFactory<Zgrada, String>("adresaZg"));
		table.setItems(temp);
	}

}
