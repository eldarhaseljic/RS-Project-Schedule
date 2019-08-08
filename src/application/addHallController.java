package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Zgrada;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

//Kontroler koji trenutno kreira pozor za dodavanje 
//nove zgrade ima i funkciju da brise zgradu ali ona nije jos razvijena
public class addHallController implements Initializable {

	@FXML
	private ComboBox<String> buildingTitle;

	@FXML
	private TextField hallTitle;

	public void addHall(ActionEvent event) throws Exception {
		//IRMA
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT z FROM Zgrada z");
		List<?> temp_list = q.getResultList();

		List<String> temp = new ArrayList<String>();
		for (Object e : temp_list)
			temp.add(((Zgrada) e).getNazivZg());
		buildingTitle.setItems(FXCollections.observableList(temp));

	}
}
