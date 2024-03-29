package application;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jfoenix.controls.JFXTextField;

import entiteti.Nastavnik;
import entiteti.Predmet;
import entiteti.Semestar;
import entiteti.Usmjerenje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class addSubjectController implements Initializable {
	@FXML
	private TextField subjectTitle = new TextField();

	@FXML
	private TableView<Nastavnik> table;

	@FXML
	private TableColumn<Nastavnik, String> teacher;

	@FXML
	private JFXTextField searchField;

	@FXML
	private ListView<Usmjerenje> orientationsTitle = new ListView<>();

	@FXML
	private ListView<Semestar> semesterTitle = new ListView<>();

	@FXML
	private Label teachersHelp = new Label();

	@FXML
	private Label errSubject = new Label();

	@FXML
	private Label errTeachers = new Label();

	@FXML
	private Label errOrientations = new Label();

	@FXML
	private Label errSemester = new Label();

	public void addSubject(ActionEvent event) throws Exception {
		if (subjectTitle.getText().isBlank() || table.getSelectionModel().isEmpty()
				|| orientationsTitle.getSelectionModel().isEmpty() || semesterTitle.getSelectionModel().isEmpty()) {
			if (subjectTitle.getText().isBlank())
				errSubject.setText("You didn't set the title of the subject");
			else
				errSubject.setText("");

			if (table.getSelectionModel().isEmpty())
				errTeachers.setText("You didn't choose the teacher.");
			else
				errTeachers.setText("");

			if (orientationsTitle.getSelectionModel().isEmpty())
				errOrientations.setText("You didn't choose the orientation.");
			else
				errOrientations.setText("");

			if (semesterTitle.getSelectionModel().isEmpty())
				errSemester.setText("You didn't choose the semester.");
			else
				errSemester.setText("");
		} else {
			String subjectName = subjectTitle.getText();
			Collection<Nastavnik> teacherName = table.getSelectionModel().getSelectedItems();
			Collection<Usmjerenje> orientationName = orientationsTitle.getSelectionModel().getSelectedItems();
			Collection<Semestar> semesterName = semesterTitle.getSelectionModel().getSelectedItems();
			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT x FROM Predmet x");

			@SuppressWarnings("unchecked")
			List<Predmet> predmeti = q.getResultList();

			for (Predmet e : predmeti) {
				if (e.getImePred().toLowerCase().equals(subjectName.toLowerCase())) {
					ProdekanController.Information = "The entity is already in the database!";
					show(event);
					em.close();
					emf.close();
					return;
				}
			}

			Predmet noviPredmet = new Predmet(subjectName, teacherName, orientationName, semesterName);
			em.getTransaction().begin();
			em.persist(noviPredmet);
			em.getTransaction().commit();
			em.close();
			emf.close();

			ProdekanController.Information = "Successfully added";
			show(event);
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		orientationsTitle.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		semesterTitle.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT x FROM Nastavnik x");
		@SuppressWarnings("unchecked")
		List<Nastavnik> temp_list = q.getResultList();

		ObservableList<Nastavnik> temp = FXCollections.observableArrayList();

		for (Nastavnik n : temp_list)
			temp.add(n);

		teacher.setCellValueFactory(new PropertyValueFactory<Nastavnik, String>("ime"));

		FilteredList<Nastavnik> nastavnici = new FilteredList<Nastavnik>(temp, p -> true);

		table.setItems(nastavnici.sorted());

		searchField.setOnKeyReleased(keyEvent -> {
			nastavnici.setPredicate(p -> p.getIme().toLowerCase().contains(searchField.getText().toLowerCase().trim()));
		});

		Query q2 = em.createQuery("SELECT x FROM Usmjerenje x");
		@SuppressWarnings("unchecked")
		List<Usmjerenje> temp2_list = q2.getResultList();

		if (temp2_list != null)
			orientationsTitle.setItems(FXCollections.observableArrayList(temp2_list).sorted());

		Query q3 = em.createQuery("SELECT x FROM Semestar x");
		@SuppressWarnings("unchecked")

		List<Semestar> temp3_list = q3.getResultList();

		if (temp3_list != null)
			semesterTitle.setItems(FXCollections.observableArrayList(temp3_list).sorted());

		em.close();
		emf.close();
	}

	private void show(Event event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}