package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entiteti.Sala;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showHallsController implements Initializable {

	@FXML
	TableView<Sala> table;
	@FXML
	TableColumn<Sala, String> title;
	@FXML
	TableColumn<Sala, String> building;
	@FXML
	ChoiceBox<String> choices;
	@FXML
	JFXTextField searchField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Sala> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Sala) e);

		title.setCellValueFactory(new PropertyValueFactory<Sala, String>("nazivSale"));
		building.setCellValueFactory(new PropertyValueFactory<Sala, String>("nazivZgrade"));
		
		FilteredList<Sala> sale = new FilteredList<Sala>(temp,p->true);
		
		table.setItems(sale);
		
		choices.getItems().addAll("Title","Building");
		
		searchField.setOnKeyReleased(keyEvent ->
		{
			switch(choices.getValue())
			{
			case "Title":
				sale.setPredicate(p -> p.getNazivSale().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Building":
				sale.setPredicate(p -> p.getNazivZgrade().toLowerCase().contains(searchField.getText().trim()));
				break;
			}
		});
		
		choices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                searchField.setText("");
                sale.setPredicate(null);
            }
        });
	}

}