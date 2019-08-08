package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import entiteti.Zgrada;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

//Kontroler koji trenutno kreira pozor za dodavanje 
//nove zgrade ima i funkciju da brise zgradu ali ona nije jos razvijena
public class deleteBuildingController implements Initializable {
	
	@FXML
	private ComboBox<String> listbox;
	
	//Krajnja funkcija koja sluzi za brisanje zgrade iz baze podataka
	public void deleteBuilding(ActionEvent event) throws Exception {
		//Samo koristeno da vidim radil dugme i dali ce ispisati
		//ono sto odaberem
		//System.out.println(listbox.getValue());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		List<String> temp = new ArrayList<String>();
		for(Object e: ProdekanController.temp_list)
			temp.add(((Zgrada) e).getNazivZg());
		listbox.setItems(FXCollections.observableList(temp));
	}
	
}
