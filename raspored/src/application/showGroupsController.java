package application;

import java.net.URL;
import java.util.ResourceBundle;

import entiteti.Grupa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showGroupsController implements Initializable {
	
	@FXML
	private TableView<Grupa> table;
	@FXML
	private TableColumn<Grupa,String> teacher;
	@FXML
	private TableColumn<Grupa,String> subject;
	@FXML
	private TableColumn<Grupa,String> type;
	@FXML
	private TableColumn<Grupa,String> students;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Grupa> temp = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp.add((Grupa)e);
		type.setCellValueFactory(new PropertyValueFactory<Grupa, String>("tipgrupe"));
		teacher.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imeNastavnika"));
		students.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imenaStudenata"));
		//subject.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imePredmeta"));
		table.setItems(temp);
	}

}
