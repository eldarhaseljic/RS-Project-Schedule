package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Sala;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Kontroler koji trenutno kreira pozor za dodavanje 
//nove zgrade ima i funkciju da brise zgradu ali ona nije jos razvijena
public class addHallController implements Initializable {

	@FXML
	private ComboBox<String> buildingTitle;

	@FXML
	private TextField hallTitle;

	public void addHall(ActionEvent event) throws Exception {
		//IRMA
		
		String nazivZ = buildingTitle.getValue();
		String nazivS = hallTitle.getText();
		boolean exists = false;
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT z FROM Zgrada z WHERE z.nazivZg = :n", Zgrada.class);
		q.setParameter("n", nazivZ);
		@SuppressWarnings("unchecked")
		List<Zgrada> zgrade = q.getResultList();
		
		for(Zgrada z : zgrade)
		{
			Collection<Sala> sale = z.getSale();
			
			if(sale.isEmpty())
			{
				Sala s = new Sala();
				s.setNazivSale(nazivS);
				s.setZgrada(z);
				z.getSale().add(s);
				em.getTransaction().begin();
				em.persist(s);
				em.getTransaction().commit();
				
				ProdekanController.Information = "Uspjesno dodano";
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
				Scene scene = new Scene(root);
				primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
				break;
			}
			
			for(Sala s : sale)
			{
				if(s.getNazivSale().equals(nazivS))
				{
					exists = true;
					ProdekanController.Information = "Entitet vec u bazi!";
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
					Scene scene = new Scene(root);
					primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					primaryStage.setScene(scene);
					primaryStage.show();
					break;
				}	
			}
			
			if(!exists)
			{
				Sala s = new Sala();
				s.setNazivSale(nazivS);
				s.setZgrada(z);
				z.getSale().add(s);
				em.getTransaction().begin();
				em.persist(s);
				em.getTransaction().commit();
				
				ProdekanController.Information = "Uspjesno dodano";
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