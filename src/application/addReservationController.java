package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Grupa;
import entiteti.Nastavnik;
import entiteti.Rezervacija;
import entiteti.Sala;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

//Kontroler koji trenutno kreira pozor za dodavanje 
//nove zgrade ima i funkciju da brise zgradu ali ona nije jos razvijena
public class addReservationController implements Initializable {

	@FXML
	private ComboBox<Sala> classCombo = new ComboBox<Sala>();

	@FXML
	private ComboBox<String> tipCombo = new ComboBox<String>();

	@FXML
	private ComboBox<Grupa> groupCombo = new ComboBox<Grupa>();

	@FXML
	private Slider satistart = new Slider();

	@FXML
	private Slider minutestart = new Slider();

	@FXML
	private Slider satiend = new Slider();

	@FXML
	private Slider minuteend = new Slider();

	@FXML
	private DatePicker date = new DatePicker();

	@SuppressWarnings("rawtypes")
	@FXML
	private TableView tabela = new TableView<Object>();
	@FXML
	private TableColumn<Rezervacija, LocalDate> DatumRezervacije = new TableColumn<Rezervacija, LocalDate>(
			"Datum rezervacije");
	@FXML
	private TableColumn<Rezervacija, Nastavnik> nastavnikRezervisan = new TableColumn<Rezervacija, Nastavnik>(
			"Rezervisao");
	@FXML
	private TableColumn<Rezervacija, String> vrijemeRezervacije = new TableColumn<Rezervacija, String>("Vrijeme");
	@FXML
	private TableColumn<Rezervacija, String> tipRezervacije = new TableColumn<Rezervacija, String>("Tip");
	@FXML
	private TableColumn<Rezervacija, Grupa> grupaStudenata = new TableColumn<Rezervacija, Grupa>("Grupa");

	@SuppressWarnings("unused")
	private List<Rezervacija> rezervacije;

	public void addReservation(ActionEvent event) throws Exception {

		if (date.getValue().isBefore(LocalDate.now())) {
			System.out.println(" Samo buduci datumi su dozvoljeni. ");
			return;
		}

		LocalDate dateOfReservation = date.getValue();
		long satipoc = Math.round(satistart.getValue());
		long minutepoc = Math.round(minutestart.getValue());
		long satikraj = Math.round(satiend.getValue());
		long minutekraj = Math.round(minuteend.getValue());

		if (satipoc > satikraj || (satipoc == satikraj && minutepoc > minutekraj)) {
			System.out.println(" Vrijeme nije validno ");
			return;
		}

		String type = tipCombo.getValue();
		Sala sala = classCombo.getValue();

		if (mogucaRezervacija(dateOfReservation, satipoc, minutepoc, satikraj, minutekraj)) {
			if (type.contentEquals("Nadoknada")) {
				addReservationForGroup(sala, type, groupCombo.getValue(), dateOfReservation, (int) satipoc,
						(int) minutepoc, (int) satikraj, (int) minutekraj);
			} else {
				addReservationForNonGroup(sala, type, dateOfReservation, (int) satipoc, (int) minutepoc, (int) satikraj,
						(int) minutekraj);
			}
		}

	}

	boolean mogucaRezervacija(LocalDate date, long hs, long ms, long hend, long mend) {
		return true;

		// PROVJERI KOLIZIJU SA CASOVIMA
	}

	void addReservationForGroup(Sala sala, String type, Grupa grupa, LocalDate date, int hs, int ms, int hend,
			int mend) {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Rezervacija rez = new Rezervacija();
		rez.setSala(sala);
		rez.setTipRezervacije(type);
		rez.setGrupa(grupa);
		rez.setDatumOdrzavanja(date);
		rez.setVrijemePocetkaCasaMinuta(ms);
		rez.setvrijemePocetkaCasaSat(hs);
		rez.setVrijemeZavrsetkaCasaMinuta(mend);
		rez.setVrijemeZavrsetkaCasaSat(hend);
		rez.setNastavnik(ProfessorController.nastavnik);

		em.getTransaction().begin();
		em.persist(rez);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}

	void addReservationForNonGroup(Sala sala, String type, LocalDate date, int hs, int ms, int hend, int mend) {
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Rezervacija rez = new Rezervacija();
		rez.setSala(sala);
		rez.setTipRezervacije(type);
		rez.setGrupa(null);
		rez.setDatumOdrzavanja(date);
		rez.setVrijemePocetkaCasaMinuta(ms);
		rez.setvrijemePocetkaCasaSat(hs);
		rez.setVrijemeZavrsetkaCasaMinuta(mend);
		rez.setVrijemeZavrsetkaCasaSat(hend);
		rez.setNastavnik(ProfessorController.nastavnik);

		em.getTransaction().begin();
		em.persist(rez);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// TODO Auto-generated method stub
		System.out.println(ProfessorController.nastavnik);

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Sala s");
		List<Sala> temp_list = q.getResultList();
		classCombo.setItems(FXCollections.observableArrayList(temp_list).sorted());

		Query q1 = em.createQuery("SELECT g FROM GrupaStudenata g");
		List<Grupa> temp_list1 = q1.getResultList();

		groupCombo.setItems(FXCollections.observableArrayList(temp_list1).sorted());
		groupCombo.setDisable(true);

		Query q2 = em.createQuery("SELECT g FROM Rezervacija g");
		rezervacije = q2.getResultList();

		tipCombo.setItems(FXCollections.observableArrayList(new String("Nadoknada"), new String("Seminar"),
				new String("Odbrana")));

		tipCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, String t, String t1) {
				if (t1.equals("Nadoknada")) {
					groupCombo.setDisable(false);
				} else {
					groupCombo.setDisable(true);
				}
			}
		});

		tabela.getColumns().addAll(DatumRezervacije, nastavnikRezervisan, // vrijemeRezervacije,
				tipRezervacije, grupaStudenata);

		DatumRezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacija, LocalDate>("datumOdrzavanja"));
		nastavnikRezervisan.setCellValueFactory(new PropertyValueFactory<Rezervacija, Nastavnik>("nastavnik"));
		tipRezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("tipRezervacije"));
		grupaStudenata.setCellValueFactory(new PropertyValueFactory<Rezervacija, Grupa>("grupa"));

		classCombo.valueProperty().addListener(new ChangeListener<Sala>() {

			@Override
			public void changed(ObservableValue<? extends Sala> arg0, Sala arg1, Sala arg2) {

				String PERSISTENCE_UNIT_NAME = "raspored";
				EntityManagerFactory emf;
				emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
				EntityManager em = emf.createEntityManager();
				Query q1 = em.createQuery("SELECT g FROM Rezervacija g WHERE g.sala = :x").setParameter("x", arg2);
				List<Rezervacija> temp_list3 = q1.getResultList();

				ObservableList<Rezervacija> data = FXCollections.observableArrayList(temp_list3).sorted();
				tabela.setItems(data);

				em.close();
				emf.close();
			}

		});
		em.close();
		emf.close();
	}

	public void createReservation() {

	}

	public void show(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
