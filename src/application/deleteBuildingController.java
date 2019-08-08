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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

//Kontroler koji trenutno kreira pozor za dodavanje 
//nove zgrade ima i funkciju da brise zgradu ali ona nije jos razvijena
public class deleteBuildingController implements Initializable {
	
	@FXML
	private ComboBox<String> listbox;
	
	//Krajnja funkcija koja sluzi za brisanje zgrade iz baze podataka
	public void deleteBuilding(ActionEvent event) throws Exception {
		
		String naziv = listbox.getValue();
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Query q1 = em.createQuery("SELECT z FROM Zgrada z WHERE z.nazivZg = :n", Zgrada.class);
		q1.setParameter("n", naziv);
		@SuppressWarnings("unchecked")
		List<Zgrada> zgrade = q1.getResultList();
		
		for(Zgrada z : zgrade)
		{
			int id = z.getZgradaId();
			Zgrada zgrada = em.find(Zgrada.class, id);
			if(zgrada != null)
			{
				em.getTransaction().begin();
				em.remove(zgrada);
				em.getTransaction().commit();
				
				ProdekanController.Information = "Obrisali ste zgradu " + naziv + ".";
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
				Scene scene = new Scene(root);
				primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		}
		
		em.close();
		emf.close();
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
