package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entiteti.Grupa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showGroupsController implements Initializable {

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
	ChoiceBox<String> choices;
	@FXML
	JFXTextField searchField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Grupa> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Grupa) e);
		
		type.setCellValueFactory(new PropertyValueFactory<Grupa, String>("tipgrupe"));
		teacher.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imeNastavnika"));
		students.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imenaStudenata"));
		subject.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imePredmeta"));
		
		FilteredList<Grupa> grupe = new FilteredList<Grupa>(temp,p->true);
		
		table.setItems(grupe);
		
		choices.getItems().addAll("Teacher","Subject","Students","Type");
		
		searchField.setOnKeyReleased(keyEvent ->
		{
			switch(choices.getValue())
			{
			case "Students":
				grupe.setPredicate(p -> p.getImenaStudenata().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Teacher":
				grupe.setPredicate(p -> p.getImeNastavnika().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Type":
				grupe.setPredicate(p -> p.getTipgrupe().toLowerCase().contains(searchField.getText().trim()));
				break;
			case "Subject":
				grupe.setPredicate(p -> p.getImePredmeta().toLowerCase().contains(searchField.getText().trim()));
				break;
			}
		});
		
		choices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {
            if (newVal != null)
            {
                searchField.setText("");
                grupe.setPredicate(null);
            }
        });
	}

}
