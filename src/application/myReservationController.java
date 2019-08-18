package application;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import entiteti.Grupa;
import entiteti.Rezervacija;
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

public class myReservationController implements Initializable {

	@FXML
	TableView<Rezervacija> table;
	@FXML
	TableColumn<Rezervacija, String> type, time;
	@FXML
	TableColumn<Rezervacija, Sala> hall;
	@FXML
	TableColumn<Rezervacija, Grupa> group;
	@FXML
	TableColumn<Rezervacija, LocalDate> date;
	@FXML
	ChoiceBox<String> choices;
	@FXML
	JFXTextField searchField;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Rezervacija> temp = FXCollections.observableArrayList();
		for (Object e : ProfessorController.temp_list)
			temp.add((Rezervacija) e);

		type.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("tipRezervacije"));
		date.setCellValueFactory(new PropertyValueFactory<Rezervacija, LocalDate>("datumOdrzavanja"));
		time.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("vrijemeTrajanja"));
		hall.setCellValueFactory(new PropertyValueFactory<Rezervacija, Sala>("sala"));
		group.setCellValueFactory(new PropertyValueFactory<Rezervacija, Grupa>("grupa"));

		FilteredList<Rezervacija> rezervacije = new FilteredList<Rezervacija>(temp, p -> true);

		table.setItems(rezervacije.sorted());

		choices.getItems().addAll("Type", "Maintenance date", "Maintenance time", "Hall", "ID of Group");
		choices.setValue("Type");

		searchField.setOnKeyReleased(keyEvent -> {
			switch (choices.getValue()) {
			case "Type":
				rezervacije.setPredicate(
						p -> p.getTipRezervacije().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
				break;
			case "Maintenance date":
				rezervacije.setPredicate(p -> p.getDatumOdrzavanja().toString().toLowerCase()
						.contains(searchField.getText().toLowerCase().trim()));
				break;
			case "Maintenance time":
				rezervacije.setPredicate(
						p -> p.getVrijemeTrajanja().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
				break;
			case "Hall":
				rezervacije.setPredicate(p -> p.getSala().getNazivSale().toLowerCase()
						.contains(searchField.getText().toLowerCase().trim()));
				break;
			case "ID of Group":
				rezervacije.setPredicate(p -> p.getGrupa().toString().toLowerCase()
						.contains(searchField.getText().toLowerCase().trim()));
				break;
			}
		});

		choices.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				searchField.setText("");
				rezervacije.setPredicate(null);
			}
		});
	}
}
