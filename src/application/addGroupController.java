package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jfoenix.controls.JFXTextField;

import entiteti.Grupa;
import entiteti.Nastavnik;
import entiteti.Predmet;
import entiteti.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class addGroupController implements Initializable {

	@FXML
	private ListView<String> students, selectedStudents;
	@FXML
	private ComboBox<String> type;
	@FXML
	private TableView<Predmet> table3;
	@FXML
	private TableColumn<Predmet, String> subject;
	@FXML
	private TableView<Nastavnik> table2;
	@FXML
	private TableColumn<Nastavnik, String> teacher;
	@FXML
	private JFXTextField searchField2, searchField3;

	private Collection<String> selected;
	private FilteredList<Predmet> pred;
	private FilteredList<Nastavnik> nast;

	@FXML
	Label errType, errSubject, errTeacher, errStudents;

	public void show(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml_files/Info.fxml"));
		Scene scene = new Scene(root);
		primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		List<String> tip_vjezbi = new ArrayList<String>();
		tip_vjezbi.add("Lecture");
		tip_vjezbi.add("Auditory");
		tip_vjezbi.add("Laboratory");
		type.setItems(FXCollections.observableList(tip_vjezbi).sorted());

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q3 = em.createQuery("SELECT p FROM Predmet p");
		List<Predmet> predmeti = q3.getResultList();

		ObservableList<Predmet> temp3 = FXCollections.observableArrayList();
		for (Predmet e : predmeti)
			temp3.add(e);

		Query q2 = em.createQuery("SELECT n FROM Nastavnik n");
		List<Nastavnik> nastavnici = q2.getResultList();

		ObservableList<Nastavnik> temp2 = FXCollections.observableArrayList();
		for (Nastavnik e : nastavnici)
			temp2.add(e);

		Query q1 = em.createQuery("SELECT s FROM Student s");
		List<Student> studenti = q1.getResultList();

		List<String> temp1 = new ArrayList<String>();
		for (Object e : studenti)
			temp1.add(((Student) e).getImeStud() + " " + ((Student) e).getPrezStud());
		students.setItems(FXCollections.observableList(temp1).sorted());
		students.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		teacher.setCellValueFactory(new PropertyValueFactory<Nastavnik, String>("ime"));
		subject.setCellValueFactory(new PropertyValueFactory<Predmet, String>("imePred"));

		nast = new FilteredList<Nastavnik>(temp2, p -> true);
		pred = new FilteredList<Predmet>(temp3, p -> true);

		table2.setItems(nast.sorted());
		table3.setItems(pred.sorted());

		searchField2.setOnKeyReleased(keyEvent -> {
			nast.setPredicate(p -> p.getIme().toLowerCase().contains(searchField2.getText().toLowerCase().trim()));
		});

		searchField3.setOnKeyReleased(keyEvent -> {
			pred.setPredicate(p -> p.getImePred().toLowerCase().contains(searchField3.getText().toLowerCase().trim()));
		});

		em.close();
		emf.close();
	}

	public void selectStudents(ActionEvent event) throws Exception {
		ObservableList<String> temp = students.getSelectionModel().getSelectedItems();
		selectedStudents.setItems(temp.sorted());
		selected = temp;
		students.setDisable(true);
	}

	public void test(MouseEvent event) throws IOException {
		if (!table2.getSelectionModel().isEmpty()) {
			ObservableList<Predmet> temp2 = FXCollections.observableArrayList();
			for (int i = 0; i < table2.getSelectionModel().getSelectedItem().getPredmeti().size(); ++i)
				temp2.add((Predmet) table2.getSelectionModel().getSelectedItem().getPredmeti().toArray()[i]);
			FilteredList<Predmet> novi = new FilteredList<Predmet>(temp2, p -> true);
			table3.setItems(novi.sorted());
		} else {
			table3.setItems(pred.sorted());
		}
	}

	public void test2(MouseEvent event) throws IOException {
		if (!table3.getSelectionModel().isEmpty()) {
			ObservableList<Nastavnik> temp2 = FXCollections.observableArrayList();
			for (int i = 0; i < table3.getSelectionModel().getSelectedItem().getNastavnike().size(); ++i)
				temp2.add((Nastavnik) table3.getSelectionModel().getSelectedItem().getNastavnike().toArray()[i]);
			FilteredList<Nastavnik> novi = new FilteredList<Nastavnik>(temp2, p -> true);
			table2.setItems(novi.sorted());
		} else {
			table2.setItems(nast.sorted());
		}
	}

	public void addGroup(ActionEvent event) throws Exception {
		if (type.getSelectionModel().isEmpty() || table3.getSelectionModel().isEmpty()
				|| table2.getSelectionModel().isEmpty() || students.getSelectionModel().isEmpty()) {
			if (type.getSelectionModel().isEmpty())
				errType.setText("You didn't choose the type");
			else
				errType.setText("");

			if (table3.getSelectionModel().isEmpty())
				errSubject.setText("You didn't choose the subject");
			else
				errSubject.setText("");

			if (table2.getSelectionModel().isEmpty())
				errTeacher.setText("You didn't choose the teacher.");
			else
				errTeacher.setText("");

			if (students.getSelectionModel().isEmpty())
				errStudents.setText("You didn't choose students.");
			else
				errStudents.setText("");
		} else {
			if (!students.isDisabled()) {
				errSubject.setText("");
				errTeacher.setText("");
				errType.setText("");
				errStudents.setText("Click on select students to confirm selection");
			} else {
				Collection<Student> studenti = new ArrayList<Student>();
				String tip = type.getValue();
				Nastavnik nastavnik = table2.getSelectionModel().getSelectedItem();
				Predmet predmet = table3.getSelectionModel().getSelectedItem();

				String PERSISTENCE_UNIT_NAME = "raspored";
				EntityManagerFactory emf;
				emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
				EntityManager em = emf.createEntityManager();

				for (String s : selected) {
					Query q3 = em.createQuery("SELECT s FROM Student s WHERE CONCAT(s.imeStud,' ',s.prezStud) = :is",
							Student.class);
					q3.setParameter("is", s);
					Object stud = q3.getSingleResult();
					studenti.add((Student) stud);
				}

				Grupa g = new Grupa();
				g.setTipgrupe(tip);
				g.setNastavnika(nastavnik);
				g.setPredmet(predmet);
				g.setStudente(studenti);
				nastavnik.getGrupe().add(g);
				for (Student s : studenti)
					s.getGrupe().add(g);

				em.getTransaction().begin();
				em.persist(g);
				em.getTransaction().commit();

				em.close();
				emf.close();

				ProdekanController.Information = "Successfully added";
				show(event);
			}
		}
	}
}