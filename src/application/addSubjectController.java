package application;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entiteti.Nastavnik;
import entiteti.Predmet;
import entiteti.Semestar;
import entiteti.Usmjerenje;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addSubjectController implements Initializable {
	@FXML
	private TextField subjectTitle = new TextField();

	@FXML
	private ListView<Nastavnik> teachersTitle = new ListView<>();

	@FXML
	private ListView<Usmjerenje> orientationsTitle = new ListView<>();

	@FXML
	private ListView<Semestar> semesterTitle = new ListView<>();

	@FXML
	private Label errSubject ;

	@FXML
	private Label errTeachers ;

	@FXML
	private Label errOrientations ;

	@FXML
	private Label errSemester ;

	public void addSubject(ActionEvent event) throws Exception {
		if (subjectTitle.getText().isBlank() || teachersTitle.getSelectionModel().isEmpty()
			|| teachersTitle.getSelectionModel().isEmpty() || semesterTitle.getSelectionModel().isEmpty()) {
			if (subjectTitle.getText().isBlank())
				errSubject.setText("You didn't set the title of the subject");
			else
				errSubject.setText("");

			if (teachersTitle.getSelectionModel().isEmpty())
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
		}

		else {
			String subjectName = subjectTitle.getText().toUpperCase();
			Collection<Nastavnik> teacherName = teachersTitle.getSelectionModel().getSelectedItems();
			Collection<Usmjerenje> orientationName = orientationsTitle.getSelectionModel().getSelectedItems();
			Collection<Semestar> semesterName = semesterTitle.getSelectionModel().getSelectedItems();
			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q = em.createQuery("SELECT x FROM Predmet x WHERE x.imePred = :n", Predmet.class);
			q.setParameter("n", subjectName);

			@SuppressWarnings("unchecked")
			List<Predmet> predmeti = q.getResultList();

			if (predmeti.size() > 0) {

				ProdekanController.Information = "Entitet vec u bazi!";
				Stage primaryStage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
				Scene scene = new Scene(root);
				primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
				em.close();
				emf.close();
				return;
			}

			Predmet noviPredmet = new Predmet(subjectName, teacherName, orientationName, semesterName);
			em.getTransaction().begin();
			em.persist(noviPredmet);
			em.getTransaction().commit();
			em.close();
			emf.close();

			ProdekanController.Information = "Uspjesno dodano";
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
			Scene scene = new Scene(root);
			primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			primaryStage.setScene(scene);
			primaryStage.show();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		teachersTitle.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		orientationsTitle.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		semesterTitle.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT x FROM Nastavnik x");
		@SuppressWarnings("unchecked")
		List<Nastavnik> temp_list = q.getResultList();

		if (temp_list != null)
			teachersTitle.setItems(FXCollections.observableArrayList(temp_list));

		Query q2 = em.createQuery("SELECT x FROM Usmjerenje x");
		@SuppressWarnings("unchecked")
		List<Usmjerenje> temp2_list = q2.getResultList();

		if (temp2_list != null)
			orientationsTitle.setItems(FXCollections.observableArrayList(temp2_list));

		Query q3 = em.createQuery("SELECT x FROM Semestar x");
		@SuppressWarnings("unchecked")
		List<Semestar> temp3_list = q3.getResultList();

		if (temp3_list != null)
			semesterTitle.setItems(FXCollections.observableArrayList(temp3_list));

		em.close();
		emf.close();
	}
}
