package application;

import java.awt.Dialog;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Cas;
import entiteti.Sala;
import entiteti.Semestar;
import entiteti.Usmjerenje;
import entiteti.Zgrada;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SchedController implements Initializable{
	
	
	@FXML
	private GridPane grid;
	
	@FXML
	private ComboBox<Semestar> semestarCombo = new  ComboBox<Semestar>();
	
	@FXML
	private ComboBox<Usmjerenje> usCombo = new  ComboBox<Usmjerenje>();
	
	@FXML
	private ComboBox<Sala> salaCombo = new  ComboBox<Sala>();
	
	public ReturnClass resultData;
	public class ReturnClass{
		public Semestar semestar;
		public Usmjerenje usmjerenje;
		public Sala sala;
		public ReturnClass(Semestar s, Usmjerenje u, Sala sa) {
			semestar=s; usmjerenje = u; sala=sa;
		}
	}
	
	public SchedController() {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
		javafx.scene.control.Dialog<ReturnClass> dialog = new javafx.scene.control.Dialog<SchedController.ReturnClass>();
		dialog.setTitle("Data for schedule");
		dialog.setHeaderText("Select data for schedule");
		ButtonType loginButtonType = new ButtonType("Display schedule", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20,150,10,10));
		
		Query q1 = em.createQuery("SELECT s from Semestar s");
		List<Semestar> sem =  q1.getResultList();
		sem.add(null);
		semestarCombo.setItems(FXCollections.observableArrayList(sem).sorted());
		
		Query q2 = em.createQuery("SELECT s from Usmjerenje s");
		List<Usmjerenje> us =  q2.getResultList();
		us.add(null);
		usCombo.setItems(FXCollections.observableArrayList(us).sorted());
		
		Query q3 = em.createQuery("SELECT s from Sala s");
		List<Sala> salaL =  q3.getResultList();
		salaL.add(null);
		salaCombo.setItems(FXCollections.observableArrayList(salaL).sorted());
		
		semestarCombo.setPromptText("Select semester");
		usCombo.setPromptText("Select orientation");
		salaCombo.setPromptText("Select hall");

		grid.add(semestarCombo, 0, 0);
		grid.add(usCombo, 1, 0);
		grid.add(salaCombo, 2, 0);
		dialog.getDialogPane().setContent(grid);
		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new ReturnClass(semestarCombo.getValue(), usCombo.getValue(), salaCombo.getValue());
		    }
		    return null;
		});
		
		Optional<ReturnClass> result = dialog.showAndWait();
		resultData = result.get();
	
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();
		
	
	
	
		
		for(int l = 0; l != 6; l++) {
			String day = "Monday";
			if(l == 0)
				day ="Monday";			
			if(l == 1)
				day ="Tuesday";			
			if(l == 2)
				day ="Wednesday";			
			if(l == 3)
				day ="Thursday";			
			if(l == 4)
				day ="Friday";
			if(l == 5)
				day ="Saturday";
	

			
			Query q = em.createQuery("SELECT c FROM Cas c WHERE c.datumOdrzavanjaCasa = :n AND c.semestar = :se ", Cas.class);
			q.setParameter("n", day);
			q.setParameter("se",resultData.semestar);
			@SuppressWarnings("unchecked")
			List<Cas> casovi = q.getResultList();
			Map<Integer,Cas> temp = new HashMap<Integer,Cas>();
		
			for(Cas c : casovi) {
			boolean proceed = false;
			boolean proceed2 = false;
			for(Usmjerenje u : c.getGrupa().getPredmet().getUsmjerenja()) {
					if(resultData.usmjerenje == null || u.getImeUsmjerenja().equals( resultData.usmjerenje.getImeUsmjerenja())  ) {
						proceed = true;
					
					}
				}
				if(!proceed) continue;
				
	         if(resultData.sala==null)
	        	 proceed2=true;
	         else
	         {
	        	 if(!resultData.sala.getNazivSale().equals(c.getImeSale()))
	        		 proceed2=false;
	        	 else
	        		 proceed2=true;
	         }
	         if(!proceed2) continue;
	         
	        		 
				
				
				System.out.print(c);
				
				c.getVrijemePocetkaCasaMinuta();
				c.getvrijemePocetkaCasaSat();
				c.getVrijemeZavrsetkaCasaSat();
				c.getVrijemeZavrsetkaCasaMinuta();
				int j = 4*(c.getvrijemePocetkaCasaSat()-8)+c.getVrijemePocetkaCasaMinuta()/15;
				int k = 4*(c.getVrijemeZavrsetkaCasaSat()-8)+c.getVrijemeZavrsetkaCasaMinuta()/15;
				while(true) {
						if(j <= k) {
							temp.put(j,c);
							j++;
						} else 
							break;
					}
				}
			
			for(int i = 0;i < (20-8)*4; ++i) {
				if(temp.get(i) != null) {
					Label z = new Label("");
					Label z1 = new Label("                                 ");

					if(temp.get(i).getGrupa().getTipgrupe().equals("Auditory"))
					z1.setStyle("-fx-background-color: red");
					if(temp.get(i).getGrupa().getTipgrupe().equals("Laboratory"))
					z1.setStyle("-fx-background-color: green");
					if(temp.get(i).getGrupa().getTipgrupe().equals("Lecture"))
					z1.setStyle("-fx-background-color: blue");
					
					grid.add(z1, l, i);

					if((4*(temp.get(i).getVrijemeZavrsetkaCasaSat()-8)+
						temp.get(i).getVrijemeZavrsetkaCasaMinuta()/15)
						- 4*(temp.get(i).getvrijemePocetkaCasaSat()-8)+
						temp.get(i).getVrijemePocetkaCasaMinuta()/15 >= 2){
						
						if(4*(temp.get(i).getvrijemePocetkaCasaSat()-8)+
						temp.get(i).getVrijemePocetkaCasaMinuta()/15 == i) {
							z =  new Label(temp.get(i).getImePredmeta());
						}
						
						if(4*(temp.get(i).getvrijemePocetkaCasaSat()-8)+
							temp.get(i).getVrijemePocetkaCasaMinuta()/15 +1 == i) {
							z =  new Label(temp.get(i).getImeSale());
						}
						
						if((4*(temp.get(i).getVrijemeZavrsetkaCasaSat()-8)+
								temp.get(i).getVrijemeZavrsetkaCasaMinuta()/15) == i) {
							z =  new Label(temp.get(i).getImeNastavnika());
						}
						
					} else {
						z = new Label(temp.get(i).getImePredmeta()+" "+temp.get(i).getImeSale());
					}
					grid.add(z, l, i);
				}
				grid.add(new Label(""),l, i);	
			}
	}
		em.close();
		emf.close();
}

}
