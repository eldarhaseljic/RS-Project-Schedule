package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jfoenix.controls.JFXTextField;

import entiteti.Cas;
import entiteti.Grupa;
import entiteti.Sala;
import entiteti.Semestar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class createPeriodController implements Initializable {
	
	@FXML
	private TableView<Grupa> table;
	@FXML
	private TableColumn<Grupa,String> type;
	@FXML
	private TableColumn<Grupa,String> teacher;
	@FXML
	private TableColumn<Grupa,String> subject;
	@FXML
	private TableColumn<Grupa,String> students;
	@FXML
	private ComboBox<String> danusedmici;
	@FXML
	private ComboBox<Semestar> semester;
	@FXML
	private ComboBox<Sala> sala;
	@FXML
	private JFXTextField start;
	@FXML
	private JFXTextField end;
	@FXML
	private Slider satistart;
	@FXML
	private Slider minutestart;
	@FXML
	private Slider satiend;
	@FXML
	private Slider minuteend;
	@FXML
	private Label errPeriod = new Label();
	
	public void createPeriod(ActionEvent event) throws Exception {
		long satipoc = Math.round(satistart.getValue());
		long minutepoc = Math.round(minutestart.getValue());
		long satikraj = Math.round(satiend.getValue());
		long minutekraj = Math.round(minuteend.getValue());
		
		errPeriod.setText("");
		
		if(table.getSelectionModel().isEmpty() || danusedmici.getSelectionModel().isEmpty() ||
		   semester.getSelectionModel().isEmpty() || sala.getSelectionModel().isEmpty() || satipoc > satikraj ||
		   (satipoc == satikraj && minutepoc > minutekraj)) {
			if(table.getSelectionModel().isEmpty())
				errPeriod.setText("You didn't chose Group.");
			
			else if(danusedmici.getSelectionModel().isEmpty())
				errPeriod.setText("You didn't set day.");
			
			else if(semester.getSelectionModel().isEmpty())
				errPeriod.setText("You didn't chose semester.");
			
			else if(sala.getSelectionModel().isEmpty())
				errPeriod.setText("You didn't chose classroom.");
			
			else
				errPeriod.setText("Time you chose is invalid.");
		}
		else {
			
			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
			int row = pos.getRow();

			// Item here is the table view type:
			Grupa item = table.getItems().get(row);
			
			Query q = em.createQuery("SELECT g FROM GrupaStudenata g WHERE g.idGrupe = :idg");
			q.setParameter("idg", item.getId());
			Grupa grupa = (Grupa) q.getSingleResult();		
			
			Query q1 = em.createQuery(
					"SELECT c FROM Cas c JOIN c.grupa g "
					+ "WHERE g.nastavnik = :grnast AND c.datumOdrzavanjaCasa = :dan "
					+ "AND (:pocvrijeme BETWEEN c.vrijemePocetkaCasaSat*60+c.vrijemePocetkaCasaMinuta "
					+ "AND c.vrijemeZavrsetkaCasaSat*60+c.vrijemeZavrsetkaCasaMinuta OR "
					+ ":krajvrijeme BETWEEN c.vrijemePocetkaCasaSat*60+c.vrijemePocetkaCasaMinuta "  
					+"AND c.vrijemeZavrsetkaCasaSat*60+c.vrijemeZavrsetkaCasaMinuta)");
			
			q1.setParameter("grnast", grupa.getNastavnik());
			q1.setParameter("dan", danusedmici.getValue());
			q1.setParameter("pocvrijeme", satipoc*60+minutepoc);
			q1.setParameter("krajvrijeme", satikraj*60+minutekraj);
			
			List<Cas> casoviSaNast=q1.getResultList();
			if(!casoviSaNast.isEmpty()) {
				errPeriod.setText("Teacher is busy at that time.");
				return;	
			}

			Query q3 = em.createQuery("SELECT c FROM Cas c WHERE c.sala = :sala AND "
					+ "(:pocvrijeme BETWEEN c.vrijemePocetkaCasaSat*60+c.vrijemePocetkaCasaMinuta "
					+ "AND c.vrijemeZavrsetkaCasaSat*60+c.vrijemeZavrsetkaCasaMinuta OR "
					+ ":krajvrijeme BETWEEN c.vrijemePocetkaCasaSat*60+c.vrijemePocetkaCasaMinuta "
					+ "AND c.vrijemeZavrsetkaCasaSat*60+c.vrijemeZavrsetkaCasaMinuta)"
					+ " AND c.datumOdrzavanjaCasa = :dan");
			
			q3.setParameter("sala", sala.getValue());
			q3.setParameter("pocvrijeme", satipoc*60+minutepoc);
			q3.setParameter("krajvrijeme", satikraj*60+minutekraj);
			q3.setParameter("dan", danusedmici.getValue());

			List<Cas> casoviSaSala=q1.getResultList();
			if(!casoviSaSala.isEmpty()) {
				errPeriod.setText("Classroom is busy at that time.");
				return;	
			}
			
			Cas cas = new Cas();
			cas.setDatumOdrzavanjaCasa(danusedmici.getValue());
			cas.setVrijemePocetkaCasaMinuta((int)minutepoc);
			cas.setvrijemePocetkaCasaSat((int)satipoc);
			cas.setVrijemeZavrsetkaCasaMinuta((int)minutekraj);
			cas.setVrijemeZavrsetkaCasaSat((int)satikraj);
			cas.setSala(sala.getValue());
			cas.setGrupa(grupa);
			cas.setSemestar(semester.getValue());
			
			em.getTransaction().begin();
			em.persist(cas);
			em.getTransaction().commit();
		}
			
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ObservableList<Grupa> temp1 = FXCollections.observableArrayList();
		for (Object e : ProdekanController.temp_list)
			temp1.add((Grupa)e);
		type.setCellValueFactory(new PropertyValueFactory<Grupa, String>("tipgrupe"));
		teacher.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imeNastavnika"));
		students.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imenaStudenata"));
		subject.setCellValueFactory(new PropertyValueFactory<Grupa, String>("imePredmeta"));
		table.setItems(temp1);
		
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Semestar s");
		List<?> temp_list = q.getResultList();
		
		List<Semestar> temp2 = new ArrayList<Semestar>();
		for (Object e : temp_list)
			temp2.add(((Semestar)e));
		semester.setItems(FXCollections.observableList(temp2));
		
		List<String> temp3 = new ArrayList<String>();
		temp3.add("Monday");
		temp3.add("Tuesday");
		temp3.add("Wednesday");
		temp3.add("Thursday");
		temp3.add("Friday");
		temp3.add("Saturday");
		danusedmici.setItems(FXCollections.observableList(temp3));
		
		Query q1 = em.createQuery("SELECT s FROM Sala s");
		List<?> temp_list1 = q1.getResultList();
		
		List<Sala> temp4 = new ArrayList<Sala>();
		for (Object e : temp_list1)
			temp4.add(((Sala)e));
		sala.setItems(FXCollections.observableList(temp4));
		
		em.close();
		emf.close();
	}
	
	@SuppressWarnings("unused")
	private void show(Event event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
