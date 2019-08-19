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

import entiteti.Cas;
import entiteti.Grupa;
import entiteti.Nastavnik;
import entiteti.Rezervacija;
import entiteti.Sala;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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

	@FXML
	private Label errHall = new Label();

	@FXML
	private Label errType = new Label();

	@FXML
	private Label errDate = new Label();

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

	private boolean empty;

	public void addReservation(ActionEvent event) throws Exception {

		if (classCombo.getSelectionModel().isEmpty() || date.getValue() == null
				|| tipCombo.getSelectionModel().isEmpty()) {
			if (classCombo.getSelectionModel().isEmpty()) {
				errHall.setText("You didn't chose hall.");
			} else {
				errHall.setText("");
			}

			if (date.getValue() == null) {
				errDate.setText("You didn't set day.");
			} else {
				errDate.setText("");
			}

			if (tipCombo.getSelectionModel().isEmpty()) {
				errType.setText("You didn't chose type.");
			} else {
				errType.setText("");
			}

		} else {
			if (tipCombo.getSelectionModel().getSelectedItem().equals("Nadoknada")
					&& groupCombo.getSelectionModel().isEmpty()) {
				errType.setText("You didn't chose the group.");
			} else {
				errType.setText("");

				if (date.getValue().isBefore(LocalDate.now())) {
					errDate.setText(" Only future dates are allowed ");
				} else {
					errDate.setText("");

					LocalDate dateOfReservation = date.getValue();
					long satipoc = Math.round(satistart.getValue());
					long minutepoc = Math.round(minutestart.getValue());
					long satikraj = Math.round(satiend.getValue());
					long minutekraj = Math.round(minuteend.getValue());

					if (satipoc > satikraj || (satipoc == satikraj && minutepoc > minutekraj)) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Time alert");
						alert.setHeaderText("Invalid time of reservation");
						alert.setContentText("Adjust time of reservation.");
						return;
					}

					String type = tipCombo.getValue();
					Sala sala = classCombo.getValue();

					if (mogucaRezervacija(dateOfReservation, satipoc, minutepoc, satikraj, minutekraj, sala)) {
						if (type.equals("Nadoknada")) {
							addReservationForGroup(sala, type, groupCombo.getValue(), dateOfReservation, (int) satipoc,
									(int) minutepoc, (int) satikraj, (int) minutekraj);
						} else {
							addReservationForNonGroup(sala, type, dateOfReservation, (int) satipoc, (int) minutepoc,
									(int) satikraj, (int) minutekraj);
						}
						ProdekanController.Information = "Reservation was successfuly placed";
						show(event);
					}
				}
			}
		}
	}

	boolean mogucaRezervacija(LocalDate date, long hs, long ms, long hend, long mend, Sala sala) {

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q1 = em.createQuery("SELECT g FROM Rezervacija g WHERE g.sala = :x ").setParameter("x", sala);
		@SuppressWarnings("unchecked")
		List<Rezervacija> temp_list1 = q1.getResultList();

		Query q2 = em.createQuery("SELECT g FROM Cas g WHERE g.sala = :x ").setParameter("x", sala);
		@SuppressWarnings("unchecked")
		List<Cas> temp_list2 = q2.getResultList();

		long startRez = hs * 60 + ms;
		long endRez = hend * 60 + mend;
		for (Rezervacija rez : temp_list1) {

			if (date.isEqual(rez.getDatumOdrzavanja())) {
				long startRez2 = rez.getvrijemePocetkaCasaSat() * 60 + rez.getVrijemePocetkaCasaMinuta();
				long endRez2 = rez.getVrijemeZavrsetkaCasaSat() * 60 + rez.getVrijemeZavrsetkaCasaMinuta();
				if (startRez < endRez2 && startRez2 < endRez) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Time alert");
					alert.setHeaderText("Reservation overlaps with another one");
					alert.setContentText("Adjust your time.");

					alert.showAndWait();
					return false;
				}
			}
		}

		for (Cas cas : temp_list2) {

			if (date.isAfter(cas.getSemestar().getDatumPocetkaSemestra())
					&& date.isBefore(cas.getSemestar().getDatumZavrsetkaSemestra())) {
				if (date.getDayOfWeek().toString().equals(cas.getDatumOdrzavanjaCasa().toUpperCase())) {
					long startRez2 = cas.getvrijemePocetkaCasaSat() * 60 + cas.getVrijemePocetkaCasaMinuta();
					long endRez2 = cas.getVrijemeZavrsetkaCasaSat() * 60 + cas.getVrijemeZavrsetkaCasaMinuta();
					if (startRez < endRez2 && startRez2 < endRez) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Time alert");
						alert.setHeaderText("Reservation overlaps with Period: " + cas);
						alert.setContentText("Adjust your time.");

						alert.showAndWait();
						return false;
					}

				}

			}
		}
		return true;
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
		rez.setVrijemeTrajanja();

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
		rez.setVrijemeTrajanja();

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
		// System.out.println(ProfessorController.nastavnik);

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT s FROM Sala s");
		List<Sala> temp_list = q.getResultList();
		classCombo.setItems(FXCollections.observableArrayList(temp_list).sorted());

		Query q1 = em.createQuery("SELECT g FROM GrupaStudenata g WHERE g.nastavnik = :a", Grupa.class);
		q1.setParameter("a", ProfessorController.nastavnik);
		List<Grupa> temp_list1 = q1.getResultList();

		// Dodano da provjeri da li postoji grupa za tog nastavnika
		empty = temp_list1.isEmpty();

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

		tabela.getColumns().addAll(DatumRezervacije, nastavnikRezervisan, vrijemeRezervacije, tipRezervacije,
				grupaStudenata);

		DatumRezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacija, LocalDate>("datumOdrzavanja"));
		nastavnikRezervisan.setCellValueFactory(new PropertyValueFactory<Rezervacija, Nastavnik>("nastavnik"));
		vrijemeRezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("vrijemeTrajanja"));
		tipRezervacije.setCellValueFactory(new PropertyValueFactory<Rezervacija, String>("tipRezervacije"));
		grupaStudenata.setCellValueFactory(new PropertyValueFactory<Rezervacija, Grupa>("grupa"));

		DatumRezervacije.prefWidthProperty().bind(tabela.widthProperty().divide(5));
		nastavnikRezervisan.prefWidthProperty().bind(tabela.widthProperty().divide(5));
		vrijemeRezervacije.prefWidthProperty().bind(tabela.widthProperty().divide(5));
		tipRezervacije.prefWidthProperty().bind(tabela.widthProperty().divide(5));
		grupaStudenata.prefWidthProperty().bind(tabela.widthProperty().divide(5));

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

	}

	public void Press(MouseEvent event) throws IOException {
		if (!groupCombo.isDisabled()) {
			if (empty) {
				ProdekanController.Information = "There is no groups for you, talk to Vice Dean.";
				show(event);
				return;
			}
		}
	}

	public void show(Event event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}