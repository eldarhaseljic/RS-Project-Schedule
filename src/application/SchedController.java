package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Cas;
import entiteti.Semestar;
import entiteti.Zgrada;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SchedController implements Initializable{
	@FXML
	private GridPane grid;
	
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
			Query q1 = em.createQuery("SELECT s from Semestar s");
			Semestar sem = (Semestar) q1.getSingleResult();
			
			Query q = em.createQuery("SELECT c FROM Cas c WHERE c.datumOdrzavanjaCasa = :n AND c.semestar = :se", Cas.class);
			q.setParameter("n", day);
			q.setParameter("se", sem);
			@SuppressWarnings("unchecked")
			List<Cas> casovi = q.getResultList();
			Map<Integer,Cas> temp = new HashMap<Integer,Cas>();
			for(Cas c : casovi) {
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
}

}
