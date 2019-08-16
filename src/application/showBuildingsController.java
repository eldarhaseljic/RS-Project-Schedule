package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entiteti.Zgrada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showBuildingsController implements Initializable {

	@FXML
	TableView<Zgrada> table;
	@FXML
	TableColumn<Zgrada, String> title;
	@FXML
	TableColumn<Zgrada, String> address;
	@FXML
	ChoiceBox<String> choices;
	@FXML
	JFXTextField searchField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Zgrada> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Zgrada)e);

		title.setCellValueFactory(new PropertyValueFactory<Zgrada, String>("nazivZg"));
		address.setCellValueFactory(new PropertyValueFactory<Zgrada, String>("adresaZg"));
		
		FilteredList<Zgrada> zgrade = new FilteredList<Zgrada>(temp,p->true);
		
		table.setItems(zgrade);
		
		choices.getItems().addAll("Title","Address");
		
		searchField.setOnKeyReleased(keyEvent ->
		{	
			
			switch(choices.getValue())
			{
			case "Title":
				zgrade.setPredicate(p -> p.getNazivZg().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Address":
				zgrade.setPredicate(p -> p.getAdresaZg().toLowerCase().contains(searchField.getText().trim()));
				break;
			}
		});
		
		choices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                searchField.setText("");
                zgrade.setPredicate(null);
            }
        });
	}

}