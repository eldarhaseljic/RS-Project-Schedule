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

import entiteti.Grupa;
import entiteti.Nastavnik;
import entiteti.Predmet;
import entiteti.Student;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

public class addGroupController implements Initializable {

	@FXML
	private ComboBox<String> type;
	@FXML
	private ComboBox<String> subjects;
	@FXML
	private ComboBox<String> teacher;
	@FXML
	private ListView<String> students;
	@FXML
	private ListView<String> selectedStudents;

	private Collection<String> selected;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		List<String> tip_vjezbi = new ArrayList<String>();
		tip_vjezbi.add("Lecture");
		tip_vjezbi.add("Auditory");
		tip_vjezbi.add("Laboratory");
		type.setItems(FXCollections.observableList(tip_vjezbi));

		String PERSISTENCE_UNIT_NAME = "raspored";
		EntityManagerFactory emf;
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = emf.createEntityManager();

		Query q = em.createQuery("SELECT p FROM Predmet p");
		List<?> predmeti = q.getResultList();

		List<String> temp = new ArrayList<String>();
		for (Object e : predmeti)
			temp.add(((Predmet) e).getImePred());
		subjects.setItems(FXCollections.observableList(temp));

		Query q1 = em.createQuery("SELECT n FROM Nastavnik n");
		List<?> nastavnici = q1.getResultList();

		List<String> temp1 = new ArrayList<String>();
		for (Object e : nastavnici)
			temp1.add(((Nastavnik) e).getImeNast() + " " + ((Nastavnik) e).getPrezNast());
		teacher.setItems(FXCollections.observableList(temp1));

		Query q2 = em.createQuery("SELECT s FROM Student s");
		List<?> studenti = q2.getResultList();

		List<String> temp2 = new ArrayList<String>();
		for (Object e : studenti)
			temp2.add(((Student) e).getImeStud() + " " + ((Student) e).getPrezStud());
		students.setItems(FXCollections.observableList(temp2));

		// Ovo selektuje vise ali kad drzis CTRL
		// pa odkomentarisi ako bude trebalo dalje
		students.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		em.close();
		emf.close();
	}

	public void selectStudents(ActionEvent event) throws Exception {
		ObservableList<String> temp = students.getSelectionModel().getSelectedItems();
		selectedStudents.setItems(temp);
		selected = temp;
		students.setDisable(true);
		/*
		 * for(String s : selected) System.out.println(s);
		 */
	}

	public void addGroup(ActionEvent event) throws Exception {
		if (	type.getSelectionModel().isEmpty()
				|| subjects.getSelectionModel().isEmpty() 
				|| teacher.getSelectionModel().isEmpty()
				|| students.getSelectionModel().isEmpty()) 
		{
			if (type.getSelectionModel().isEmpty())
				errType.setText("You didn't choose the type");
			else
				errType.setText("");
			
			if (subjects.getSelectionModel().isEmpty())
				errSubject.setText("You didn't choose the subject");
			else
				errSubject.setText("");

		    if (teacher.getSelectionModel().isEmpty())
				errTeacher.setText("You didn't choose the teacher.");
			else
				errTeacher.setText("");
		    
			if (students.getSelectionModel().isEmpty())
				errStudents.setText("You didn't choose students.");
			else
				errStudents.setText("");
		} else {
			Collection<Student> studenti = new ArrayList<Student>();
			String tip = type.getValue();
			String nastavnik = teacher.getValue();

			String PERSISTENCE_UNIT_NAME = "raspored";
			EntityManagerFactory emf;
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = emf.createEntityManager();

			Query q2 = em.createQuery("SELECT n FROM Nastavnik n WHERE CONCAT(n.imeNast,' ',n.prezNast) = :in",
					Nastavnik.class);
			q2.setParameter("in", nastavnik);
			@SuppressWarnings("unchecked")
			List<Nastavnik> n = q2.getResultList();
			Nastavnik nast = n.get(0);

			for (String s : selected) {
				Query q3 = em.createQuery("SELECT s FROM Student s WHERE CONCAT(s.imeStud,' ',s.prezStud) = :is",
						Student.class);
				q3.setParameter("is", s);
				Object stud = q3.getSingleResult();
				studenti.add((Student) stud);
			}

			Grupa g = new Grupa();
			g.setTipgrupe(tip);
			g.setNastavnika(nast);
			nast.getGrupe().add(g);
			// g.setPredmet((Predmet)p);
			g.setStudente(studenti);
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