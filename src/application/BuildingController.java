package application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Zgrada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BuildingController {
	
	@FXML
	private TextField buildtitle;
	
	@FXML
	private TextField addr;
	
	public static String Information;
	
	public void addBuilding(ActionEvent event) throws Exception {
		
		boolean exists = false;
		
		String naziv = buildtitle.getText().toUpperCase();
		String adresa = addr.getText().toLowerCase();
		
		String nazivBaza;
		String adresaBaza;
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		Query q = em.createQuery("SELECT z FROM Zgrada z");
		List<Zgrada> zgrade = q.getResultList();
		
		int count = zgrade.size();
		
		for(Zgrada zgrada : zgrade)
		{
			if(count < 1)
			{
				Information = "Nema zapisa o zgradama u bazi!";
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("Info.fxml"));
				Scene scene = new Scene(root);
				primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			
			nazivBaza = zgrada.getNazivZg();
			adresaBaza = zgrada.getAdresaZg();
			
			if(nazivBaza.equals(naziv) && adresaBaza.equals(adresa))
			{
				exists = true;
				Information = "Entitet vec u bazi!";
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("Info.fxml"));
				Scene scene = new Scene(root);
				primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
				break;
			}
			
		}
		
		if(!exists)
		{
			Zgrada z = new Zgrada();
			z.setNazivZg(naziv);
			z.setAdresaZg(adresa);
			
			em.getTransaction().begin();
			em.persist(z);
			em.getTransaction().commit();
			
			Information = "Uspjesno dodano";
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("Info.fxml"));
			Scene scene = new Scene(root);
			primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		em.close();
		emf.close();
	}
	
}
